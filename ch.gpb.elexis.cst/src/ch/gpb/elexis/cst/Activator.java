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
package ch.gpb.elexis.cst;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import ch.elexis.core.ui.UiDesk;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    public static final String IMG_ARROW_UP_NAME = "arrow-up";
    public static final String IMG_ARROW_UP_PATH = "icons/arrow-up.png";
    public static final String IMG_ARROW_DOWN_NAME = "arrow-down";
    public static final String IMG_ARROW_DOWN_PATH = "icons/arrow-down.png";
    public static final String IMG_ACTIVE_NAME = "active";
    public static final String IMG_ACTIVE_PATH = "icons/active.png";
    public static final String IMG_PASSIVE_NAME = "passive";
    public static final String IMG_PASSIVE_PATH = "icons/inactive.png";
    public static final String IMG_PDF_NAME = "pdficon_large-32";
    public static final String IMG_PDF_PATH = "icons/pdficon_large-32.png";
    public static final String IMG_PNG_NAME = "png-icon-32";
    public static final String IMG_PNG_PATH = "icons/png-icon-32.png";
    public static final String IMG_LINE_NAME = "line-2px";
    public static final String IMG_LINE_PATH = "icons/line-2px-transp.png";
    public static final String IMG_POINTER_NAME = "pointer.png";
    public static final String IMG_POINTER_PATH = "icons/pointer.png";
    public static final String IMG_CSTGROUP_NAME = "icon-cat-1-16.png";
    public static final String IMG_CSTGROUP_PATH = "icons/icon-cat-1-16.png";
    public static final String IMG_CSTPROFILE_NAME = "icon-cat-2-16.png";
    public static final String IMG_CSTPROFILE_PATH = "icons/icon-cat-2-16.png";
    // The plug-in ID png-icon-32
    public static final String PLUGIN_ID = "ch.gpb.elexis.cst"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
	super.start(context);
	plugin = this;
	UiDesk.getImageRegistry().put(IMG_ACTIVE_NAME, Activator.getImageDescriptor(IMG_ACTIVE_PATH));
	UiDesk.getImageRegistry().put(IMG_PASSIVE_NAME, Activator.getImageDescriptor(IMG_PASSIVE_PATH));
	UiDesk.getImageRegistry().put(IMG_ARROW_UP_NAME, Activator.getImageDescriptor(IMG_ARROW_UP_PATH));
	UiDesk.getImageRegistry().put(IMG_ARROW_DOWN_NAME, Activator.getImageDescriptor(IMG_ARROW_DOWN_PATH));
	UiDesk.getImageRegistry().put(IMG_PDF_NAME, Activator.getImageDescriptor(IMG_PDF_PATH));
	UiDesk.getImageRegistry().put(IMG_PNG_NAME, Activator.getImageDescriptor(IMG_PNG_PATH));
	UiDesk.getImageRegistry().put(IMG_LINE_NAME, Activator.getImageDescriptor(IMG_LINE_PATH));
	UiDesk.getImageRegistry().put(IMG_POINTER_NAME, Activator.getImageDescriptor(IMG_POINTER_PATH));
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
	plugin = null;
	super.stop(context);
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static Activator getDefault() {
	return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given
     * plug-in relative path
     *
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
	return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }
}
