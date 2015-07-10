/*******************************************************************************
 * Copyright (c) 2015, Daniel Ludin
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Daniel Ludin (ludin@hispeed.ch) - initial implementation
 *******************************************************************************/
package ch.gpb.elexis.cst.test;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ch.gpb.elexis.cst.dialog.PdfOptionsDialog;
import ch.gpb.elexis.cst.util.ImageUtils;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class TestRunnerPdf {
    public static int OUTPUTWIDTH = 794;
    public static int OUTPUTHEIGTH = 1123;

    public void makePdf() {
	Display display = new Display();
	Shell viewer = new Shell(display);
	viewer.setLayout(new GridLayout(1, false));

	// Load an image
	//ImageData imgData = new ImageData( "your image path" );
	ImageData imgData = new ImageData("D:\\Elexis\\Testdaten\\effektiv-baur.png");
	Image image = new Image(display, imgData);
	//String selected = "D:\\Elexis\\Testdaten\\effektiv-baur.pdf";

	// The scrolled composite
	ScrolledComposite sc = new ScrolledComposite(viewer, SWT.H_SCROLL | SWT.V_SCROLL);
	GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
	layoutData.heightHint = 400;
	sc.setLayoutData(layoutData);

	Label imgLabel = new Label(sc, SWT.NONE);
	imgLabel.setImage(image);
	imgLabel.setSize(imgLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	sc.setContent(imgLabel);

	GC gc = null;
	//Image image = null;
	try {

	    String selected = "D:\\Elexis\\Testdaten\\effektiv-baur.pdf";

	    //image = new Image(viewer.getDisplay(), viewer.getBounds().width, viewer.getBounds().height);
	    //image = new Image(viewer.getDisplay(), 794, viewer.getBounds().height);
	    //ImageLoader loader = new ImageLoader();

	    gc = new GC(image);
	    viewer.print(gc);

	    gc.dispose();

	    Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
	    System.out.println(sdf.format(date));

	    //Patient patient = Patient.load(profile.getKontaktId());
	    String sTitle = "CST Auswertung für ";
	    /*
	    if (patient != null) {
	    sTitle = sTitle + patient.getName() + " " + patient.getVorname() + " vom " + sdf.format(date);
	    }*/

	    //

	    float inputWidth = 595f;
	    float inputHeigth = 842f;
	    int pdfOutputOption = 0;
	    boolean onePage = true;

	    System.out.println("check 0 w: " + image.getBounds().width + " h:" + image.getBounds().height);

	    PdfOptionsDialog dialog = new PdfOptionsDialog(viewer);
	    dialog.create();

	    if (dialog.open() == Window.OK) {
		System.out.println(dialog.getWidth());
		System.out.println(dialog.getHeigth());

		/*
		Float hDoc = Float.valueOf(dialog.getWidth());
		inputWidth = hDoc.floatValue();
		Float wDoc = Float.valueOf(dialog.getHeigth());
		inputHeigth = wDoc.floatValue();
		*/

		pdfOutputOption = dialog.getPdfOutputOption();
		if (pdfOutputOption == PdfOptionsDialog.OPTION_ONE_PAGE) {
		    onePage = true;
		} else {

		    onePage = false;
		}
	    }
	    System.out.println("is one page: " + onePage);

	    float fontSize = 12f;

	    /*
	    float docHeight = viewer.getBounds().height;
	    // docHeight = docHeight / 7.62778f;
	    docHeight = docHeight / 7.5f;


	    System.out.println("docHeight: " + docHeight);
	    if (docHeight < 360f) {
	    docHeight = 360f;
	    }*/

	    BufferedImage bimage = ImageUtils.convertToAWT(image.getImageData());

	    System.out.println("bimage w:h: " + bimage.getWidth() + " / " + bimage.getHeight());

	    com.lowagie.text.Image itextImage = null;
	    java.awt.Image awtImage = null;

	    try {

		awtImage = Toolkit.getDefaultToolkit().createImage(bimage.getSource());

		itextImage = com.lowagie.text.Image.getInstance(awtImage, null);

		/*
		itextImage = com.lowagie.text.Image.getInstance(
			Toolkit.getDefaultToolkit().createImage(bimage.getSource()), null);
		*/

	    } catch (Exception e) {
		System.out.println("Error on image loading: " + e.toString());
		e.printStackTrace();
	    }

	    // only for debugging

	    //loader.data = new ImageData[] { image.getImageData() };
	    //loader.save("D:\\tmp\\debug.png", SWT.IMAGE_PNG);

	    /*
	    try {
	    BufferedImage[] imageChunks = ImageUtils.splitImageByHeigth(bimage, OUTPUTHEIGTH);

	    for (int i = 0; i < imageChunks.length; i++) {
	        ImageIO.write(imageChunks[i], "png", new File("D:\\tmp", "img" + i + ".png"));
	    }

	    } catch (Exception e) {
	    System.out.println("Error on image loading: " + e.toString());
	    e.printStackTrace();
	    }
	     */
	    Rectangle a4 = new Rectangle(PageSize.A4);

	    // Rectangle pagesize = new Rectangle(216f, docHeight);
	    // Rectangle pagesize = new Rectangle(216f, docHeight);
	    // Rectangle pagesize = new Rectangle(216f, 1440f);

	    //Rectangle pagesize = new Rectangle(viewer.getBounds().width, viewer.getBounds().height);
	    Rectangle pagesize = new Rectangle(595f, itextImage.getHeight() * 0.75f);

	    // Rectangle pagesize =
	    // new Rectangle(
	    // ImageUtils.PixelsToPoints(
	    // viewer.getBounds().width, 96),
	    // ImageUtils.PixelsToPoints(
	    // viewer.getBounds().height, 96));
	    // Rectangle pagesize =
	    // new Rectangle(
	    // 14400,
	    // 14400);

	    System.out.println("pagesize: " + pagesize.toString());
	    viewer.open();

	    Document document;
	    if (onePage) {
		document = new Document(pagesize);

	    } else {
		document = new Document(PageSize.A4);
	    }

	    document.addCreationDate();

	    try {
		// creation of the different writers
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(selected));

		// various fonts
		BaseFont bf_helv = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", true);
		BaseFont bf_times = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", true);
		BaseFont bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252", true);
		BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", true);

		com.lowagie.text.Font fontHelv12 = new com.lowagie.text.Font(bf_helv, fontSize);

		com.lowagie.text.Font fontcourier = new com.lowagie.text.Font(bf_courier, fontSize);

		com.lowagie.text.Font fontTimes = new com.lowagie.text.Font(bf_times, fontSize);

		fontTimes.setSize(fontSize);
		fontTimes.setStyle(com.lowagie.text.Font.ITALIC);

		Chunk chunkHeader = new Chunk(sTitle, FontFactory.getFont(FontFactory.HELVETICA, fontSize,
			com.lowagie.text.Font.NORMAL, new java.awt.Color(255, 0, 0)));

		com.lowagie.text.Phrase phraseHeader = new com.lowagie.text.Phrase(chunkHeader);

		// headers and footers must be added before the document
		// is opened

		Chunk chunkFooter = new Chunk("Seite: ", FontFactory.getFont(FontFactory.HELVETICA, fontSize,
			com.lowagie.text.Font.BOLD, new java.awt.Color(0, 0, 0)));

		Phrase phraseFooter = new Phrase(chunkFooter);
		phraseFooter.setFont(fontHelv12);

		HeaderFooter footer = new HeaderFooter(phraseFooter, true);
		footer.setBorder(Rectangle.NO_BORDER);
		footer.setAlignment(Element.ALIGN_CENTER);

		document.setFooter(footer);

		Phrase headerPhrase = new Phrase(sTitle);
		headerPhrase.setFont(fontTimes);

		HeaderFooter header = new HeaderFooter(phraseHeader, false);
		header.setBorder(Rectangle.BOTTOM);
		header.setBorderWidth(0.5f);
		header.setAlignment(Element.ALIGN_LEFT);
		document.setHeader(header);

		document.open();

		/*
		 * if (itextImage.getScaledWidth() > 300 || itextImage.getScaledHeight() > 300) {
		 * itextImage.scaleToFit(300, 300); }
		 */

		System.out.println("check 1 w: " + itextImage.getWidth() + " h:" + itextImage.getHeight());

		/*
		 * if (itextImage.getScaledWidth() > 300) { itextImage.scaleToFit(1008, itextImage.getHeight());
		 * 
		 * }
		 */

		// whatever

		if (onePage) {

		    /*
		    itextImage.scaleToFit(
		        document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin(),
		        itextImage.getHeight() * 0.75f);

		    */
		    itextImage.scalePercent(66);
		    //itextImage.setAbsolutePosition(30, 20);

		    System.out.println("scaled width: " + itextImage.getScaledWidth());
		    System.out.println("scaled height: " + itextImage.getScaledHeight());

		    System.out.println("doc pagesize width: " + document.getPageSize().getWidth());
		    System.out.println("doc pagesize height: " + document.getPageSize().getHeight());
		    //
		    document.add(itextImage);
		} else {

		    BufferedImage[] imageChunks = ImageUtils.splitImageByHeigth(bimage, OUTPUTHEIGTH);

		    for (int i = 0; i < imageChunks.length; i++) {

			com.lowagie.text.Image itextImage2 = com.lowagie.text.Image.getInstance(Toolkit
				.getDefaultToolkit().createImage(imageChunks[i].getSource()), null);

			//ImageIO.write(imageChunks[i], "png", new File("D:\\tmp", "img" + (i + 1) + ".png"));

			//itextImage2.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());

			float imgWidth = document.getPageSize().getWidth() - document.leftMargin()
				- document.rightMargin();
			float imgHeigth = itextImage.getHeight() * 0.75f;

			System.out.println("Scaling w/h: " + imgWidth + " / " + imgHeigth);

			itextImage2.scaleToFit(imgWidth, imgHeigth);
			//itextImage2.scaleAbsolute(794, 1123);
			//itextImage2.scaleAbsoluteHeight(900);

			/*
			itextImage2.scaleToFit(document.getPageSize().getWidth() - document.leftMargin()
				- document.rightMargin(), itextImage.getHeight() * 0.75f);
			 */
			document.add(itextImage2);
			document.newPage();

		    }

		}

		System.out.println("check 3");

		// we're done!
		document.close();

		///////////////////////////////

	    } catch (Exception ex) {
		System.err.println(ex.getMessage());
	    }

	} finally {
	    image.dispose();
	    gc.dispose();
	}

	// Run it
	//shell.setSize(800, 600);
	while (!viewer.isDisposed()) {
	    if (!display.readAndDispatch())
		display.sleep();
	}

	display.dispose();
	image.dispose();

    }

    public static void main(String[] args) {

	/*
	// Display + shell
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new GridLayout(1, false));

	// Load an image
	//ImageData imgData = new ImageData( "your image path" );
	ImageData imgData = new ImageData("D:\\Elexis\\Testdaten\\effektiv-baur.png");
	Image image = new Image(display, imgData);

	// The scrolled composite
	ScrolledComposite sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
	GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
	layoutData.heightHint = 400;
	sc.setLayoutData(layoutData);

	Label imgLabel = new Label(sc, SWT.NONE);
	imgLabel.setImage(image);
	imgLabel.setSize(imgLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	sc.setContent(imgLabel);


	// Run it
	//shell.setSize(800, 600);
	shell.open();
	while (!shell.isDisposed()) {
	    if (!display.readAndDispatch())
		display.sleep();
	}

	display.dispose();
	image.dispose();
	*/
	new TestRunnerPdf().makePdf();

    }
}
