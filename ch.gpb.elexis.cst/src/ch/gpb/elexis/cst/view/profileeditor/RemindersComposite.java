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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import ch.elexis.core.data.activator.CoreHub;
import ch.gpb.elexis.cst.data.CstProfile;
import ch.gpb.elexis.cst.data.CstStateItem;
import ch.gpb.elexis.cst.preferences.Messages;

public class RemindersComposite extends CstComposite {

    CstProfile aProfile;
    TreeViewer treeviewer;
    Action actionAddObject;
    Action actionDeleteObject;

    public RemindersComposite(Composite parent) {
	super(parent, SWT.NONE);

	GridLayout gridLayout = new GridLayout(1, false);
	setLayout(gridLayout);

	createLayout(this);

	treeviewer = new TreeViewer(this, SWT.BORDER);
	Tree tree_1 = treeviewer.getTree();
	GridData gd_tree_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
	gd_tree_1.heightHint = 260;
	gd_tree_1.widthHint = 360;
	tree_1.setLayoutData(gd_tree_1);
	//tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	treeviewer.setContentProvider(new ViewContentProvider());
	treeviewer.setLabelProvider(new ViewLabelProvider());
	//tree.setInput(File.listRoots());
	//tree.setInput(CstStateItem.getRootItems());

	// StateItems with parent = null are root objects
	//tree.setInput(CstStateItem.getStateItems(null));

	Button btnNewAction = new Button(this, SWT.NONE);
	btnNewAction.setText("New Item");
	btnNewAction.addSelectionListener(new NewItemListener());

	Button btnExpandAll = new Button(this, SWT.NONE);
	btnExpandAll.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
	btnExpandAll.setText("Expand All");
	btnExpandAll.addSelectionListener(new ExpandAllListener());

	MenuManager menuMgr = new MenuManager();
	Menu menu = menuMgr.createContextMenu(treeviewer.getControl());
	menuMgr.addMenuListener(new IMenuListener() {
	    @Override
	    public void menuAboutToShow(IMenuManager manager) {
		if (treeviewer.getSelection().isEmpty()) {
		    return;
		}

		if (treeviewer.getSelection() instanceof IStructuredSelection) {
		    IStructuredSelection selection = (IStructuredSelection) treeviewer.getSelection();

		    /*
		    DatabaseModelObject object = (DatabaseModelObject)selection.getFirstElement();

		    if (object.getType() == DATABASE_OBJECT_TYPE.TABLE){
		      manager.add(new ShowTableDataAction(SWTApp.this));
		    }*/
		    manager.add(actionAddObject);
		    manager.add(actionDeleteObject);

		}
	    }
	});

	menuMgr.setRemoveAllWhenShown(true);
	treeviewer.getControl().setMenu(menu);

	makeActions();
    }

    // dynamic Layout elements
    private void createLayout(Composite parent) {

	Label labelTherapievorschlag = new Label(parent, SWT.NONE);
	labelTherapievorschlag.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
	labelTherapievorschlag.setText(Messages.CstProfileEditor_Therapievorschlag);
	labelTherapievorschlag.setSize(200, 20);

    }

    class NewItemListener extends SelectionAdapter {
	@Override
	public void widgetSelected(final SelectionEvent e) {
	    //CstStateItem selItem = (CstStateItem) e.getSource();
	    //addObject(selItem);
	    addObject(null);

	    treeviewer.setInput(CstStateItem.getRootItems(aProfile));

	    treeviewer.refresh();
	}
    }

    class ExpandAllListener extends SelectionAdapter {
	@Override
	public void widgetSelected(final SelectionEvent e) {
	    //CstStateItem selItem = (CstStateItem) e.getSource();
	    //addObject(selItem);
	    expandAll();
	}
    }

    public void addObject(CstStateItem selItem) {

	if (selItem != null) {

	    CstStateItem item = new CstStateItem("ACTION", "", aProfile.getId(), selItem.getId(),
		    CoreHub.actMandant.getId());
	    System.out.println("created CstStateItem with parent: " + item.getId());
	} else {
	    CstStateItem item = new CstStateItem("ACTION", "", aProfile.getId(), null,
		    CoreHub.actMandant.getId());
	    System.out.println("created CstStateItem without parent: " + item.getId());
	}
	treeviewer.refresh();

    }

    private void makeActions() {
	actionAddObject = new Action() {
	    public void run() {
		IStructuredSelection selection = (IStructuredSelection) treeviewer.getSelection();
		System.out.println("sel tree: " + selection.toString());
		CstStateItem selItem = (CstStateItem) selection.getFirstElement();
		addObject(selItem);
		expandAll();

	    }
	};
	actionAddObject.setText("Add Item");
	actionAddObject.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
		.getImageDescriptor(ISharedImages.IMG_OBJ_ADD));

