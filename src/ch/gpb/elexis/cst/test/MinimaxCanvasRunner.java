package ch.gpb.elexis.cst.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ch.gpb.elexis.cst.data.MinimaxValue;
import ch.gpb.elexis.cst.widget.MinimaxCanvas;

public class MinimaxCanvasRunner {

    public static void main(String[] args) {
	Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setSize(794, 400);
	shell.setLayout(new RowLayout());

	shell.setText("Composite Example");

	final Composite composite = new Composite(shell, SWT.NONE);
	GridLayout gridLayout = new GridLayout();
	gridLayout.numColumns = 1;
	composite.setLayout(gridLayout);


	//ValueSingleTimelineCanvas canvas = new ValueSingleTimelineCanvas(shell, SWT.NONE, "Test", "kg");

	MinimaxCanvas canvas = new MinimaxCanvas(shell, SWT.None);

	List<MinimaxValue> values = new ArrayList<MinimaxValue>();

	MinimaxValue value = new MinimaxValue();
	value.setDateStartOfSpan1(new Date());
	value.setDateEndOfSpan1(new Date());

	value.setMinOfSpan1(21.2);
	value.setMaxOfSpan1(33.2);
	value.setMinOfSpan2(11.2);
	value.setMaxOfSpan2(44.2);
	value.setMinOfSpan3(21.2);
	value.setMaxOfSpan3(55.2);

	value.setDateStartOfSpan2(new Date());
	value.setDateEndOfSpan2(new Date());

	value.setDateStartOfSpan3(new Date());
	value.setDateEndOfSpan3(new Date());

	values.add(new MinimaxValue());

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

	canvas.setFinding(value);

	

	shell.open();
	while (!shell.isDisposed()) {
	    if (!display.readAndDispatch())
		display.sleep();
	}
	display.dispose();
    }

}
