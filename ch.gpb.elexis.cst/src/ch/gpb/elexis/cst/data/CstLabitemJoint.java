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

public class CstLabitemJoint extends PersistentObject {
	
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
			+ " UNIQUE INDEX `GroupID` (`GroupID`, `ItemID`)) "
			+ " COLLAtE='utf8_general_ci' ENGINE=InnoDB; "

			+ "INSERT INTO " + TABLENAME + " (ID,\"itemID\") VALUES ("
			+ JdbcLink.wrap(VERSIONID) + "," + JdbcLink.wrap(VERSION) + ");";
	
	static {
		addMapping(TABLENAME, 
				"groupId=GroupID", 
				"itemId=ItemID",
				"ranking=Ranking" 
				);

		if (!tableExists(TABLENAME)) {
			createOrModifyTable(create);
			log.debug("Creating table:\r\n" + create);
		} else {
			// load a Record whose ID is 'VERSION' there we set ItemID as Value
			CstLabitemJoint version = load(VERSIONID);

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
	public CstLabitemJoint(){
		// TODO Auto-generated constructor stub
	}
	
	public CstLabitemJoint(final String id){
		super(id);
	}
	
	public static CstLabitemJoint load(final String id){
		return new CstLabitemJoint(id);
	}
	
	public CstLabitemJoint(String groupId, String itemId, int ranking){
		CstLabitemJoint existing = getByItemAndGroupId(groupId, itemId);
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
	}


	
	
	public static CstLabitemJoint getByItemAndGroupId(String groupId, String itemId){
		Query<CstLabitemJoint> qbe = new Query<CstLabitemJoint>(CstLabitemJoint.class);
		qbe.add("ID", Query.NOT_EQUAL, VERSIONID); //$NON-NLS-1$
		qbe.add("groupId", Query.EQUALS, groupId); 
		qbe.add("itemId", Query.EQUALS, itemId); 
		List<CstLabitemJoint> res = qbe.execute();
		if (res.isEmpty()) {
			return null;
		} else {
			if (res.size() > 1) {
				throw new IllegalArgumentException(String.format(
					"There is already a category of name [%s] - [%s]", groupId));
			}
			return res.get(0);
		}
	}

	public static List<CstLabitemJoint> getByGroupId(String groupId){
		Query<CstLabitemJoint> qbe = new Query<CstLabitemJoint>(CstLabitemJoint.class);
		qbe.add("ID", Query.NOT_EQUAL, VERSIONID); 
		qbe.add("groupId", Query.EQUALS, groupId);
		return qbe.execute();
	}
	public static List<CstLabitemJoint> getByItemId(String itemId){
		Query<CstLabitemJoint> qbe = new Query<CstLabitemJoint>(CstLabitemJoint.class);
		qbe.add("ID", Query.NOT_EQUAL, VERSIONID); 
		qbe.add("itemId", Query.EQUALS, itemId);
		return qbe.execute();
	}
	
	

	

	
	@Override
	public boolean delete(){
		getConnection().exec(
			"DELETE FROM cstgroup_labitem_joint WHERE GroupID =" + getWrappedId());
		return super.delete();
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

	public void setGroupId(String groupId) {
		set("groupId", groupId);
	}
	public String getGroupId() {
		return get("groupId");
	}
	

	public void setItemId(String itemId) {
		set("itemId", itemId);
	}
	public String getItemId() {
		return get("itemId");
	}
	
	public void setRanking(String ranking) {
		set("ranking", ranking);
	}
	public String getRanking() {
		return get("ranking");
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
