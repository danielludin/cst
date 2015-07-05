package ch.gpb.elexis.cst.data;
/*
 * POJO for CST Display
 */
public class CstLabItem {
	//Name, Kürzel, Einheit, Sequenzierungsmerkmal und natürlich der Name der Gruppe
	private String name;
	private String unit;
	private int sequenceNumber;
	private String groupName;  // will be replaced by inner join to group table
	private boolean isActive;
	
	
	public CstLabItem(String name, String unit, int sequenceNumber,
			String groupName,boolean active) {
		super();
		this.name = name;
		this.unit = unit;
		this.sequenceNumber = sequenceNumber;
		this.groupName = groupName;
		this.isActive = active;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
