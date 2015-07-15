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
package ch.gpb.elexis.cst.data;

import java.util.List;

/*
 * 
 * DB Object for cstgroup_labitem_joint
 */
import ch.elexis.data.PersistentObject;
import ch.elexis.data.Query;
import ch.rgw.tools.JdbcLink;
import ch.rgw.tools.VersionInfo;

/**
 * @author daniel ludin ludin@swissonline.ch
 * 27.06.2015
 * 
 */

public class CstStateItem extends PersistentObject {

    private static final String TABLENAME = "cststateitems";
    public static final String VERSIONID = "VERSION";
    public static final String VERSION = "3.0.0";
    //public static final String GROUP_ITEM_TABLENAME = "cstgroup_labitem_joint";
    public static final String ITEMRANKING = "itemsRanking";
    private JdbcLink j = getConnection();

    static final String create =
	    "CREATE TABLE `cstgroups` ("
		    + "	`ID` VARCHAR(25) NOT NULL,"
		    + "	`lastupdate` BIGINT(20) NULL DEFAULT NULL,"
		    + "	`deleted` CHAR(1) NULL DEFAULT '0',"
		    + "	`ParentID` VARCHAR(25) NULL DEFAULT NULL,"
		    + "	`ProfileID` VARCHAR(25) NULL DEFAULT NULL,"
		    + "	`ItemType` CHAR(2) NULL DEFAULT NULL,"
		    + "	`Name` VARCHAR(30) NULL DEFAULT NULL,"
		    + "	`Description` VARCHAR(256) NULL DEFAULT NULL,"
		    + "	`Parameter` BLOB NULL, "
		    + "	PRIMARY KEY (`ID`)" + ")"
		    + " COlLATE='utf8_general_ci' "
		    + " ENGINE=InnoDB;"
		    + " INSERT INTO " + TABLENAME
		    + " (ID, name) VALUES (" +
		    JdbcLink.wrap(VERSIONID) + ","
		    + JdbcLink.wrap(VERSION) + ");";

    static {
	addMapping(TABLENAME,
		"name=Name",
		"description=Description",
		"mandantId=MandantID",
		"parentId=ParentID",
		"profileId=ProfileID",
		"itemType=ItemType",
		"parameter=Parameter");

	if (!tableExists(TABLENAME)) {
	    createOrModifyTable(create);
	} else {
	    // load a Record whose ID is 'VERSION' there we set ItemID as Value
	    CstStateItem version = load(VERSIONID);

	    VersionInfo vi = new VersionInfo(version.get("name"));
	    if (vi.isOlder(VERSION)) {
		// we should update eg. with createOrModifyTable(update.sql);
		// And then set the new version
		/**/
		/* TODO: this create seems to be unnecessary in other 
		 * examples of PersistenObject implementations, check this
		 * */
		// there is no version record yet, create it
		if (version.getName() == null) {
		    version.create(VERSIONID);
		}

		version.set("name", VERSION);
	    }
	}
    }

    public enum StateTypes {
	ACTION, DECISION, TRIGGER
    };

    public CstStateItem() {
	// TODO Auto-generated constructor stub
    }

    public CstStateItem(final String id) {
	super(id);
    }

    public static CstStateItem load(final String id) {
	return new CstStateItem(id);
    }

    public CstStateItem(String name, String description, String profileID, String parentID, String mandantID) {
	/*
	CstStateItem existing = getByTypeAndProfileAndMandant(name, profileID, parentID);
	if (existing != null) {
	    throw new IllegalArgumentException(
		    String
			    .format("Mapping for origin id [%s] - [%s] already exists can not create multiple instances.", //$NON-NLS-1$
				    name, description));
	}*/

	create(null);
	set("name", name);
	set("description", description);
	set("mandantId", mandantID);
	set("parentId", parentID);
	set("profileId", profileID);
    }

    public CstStateItem(String name, String description, String mandantID) {
	/*
	CstStateItem existing = getByTypeAndMandant(name, mandantID);
	if (existing != null) {
	    throw new IllegalArgumentException(
		    String
			    .format("Mapping for origin id [%s] - [%s] already exists can not create multiple instances.", //$NON-NLS-1$
				    name, description));
	}
	 */
	create(null);
	set("name", name);
	set("description", description);
	set("mandantId", mandantID);
    }

