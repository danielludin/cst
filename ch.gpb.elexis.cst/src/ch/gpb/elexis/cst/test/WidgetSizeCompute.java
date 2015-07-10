package ch.gpb.elexis.cst.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WidgetSizeCompute {

  public static void main(String[] args) {
    final Display display = new Display();
    Shell shell = new Shell(display);

    shell.setLayout(new GridLayout());

    Button button = new Button(shell, SWT.PUSH | SWT.LEFT);
    button.setText("Button");

    System.out.println("Bounds: " + button.getBounds());
    System.out.println("computeSize: " + button.computeSize(100, SWT.DEFAULT));
    System.out.println("computeSize: " + button.computeSize(40, SWT.DEFAULT));
    System.out.println("computeSize: " + button.computeSize(SWT.DEFAULT, 100));
    System.out.println("computeSize: " + button.computeSize(SWT.DEFAULT, 20));
    System.out.println("computeSize: " + button.computeSize(SWT.DEFAULT, 15));
    System.out.println("computeSize: " + button.computeSize(100, 200));
    System.out.println("computeSize: " + button.computeSize(SWT.DEFAULT, SWT.DEFAULT));

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }
}