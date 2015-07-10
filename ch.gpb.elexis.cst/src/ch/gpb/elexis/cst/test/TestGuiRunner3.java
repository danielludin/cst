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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ch.gpb.elexis.cst.data.ValuePairTimeline;
import ch.gpb.elexis.cst.widget.ValuePairTimelineCanvas;

public class TestGuiRunner3 {

    public static void main(String[] args) {
	Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setSize(780, 400);
	shell.setLayout(new RowLayout());

	shell.setText("Composite Example");

	final Composite composite = new Composite(shell, SWT.NONE);
	GridLayout gridLayout = new GridLayout();
	gridLayout.numColumns = 1;
	composite.setLayout(gridLayout);


	ValuePairTimelineCanvas canvas = new ValuePairTimelineCanvas(shell, SWT.NONE, "Test", "kg");

	List<ValuePairTimeline> values = new ArrayList<ValuePairTimeline>();

	/*
	values.add(new ValuePairTimeline("20150620", 500d, 550d));
	values.add(new ValuePairTimeline("20150601", 400d, 780d));
	values.add(new ValuePairTimeline("20150510", 300d, 440d));
	values.add(new ValuePairTimeline("20150430", 880d, 780d));
	values.add(new ValuePairTimeline("20150424", 110d, 820d));
	values.add(new ValuePairTimeline("20150414", 0d, 690d));
	values.add(new ValuePairTimeline("20150321", 280d, 740d));


	values.add(new ValuePairTimeline("20150620", 1.23d, 2.35d));
	values.add(new ValuePairTimeline("20150601", 4.52d, 3.45d));
	values.add(new ValuePairTimeline("20150510", 3.48d, 6.12d));
	values.add(new ValuePairTimeline("20150424", 9.85d, 5.33d));
	values.add(new ValuePairTimeline("20150414", 0.05d, 4.23d));
	values.add(new ValuePairTimeline("20150321", 2.80d, 3.49d));
	 */


	/**/

	values.add(new ValuePairTimeline("20150620", 100, 110));
	values.add(new ValuePairTimeline("20150601", 20, 112));
	values.add(new ValuePairTimeline("20150510", 130, 116));
	values.add(new ValuePairTimeline("20150424", 140, 112));
	values.add(new ValuePairTimeline("20150414", 120, 109));
	values.add(new ValuePairTimeline("20150321", 120, 110));
	
	/*
	 *
	values.add(new ValuePairTimeline("20150620", 340, 400));
	values.add(new ValuePairTimeline("20150601", 120, 440));
	values.add(new ValuePairTimeline("20150510", 130, 350));
	values.add(new ValuePairTimeline("20150424", 140, 250));
	values.add(new ValuePairTimeline("20150414", 120, 330));
	values.add(new ValuePairTimeline("20150321", 120, 660));
	 */
	canvas.setFindings(values);

	

	shell.open();
	while (!shell.isDisposed()) {
	    if (!display.readAndDispatch())
		display.sleep();
	}
	display.dispose();
    }

}