	actionDeleteObject = new Action() {
	    public void run() {
		IStructuredSelection selection = (IStructuredSelection) treeviewer.getSelection();
		System.out.println("sel tree: " + selection.toString());
		CstStateItem selItem = (CstStateItem) selection.getFirstElement();

		List<CstStateItem> result = new ArrayList<CstStateItem>();
		List<CstStateItem> itemsToDelete = getChildrenToDelete(selItem, result);
		itemsToDelete.add(selItem);

		for (CstStateItem cstStateItem : itemsToDelete) {
		    cstStateItem.delete();
		}

		treeviewer.setInput(CstStateItem.getRootItems(aProfile));
		treeviewer.refresh();
		//treeviewer.setExpandedElements(new Object[] { selItem.getParent() });
		expandAll();

	    }
	};
	actionDeleteObject.setText("Delete Item");
	actionDeleteObject.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
		.getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));

    }

    private static List<CstStateItem> getChildrenToDelete(CstStateItem parent, List<CstStateItem> result) {
	//ArrayList<CstStateItem> result = new ArrayList<CstStateItem>();
	List<CstStateItem> items = CstStateItem.getChildren(parent);

	for (CstStateItem item : items) {
	    /*
	     result.add(item);
	     List<CstStateItem> children = RemindersComposite.getChildrenToDelete(item, result);
	     result.addAll(children);
	     */
	    result.add(item);

	    if (!CstStateItem.getChildren(item).isEmpty()) {
		RemindersComposite.getChildrenToDelete(item, result);
	    }

	}

	return result;
    }

    private void expandAll() {
	/*
	Tree tree = treeviewer.getTree();
	for (TreeItem item : tree.getItems()) {
	    item.setExpanded(true);
	}
	*/
	treeviewer.expandAll();
    }
    class ViewLabelProvider extends StyledCellLabelProvider {
	@Override
	public void update(ViewerCell cell) {
	    /*
	    Object element = cell.getElement();
	    StyledString text = new StyledString();
	    File file = (File) element;
	    if (file.isDirectory()) {
	    text.append(getFileName(file));
	    cell.setImage(image);
	    String[] files = file.list();
	    if (files != null) {
	      text.append(" (" + files.length + ") ",
	          StyledString.COUNTER_STYLER);
	    }
	    } else {
	    text.append(getFileName(file));
	    }
	    cell.setText(text.toString());
	    cell.setStyleRanges(text.getStyleRanges());
	    super.update(cell);
	     */

	    CstStateItem element = (CstStateItem) cell.getElement();
	    StyledString text = new StyledString();
	    text.append(element.getId());

	    cell.setText(text.toString());
	    cell.setStyleRanges(text.getStyleRanges());
	    super.update(cell);

	}
    }

    class ViewContentProvider implements ITreeContentProvider {
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object[] getElements(Object inputElement) {
	    //return (File[]) inputElement;
	    /*
	    Object[] elements = (Object[]) inputElement;
	    return elements;
	    */
	    LinkedList list = (LinkedList) inputElement;
	    return list.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
	    CstStateItem file = (CstStateItem) parentElement;
	    //return file.listFiles();

	    List children = CstStateItem.getChildren(file);
	    return children.toArray();

	}

	@Override
	public Object getParent(Object element) {
	    /*
	    File file = (File) element;
	    return file.getParentFile();
	    */

	    System.out.println("getParent class: " + element.getClass());
	    if (element instanceof LinkedList) {
		LinkedList list = (LinkedList) element;
		Iterator it = list.iterator();
		if (it.hasNext()) {

		    CstStateItem child2 = (CstStateItem) it.next();
		    return child2;
		} else {
		    return null;
		}

	    }

	    CstStateItem child = (CstStateItem) element;
	    return CstStateItem.getParent(child);

	}

	@Override
	public boolean hasChildren(Object element) {
	    /*
	      File file = (File) element;
	      if (file.isDirectory()) {
	    return true;
	      }
	      return false;
	      */
	    CstStateItem child = (CstStateItem) element;
	    List<CstStateItem> children = CstStateItem.getChildren(child);
	    return !children.isEmpty();

	}

    }

    public void clear() {
    }

    public void setProfile(CstProfile aProfile) {
	this.aProfile = aProfile;
	this.treeviewer.setInput(CstStateItem.getRootItems(aProfile));
    }

}