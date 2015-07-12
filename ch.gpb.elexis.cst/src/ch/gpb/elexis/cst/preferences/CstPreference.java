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
/**
 * @author daniel ludin ludin@swissonline.ch
 * 27.06.2015
 * 
 */
package ch.gpb.elexis.cst.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import ch.elexis.core.data.activator.CoreHub;
import ch.elexis.core.data.events.ElexisEvent;
import ch.elexis.core.data.events.ElexisEventDispatcher;
import ch.elexis.core.ui.preferences.SettingsPreferenceStore;
import ch.elexis.data.Anwender;

public class CstPreference extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
    public static String CST_IDENTIFIER_OMNIVORE = "ch.gpb.elexis.ident.omnivore";
    public static String CST_IDENTIFIER_BRIEFE = "ch.gpb.elexis.ident.briefe";

    public CstPreference() {
	super(Messages.Cst_Preference_Einstellungen, GRID);
	setPreferenceStore(new SettingsPreferenceStore(CoreHub.userCfg));

	initIdentifiers();
    }

    public static void initIdentifiers() {
	if (CoreHub.userCfg.get(CST_IDENTIFIER_OMNIVORE, "notset").equals("notset")) {
	    CoreHub.userCfg.set(CST_IDENTIFIER_OMNIVORE, getDefaultIdentifierOmnivore());
	}

	if (CoreHub.userCfg.get(CST_IDENTIFIER_BRIEFE, "notset").equals("notset")) {
	    CoreHub.userCfg.set(CST_IDENTIFIER_BRIEFE, getDefaultIdentifierBriefe());
	}

    }

    @Override
    protected void createFieldEditors() {
	addField(new StringFieldEditor(CST_IDENTIFIER_OMNIVORE, "Cst Omnivore Documents", getFieldEditorParent()));
	addField(new StringFieldEditor(CST_IDENTIFIER_BRIEFE, "Cst Briefe", getFieldEditorParent()));
    }

    public void init(IWorkbench workbench) {
	// TODO Auto-generated method stub
    }

    @Override
    public boolean performOk() {
	boolean ret = super.performOk();

	ElexisEventDispatcher.getInstance().fire(
		new ElexisEvent(CoreHub.actUser, Anwender.class, ElexisEvent.EVENT_USER_CHANGED));


	return ret;
    }

    public static String getDefaultIdentifierOmnivore() {
	return "CST";
    }

    public static String getDefaultIdentifierBriefe() {
	return "CST";
    }

}
