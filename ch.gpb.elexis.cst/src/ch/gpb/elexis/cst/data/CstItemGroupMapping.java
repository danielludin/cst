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

public class CstItemGroupMapping extends PersistentObject {
	
	private static final String TABLENAME = "cstgroup_labitem_joint";
	public static final String VERSIONID = "VERSION"; 
	public static final String VERSION = "3.0.0";
	
	static final String create = "CREATE TABLE `cstgroup_labitem_joint` ("
			+ "	`ID` VARCHAR(25) NULL DEFAULT NULL,	"
			+ "`deleted` CHAR(1) NULL DEFAULT '0',	"
			+ "`lastupdate` BIGINT(20) NULL DEFAULT NULL,	"
			+ "`GroupID` VARCHAR(25) NULL DEFAULT NULL,	"
			+ "`ItemID` VARCHAR(25) NULL DEFAULT NULL,	"
			+ "`Comment` TEXT NULL,  "
			+ "UNIQUE INDEX `GroupID` (`GroupID`, `ItemID`)) "
			+ "COLLaTE='utf8_general_ci' ENGINE=InnoDB;"
			+ "INSERT INTO "
			+ TABLENAME + " (ID,\"itemID\") VALUES ("
			+ JdbcLink.wrap(VERSIONID) + "," + JdbcLink.wrap(VERSION) + ");";
;
	
	
	
	static {
		addMapping(TABLENAME, "groupId=GroupID", "itemId=ItemID", "comment=Comment");
	
		if (!tableExists(TABLENAME)) {
			createOrModifyTable(create);
			log.debug("Creating table:\r\n" + create);

		} else {
			// load a Record whose ID is 'VERSION' there we set ItemID as Value
			CstItemGroupMapping version = load(VERSIONID);
			
			VersionInfo vi = new VersionInfo(version.get("itemId"));
			if (vi.isOlder(VERSION)) {
				// we should update eg. with createOrModifyTable(update.sql);
				// And then set the new version
				/**/
				/* TODO: this create seems to be unnecessary in other 
				 * examples of PersistenObject implementations, check this
				 * */
				// there is no version record yet, create it
				if (version.getItemId() == null) {
					version.create(VERSIONID);
				}
				
				version.set("itemId", VERSION);
			}
		}
	}
	public CstItemGroupMapping(){
		// TODO Auto-generated constructor stub
	}
	
	public CstItemGroupMapping(final String id){
		super(id);
	}
	
	public static CstItemGroupMapping load(final String id){
		return new CstItemGroupMapping(id);
	}
	
	public CstItemGroupMapping(String groupId, String itemId, String comment){
		CstItemGroupMapping existing = getByGroupAndItemId(groupId, itemId);
		if (existing != null) {
			throw new IllegalArgumentException(
				String
					.format(
						"Mapping for origin id [%s] - [%s] already exists can not create multiple instances.", //$NON-NLS-1$
						groupId, itemId));
		}
		
		create(null);
		set("groupId", groupId);
		set("itemId", itemId);
		set("comment", comment);
	}

	public static CstItemGroupMapping getByGroupAndItemId(String groupId, String itemId){
		Query<CstItemGroupMapping> qbe = new Query<CstItemGroupMapping>(CstItemGroupMapping.class);
		qbe.add("ID", Query.NOT_EQUAL, VERSIONID); //$NON-NLS-1$
		qbe.add("groupId", Query.EQUALS, groupId);
		qbe.add("itemId", Query.EQUALS, itemId);
		List<CstItemGroupMapping> res = qbe.execute();
		if (res.isEmpty()) {
			return null;
		} else {
			if (res.size() > 1) {
				throw new IllegalArgumentException(String.format(
					"Found more then 1 mapping for origin id [%s] - [%s]", groupId, itemId));
			}
			return res.get(0);
		}
	}
	 
	
	public static List<CstItemGroupMapping> getByLabItemId(String labItemId){
		Query<CstItemGroupMapping> qbe = new Query<CstItemGroupMapping>(CstItemGroupMapping.class);
		qbe.add("ID", Query.NOT_EQUAL, VERSIONID); 
		qbe.add("itemId", Query.EQUALS, labItemId);
		return qbe.execute();
	}
	

	public void setGroupId(String groupId) {
		set("groupId", groupId);
	}
	public String getGroupId() {
		return get("groupId");
	}
	

	public void setComment(String comment) {
		set("comment", comment);
	}
	public String getComment() {
		return get("comment");
	}

	
	public void setItemId(String itemId) {
		set("itemId", itemId);
	}
	public String getItemId() {
		return get("itemId");
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

}