    public static CstStateItem getByTypeAndProfileAndMandant(String name, String profileId, String mandantId) {
	Query<CstStateItem> qbe = new Query<CstStateItem>(CstStateItem.class);
	qbe.add("ID", Query.NOT_EQUAL, VERSIONID); //$NON-NLS-1$
	qbe.add("name", Query.EQUALS, name);
	qbe.add("profileId", Query.EQUALS, profileId);
	qbe.add("mandantId", Query.EQUALS, mandantId);
	List<CstStateItem> res = qbe.execute();
	if (res.isEmpty()) {
	    return null;
	} else {
	    if (res.size() > 1) {
		throw new IllegalArgumentException(String.format(
			"There is already a category of name [%s] - [%s]", name));
	    }
	    return res.get(0);
	}
    }

    public static CstStateItem getByTypeAndMandant(String name, String mandantId) {
	Query<CstStateItem> qbe = new Query<CstStateItem>(CstStateItem.class);
	qbe.add("ID", Query.NOT_EQUAL, VERSIONID); //$NON-NLS-1$
	qbe.add("name", Query.EQUALS, name);
	qbe.add("mandantId", Query.EQUALS, mandantId);
	List<CstStateItem> res = qbe.execute();
	if (res.isEmpty()) {
	    return null;
	} else {
	    if (res.size() > 1) {
		throw new IllegalArgumentException(String.format(
			"There is already a category of name [%s] - [%s]", name));
	    }
	    return res.get(0);
	}
    }


    /**
     * Should only return one object!
     * @param child
     * @return
     */
    public static List<CstStateItem> getParent(CstStateItem child) {
	Query<CstStateItem> qbe = new Query<CstStateItem>(CstStateItem.class);
	qbe.add("ID", Query.NOT_EQUAL, VERSIONID);
	qbe.add("parentId", Query.EQUALS, child.getId());
	return qbe.execute();
    }

    public static List<CstStateItem> getChildren(CstStateItem parent) {
	Query<CstStateItem> qbe = new Query<CstStateItem>(CstStateItem.class);
	qbe.add("ID", Query.NOT_EQUAL, VERSIONID);
	qbe.add("parentId", Query.EQUALS, parent.getId());
	return qbe.execute();
    }

    /**
     * root items are all items of this profile that have no parent id
     * @param profile
     * @return
     */
    public static List<CstStateItem> getRootItems(CstProfile profile) {
	Query<CstStateItem> qbe = new Query<CstStateItem>(CstStateItem.class);
	qbe.add("ID", Query.NOT_EQUAL, VERSIONID);
	qbe.add("profileId", Query.EQUALS, profile.getId());
	qbe.add("parentId", Query.EQUALS, null);
	return qbe.execute();
    }

    public static List<CstStateItem> getStateItems(CstProfile profile) {
	Query<CstStateItem> qbe = new Query<CstStateItem>(CstStateItem.class);
	qbe.add("ID", Query.NOT_EQUAL, VERSIONID);
	qbe.add("profileId", Query.EQUALS, profile.getId());
	return qbe.execute();
    }

    public static List<CstStateItem> getStateItems(CstProfile profile, String mandantId) {
	Query<CstStateItem> qbe = new Query<CstStateItem>(CstStateItem.class);
	qbe.add("ID", Query.NOT_EQUAL, VERSIONID);
	qbe.add("mandantId", Query.EQUALS, mandantId);
	qbe.add("profileId", Query.EQUALS, profile.getId());
	return qbe.execute();
    }

    public static List<CstStateItem> getStateItems() {
	Query<CstStateItem> qbe = new Query<CstStateItem>(CstStateItem.class);
	qbe.add("ID", Query.NOT_EQUAL, VERSIONID);
	return qbe.execute();
    }




    // TODO: do a hard delete, else we clutter the table
    @Override
    public boolean delete() {
	int ret = getConnection().exec(
		"DELETE FROM  " + TABLENAME + " WHERE ID =" + getWrappedId());

	if (ret == 0) {
	    return false;
	} else {
	    return true;
	}

    }

    public void setName(String name) {
	set("name", name);
    }

    public String getName() {
	return get("name");
    }

    public void setDescription(String description) {
	set("description", description);
    }

    public String getDescription() {
	return get("description");
    }

    public void setParentId(String parentId) {
	set("parentId", parentId);
    }

    public String getParentId() {
	return get("parentId");
    }

    public void setProfileId(String profileId) {
	set("profileId", profileId);
    }

    public String getProfileId() {
	return get("profileId");
    }

    public void setMandantId(String mandantId) {
	set("mandantId", mandantId);
    }

    public String getMandantId() {
	return get("mandantId");
    }


    @Override
    public String getLabel() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected String getTableName() {
	return TABLENAME;
    }

    // for the View content provider
    public Object getParent() {
	return new Object();
    }

}
