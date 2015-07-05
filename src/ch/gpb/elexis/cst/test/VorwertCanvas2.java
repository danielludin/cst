package ch.gpb.elexis.cst.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import ch.gpb.elexis.cst.preferences.Messages;

public class VorwertCanvas2 extends Canvas {

    Font fontA;
    static Color ORANGE;
    static Color BRIGHTGREEN;
    Color WHITE;
    Color GREY;
    Color BLACK;
    Color BLUE;
    Image pointer;
    Finding finding;
    int iPixX = 300;
    //int iPixY = 60;
    int iPixY = 70;
    int xoffBase = 4;
    int yoffBase = 80;
    /*
    double refMstart;
    double refMend;
    double refFstart;
    double refFend;
    */
    List<Finding> findings = new ArrayList();

    public VorwertCanvas2(Composite parent, int style) {
	super(parent, style);

	WHITE = new Color(null, 255, 255, 255);
	ORANGE = new Color(getDisplay(), 255, 104, 0);
	BRIGHTGREEN = new Color(getDisplay(), 104, 255, 0);
	BLACK = new Color(getDisplay(), 0, 0, 0);
	GREY = new Color(getDisplay(), 220, 220, 220);
	BLUE = new Color(getDisplay(), 30, 30, 255);

	Font initialFont = getDisplay().getSystemFont();
	FontData[] fontData = initialFont.getFontData();
	for (int i = 0; i < fontData.length; i++) {
	    fontData[i].setHeight(6);
	}
	fontA = new Font(getDisplay(), fontData);
	//System.out.println("constructor DangerRangeCanvas: " + fontA.getFontData()[0].getName());
	setBackground(WHITE);

	pointer = new Image(getDisplay(), VorwertCanvas2.class.getResourceAsStream("pointer.png"));

	// TODO: dispose all other colors
	addDisposeListener(new DisposeListener() {
	    public void widgetDisposed(DisposeEvent e) {
		WHITE.dispose();
		BLACK.dispose();
		ORANGE.dispose();
		BRIGHTGREEN.dispose();
		BLUE.dispose();
		GREY.dispose();
		fontA.dispose();
		pointer.dispose();
	    }

	});

	addPaintListener(new PaintListener() {
	    public void paintControl(PaintEvent e) {
		VorwertCanvas2.this.paintControl(e);
	    }
	});
    }

