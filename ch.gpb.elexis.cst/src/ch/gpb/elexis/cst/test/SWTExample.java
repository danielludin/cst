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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class SWTExample 
{
public static void main (String [] args) 
{
    Display display = new Display ();
    Shell shell = new Shell (display);
    shell.setLayout (new RowLayout ());

    DateTime calendar = new DateTime (shell, SWT.CALENDAR);
    calendar.addSelectionListener (new SelectionAdapter() {
        public void widgetSelected (SelectionEvent e) {
            System.out.println ("calendar date changed");
        }
    });

    DateTime time = new DateTime (shell, SWT.TIME);
    time.addSelectionListener (new SelectionAdapter () {
        public void widgetSelected (SelectionEvent e) {
            System.out.println ("time changed");
        }
    });

    shell.pack ();
    shell.open ();
    while (!shell.isDisposed ()) {
        if (!display.readAndDispatch ()) display.sleep ();
    }
    display.dispose ();
}

}