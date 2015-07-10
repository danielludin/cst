package ch.gpb.elexis.cst.test;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ch.gpb.elexis.cst.data.ValueSingleTimeline;
import ch.gpb.elexis.cst.widget.ValueSingleTimelineCanvas;

public class TestGuiRunner2 {

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


	ValueSingleTimelineCanvas canvas = new ValueSingleTimelineCanvas(shell, SWT.NONE, "Test", "kg");

	List<ValueSingleTimeline> values = new ArrayList<ValueSingleTimeline>();

	/*
	values.add(new ValueSingleTimeline("20150620", 500d));
	values.add(new ValueSingleTimeline("20150601", 400d));
	values.add(new ValueSingleTimeline("20150510", 300d));
	values.add(new ValueSingleTimeline("20150430", 8840d));
	values.add(new ValueSingleTimeline("20150424", 1120d));
	values.add(new ValueSingleTimeline("20150414", 0d));
	values.add(new ValueSingleTimeline("20150321", 280d));


	values.add(new ValueSingleTimeline("20150620", 1.23d));
	values.add(new ValueSingleTimeline("20150601", 4.52d));
	values.add(new ValueSingleTimeline("20150510", 3.48d));
	values.add(new ValueSingleTimeline("20150424", 9.85d));
	values.add(new ValueSingleTimeline("20150414", 0.05d));
	values.add(new ValueSingleTimeline("20150321", 2.80d));
	 */

	/*
	 */
	values.add(new ValueSingleTimeline("20150620", 10.23d));
	values.add(new ValueSingleTimeline("20150601", 40.52d));
	values.add(new ValueSingleTimeline("20150510", 36.48d));
	values.add(new ValueSingleTimeline("20150424", 91.85d));
	values.add(new ValueSingleTimeline("20150414", 6.05d));
	values.add(new ValueSingleTimeline("20150321", 20.80d));

	canvas.setFindings(values);

	

	shell.open();
	while (!shell.isDisposed()) {
	    if (!display.readAndDispatch())
		display.sleep();
	}
	display.dispose();
    }

}