    void paintControl(PaintEvent e) {

	GC gc = e.gc;
	gc.setFont(fontA);

	if (findings == null || findings.size() == 0) {
	    gc.drawText(Messages.Cst_Text_keine_vorwerte, 20, 20);
	    return;
	}
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	// compute nr of days between lowest and highest date
	Date dStart = findings.get(0).getDateOfFinding();
	Date dEnd = findings.get(findings.size() - 1).getDateOfFinding();
	System.out.println("start/end: (" + dStart.toLocaleString() + " - " + dEnd.toLocaleString() + ")");

	long totalSpan = getNrOfDaysBetween(dStart, dEnd);
	System.out.println("totalSpan: (" + sdf.format(dStart) + " - " + sdf.format(dEnd) + ") " + totalSpan);

	gc.setBackground(ORANGE);
	//gc.setForeground(BRIGHTGREEN);
	gc.drawRectangle(xoffBase, yoffBase, 400, 1);

	double dRefFend = findings.get(0).getRefFend();
	double dRefMend = findings.get(0).getRefMend();
	double dRefFstart = findings.get(0).getRefFstart();
	double dRefMstart = findings.get(0).getRefMstart();

	double yFactor = new Double(iPixY).doubleValue() / Math.max(dRefFend, dRefMend);

	int iOffYtopM = new Double(dRefMend * yFactor).intValue();
	int iOffYtopF = new Double(dRefFend * yFactor).intValue();

	int iOffYbottomM = new Double(dRefMstart * yFactor).intValue();
	int iOffYbottomF = new Double(dRefFstart * yFactor).intValue();
	System.out.println(" refF refM(" + dRefFstart + "/" + dRefFend + ") (" + dRefMstart + "/" + dRefMend + ")");

	double dFstart = findings.get(0).getRefFstart();
	double dFend = findings.get(0).getRefFend();

	// draw top range Male
	gc.setForeground(BLUE);
	gc.drawLine(xoffBase, yoffBase - iOffYtopM, iPixX, yoffBase - iOffYtopM);
	gc.drawText(String.valueOf(dRefMend), iPixX + 25, yoffBase - iOffYtopM - 4, true);
	// draw bottom range Male
	gc.drawLine(xoffBase, yoffBase - iOffYbottomM, iPixX, yoffBase - iOffYbottomM);
	gc.drawText(String.valueOf(dRefMstart), iPixX + 25, yoffBase - iOffYbottomM - 4, true);
	// draw top range Femal
	gc.setForeground(ORANGE);
	gc.drawLine(xoffBase, yoffBase - iOffYtopF, iPixX, yoffBase - iOffYtopF);
	gc.drawText(String.valueOf(dRefFend), iPixX + 25, yoffBase - iOffYtopF - 4, true);
	// draw top range Femal
	gc.drawLine(xoffBase, yoffBase - iOffYbottomF, iPixX, yoffBase - iOffYbottomF);
	gc.drawText(String.valueOf(dRefFstart), iPixX + 25, yoffBase - iOffYbottomF - 4, true);

	gc.setBackground(GREY);

	int xoff = 0;
	double xFactor = new Double(iPixX).doubleValue() / new Double(totalSpan).doubleValue();
	System.out.println("xFactor: " + xFactor);

	gc.setForeground(BLACK);
	for (int x = 0; x < findings.size(); x++) {

	    //System.out.println("  xoff: " + xoff);
	    //System.out.println("  xoff*F: " +  new Double(xoff * xFactor).intValue());
	    Finding finding = findings.get(x);

	    int yoff = 0;
	    if (x % 2 == 0) {
		yoff = 8;
	    }
	    String date = sdf.format(finding.getDateOfFinding());
	    gc.drawText(date, xoffBase + new Double(xoff * xFactor).intValue(), yoffBase + yoff + 8);

	    gc.drawText(String.valueOf(finding.getValue()), xoffBase + new Double(xoff * xFactor).intValue(), yoffBase
		    - new Double(finding.getValue() * yFactor).intValue() - 8, true);

	    //System.out.println("date finding: " + date  + " / " + xoff);

	    if (x < findings.size() - 1) {
		long lSpan = getNrOfDaysBetween(findings.get(0).getDateOfFinding(), findings.get(x + 1)
			.getDateOfFinding());
		xoff = new Long(lSpan).intValue();
	    }

	}

	gc.dispose();
    }

    private long getNrOfDaysBetween(Date dStart, Date dEnd) {
	long diff = dEnd.getTime() - dStart.getTime();
	//System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	return days;

    }

    public Point computeSize(int wHint, int hHint, boolean changed) {
	/*
	 * int width = 0, height = 0; if (image != null) { Rectangle bounds =
	 * image.getBounds(); width = bounds.width + 5; height = bounds.height;
	 * }
	 * 
	 * if (text != null) { GC gc = new GC(this); Point extent =
	 * gc.stringExtent(text); gc.dispose(); width += extent.x; height =
	 * Math.max(height, extent.y); }
	 */
	return new Point(iPixX + 70, 30 + yoffBase);

    }

    public Finding getFinding() {
	return finding;
    }

    public void setFinding(Finding finding) {
	this.finding = finding;
    }

    public List<Finding> getFindings() {
	return findings;
    }

    public void setFindings(List<Finding> findings) {
	/*
	for (Finding finding : findings) {
		System.out.println("A: " + finding.getDateOfFinding().toGMTString());
	}*/

	System.out.println();
	this.findings = findings;
	Collections.sort(this.findings, new FindingsComparable());
	System.out.println();
	/*
	for (Finding finding : findings) {
		System.out.println("B: " + finding.getDateOfFinding().toGMTString());
	}*/
    }

    public class FindingsComparable implements Comparator<Finding> {

	@Override
	public int compare(Finding o1, Finding o2) {
	    return o1.getDateOfFinding().compareTo(o2.getDateOfFinding());

	}
    }
}
