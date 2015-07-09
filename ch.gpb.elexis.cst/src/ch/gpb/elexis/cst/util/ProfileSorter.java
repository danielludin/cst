package ch.gpb.elexis.cst.util;

import java.text.Collator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import ch.gpb.elexis.cst.data.CstProfile;

public class ProfileSorter extends ViewerSorter {
    private int sortColumn = 0;
    private boolean sortReverse = false;

    public ProfileSorter() {
	// TODO Auto-generated constructor stub
    }

    public ProfileSorter(Collator collator) {
	super(collator);
	// TODO Auto-generated constructor stub
    }

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
	if ((e1 instanceof CstProfile) && (e2 instanceof CstProfile)) {
	    CstProfile d1 = (CstProfile) e1;
	    CstProfile d2 = (CstProfile) e2;
	    String c1 = "";
	    String c2 = "";
	    switch (sortColumn) {
	    case 0:
		c1 = d1.getName();
		c2 = d2.getName();
		break;
	    case 1:
		c1 = d1.getDescription();
		c2 = d2.getDescription();
		break;
	    case 2:
		c1 = d1.getValidFrom();
		c2 = d2.getValidFrom();
		break;
	    }
	    if (sortReverse) {
		return c1.compareTo(c2);
	    } else {
		return c2.compareTo(c1);
	    }
	}
	return 0;
    }

}
