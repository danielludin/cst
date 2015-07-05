package ch.gpb.elexis.cst.data;


public class CstDummyDataProvider {

	public static Object[] getDataForCstLabItemView(){
		
		CstLabItem[] items = new CstLabItem[3];
		CstLabItem item1 = new CstLabItem("Blutwert1", "mg/L", 1, "Gruppe 1", true);
		CstLabItem item2 = new CstLabItem("Blutwert2", "mg/L", 2, "Gruppe 1", false);
		CstLabItem item3 = new CstLabItem("Blutwert3", "mg/L", 3, "Gruppe 1", true);
		
		items[0] = item1;
		items[1] = item2;
		items[2] = item3;
		
		
		return items;
	}
}
