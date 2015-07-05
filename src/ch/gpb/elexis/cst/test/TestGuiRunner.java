package ch.gpb.elexis.cst.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ch.gpb.elexis.cst.view.profileeditor.DateRangeComposite;

public class TestGuiRunner {

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setSize(530, 400);
		shell.setLayout(new RowLayout());

		shell.setText("Composite Example");

		/*
		final Composite composite = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);

		

		CstDangerRangeCanvas pLabel = new CstDangerRangeCanvas(shell, SWT.NONE, 5.0, 45, 1.9, "Alpha-1-Antitryptin", "20150815");
		pLabel = new CstDangerRangeCanvas(shell, SWT.NONE, 200.0, 250, 249.3, "M2PA", "20150815");
		pLabel = new CstDangerRangeCanvas(shell, SWT.NONE, 50.0, 550, 520.3, "M2PA", "20150815");
		pLabel = new CstDangerRangeCanvas(shell, SWT.NONE, 10.0, 249, 245.9, "Calprotectin", "20150815");
		pLabel = new CstDangerRangeCanvas(shell, SWT.NONE, 1.0, 250.8, 249.8, "Whatever", "20150815");
		pLabel = new CstDangerRangeCanvas(shell, SWT.NONE, 00.0, 250, 1.0, "Whatever", "20150815");
		pLabel = new CstDangerRangeCanvas(shell, SWT.NONE, 100.0, 1000.0, 208.8, "Whatever", "20150815");
		pLabel = new CstDangerRangeCanvas(shell, SWT.NONE, 00.1, 9.9, 5.8, "Whatever", "20150815");

		
		List<Finding> findings = Finding.getFindings3();
		VorwertCanvas2 vCanvas = new VorwertCanvas2(composite, SWT.BORDER);
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.BEGINNING;
		vCanvas.setLayoutData(gd);
		vCanvas.setFindings(findings);

		findings = Finding.getFindings4();
		vCanvas = new VorwertCanvas2(composite, SWT.BORDER);
		gd = new GridData();
		gd.verticalAlignment = SWT.BEGINNING;
		vCanvas.setLayoutData(gd);
		vCanvas.setFindings(findings);

		findings = Finding.getFindings1();
		vCanvas = new VorwertCanvas2(composite, SWT.BORDER);
		gd = new GridData();
		gd.verticalAlignment = SWT.BEGINNING;
		vCanvas.setLayoutData(gd);
		vCanvas.setFindings(findings);
		 */

		final Image image = new Image(display, "D:\\Elexis\\bootstrap-elexis-3\\checkout\\elexis-3-core\\ch.elexis.core.application\\rsc\\elexis-logo.png");

		System.out.println("image: " + image.toString());

		try {
			DateRangeComposite pl = new DateRangeComposite(shell, SWT.NONE);
		} catch (Exception e) {
			System.out.println("FAilure: " + e.toString());
		}

		//pl.redraw();
		/*
		GridLayout glPl = new GridLayout();
		glPl.numColumns = 1;
		pl.setLayout(glPl);

		GridData gd = new GridData();
		gd.verticalAlignment = SWT.BEGINNING;
		pl.setLayoutData(gd);
		 */
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		image.dispose();
	}

}
