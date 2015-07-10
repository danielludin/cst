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
package ch.gpb.elexis.cst.view.profileeditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.elexis.core.ui.UiDesk;

/**
 * this is the base composite for the custom composites that make up the profile editor
 * @author daniel
 *
 */
public abstract class CstComposite extends Composite {
    Color COLOR_RED;
    Color GREEN;
    Color BLACK;
    Color WHITE;

    Font titelFont;
    Font fontBold; //$NON-NLS-1$
    Font fontNormal; //$NON-NLS-1$

    Color titelColor = UiDesk.getColorFromRGB("D90A0A");
    protected Logger log = LoggerFactory.getLogger(CstComposite.class.getName());

    public CstComposite(Composite parent, int style) {
	super(parent, style);

	// TODO Auto-generated constructor stub
	COLOR_RED = UiDesk.getColorFromRGB("D90A0A");
	GREEN = UiDesk.getColorFromRGB("77C742");
	BLACK = UiDesk.getColorFromRGB("000000");
	WHITE = UiDesk.getColorFromRGB("000000");

	//titelFont = UiDesk.getFont(Preferences.USR_SMALLFONT);
	titelFont = UiDesk.getFont("Helvetica", 12, SWT.BOLD); //$NON-NLS-1$
	fontBold = UiDesk.getFont("Helvetica", 12, SWT.BOLD); //$NON-NLS-1$
	fontNormal = UiDesk.getFont("Helvetica", 9, SWT.NORMAL); //$NON-NLS-1$

    }


}
