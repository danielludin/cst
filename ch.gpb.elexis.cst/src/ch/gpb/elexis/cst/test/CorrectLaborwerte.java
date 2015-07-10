package ch.gpb.elexis.cst.test;

import java.io.Closeable;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class CorrectLaborwerte {
	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static final String AB = "abcdefghijklmnopqrstuvwxyz";
	static final String NUM = "0123456789";
	static Random rnd = new Random();
	
	String randomString(int len){
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
	
	String randomNumericString(int len){
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(NUM.charAt(rnd.nextInt(NUM.length())));
		return sb.toString();
	}
	
	public void readDataBase(String sJdbcUrl) throws Exception{
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			// con =
			// DriverManager.getConnection("jdbc:mysql://localhost/world?user=root&password=root");

			con = DriverManager.getConnection(sJdbcUrl);

			String sql =
				"SELECT lw.ID FROM laborwerte lw, kontakt k, laboritems li "
					+ "where li.ID = lw.ItemID "
					+ "and k.ID = lw.PatientID and lw.datum > '20150201' and lw.datum < '20150210' "
					+ "and li.Gruppe = 'Eigenlabor: Sysmex' and lw.deleted = '1' and k.ID in ('10089','10120','10251','10463','10547','10738','10841','11282','11607','11723','11724','1320','1322','1737', '1839','20629','20684','21177','213','21433','21515','21860','22204','22248','22344','22398','22467','22770','23312','23333', '23501','23992','24349','24416','24475','24504','2452','2559','27','339','391','500','837','887','998','B487907ec562cdffd04888','b725919d05d63bf28073','D34cff88bfc5228c0431','ddadf305597667250362','gb2cd48928cd6617802014','h138271b632875ac006415','j8f9d0c2a02cf88c80766','m2d6f2accf5235a4c01775','p2e46e16e3d19d12603909','p3599725b29de7b1303209','p4977a2fe65b54f6f0109','p4d6dadd911f632c0509','P650ed9fb9761e4ce04949','p659659f7b686361102609','u234b16fc1c0a188301356','U6c21f717398978705592','v3068dde13b24af2202022','V323dc2f1f8de5de001435','W75ef3e551927824401129','x17f9c73bb9e3ec0a02020','Y5e00e0bba2e73e2d01428','z3a5ac5dd835f04120255','zb9ebfa8bdc1f75360805')";

			System.out.println("Preparing SELECT.");
			// statements allow to issue SQL queries to the database
			stmt = con.createStatement();
			// resultSet gets the result of the SQL query
			rs = stmt.executeQuery(sql);

			System.out.println("Executing SELECT.");

			while (rs.next()) {

				String sID = rs.getString("ID");

				System.out.println("Processing Laborwert with ID: " + sID);

				ps = con.prepareStatement("update Laborwerte set deleted = '0' where ID = ? ");
				
				ps.setString(1, sID);
				
				int result = ps.executeUpdate();
				System.out.println("updated: " + result + "/ ID: " + sID);
				if (result == 0) {
					System.out.println("ATTENTION: nothing updated: ");
				}

			}

			/*
			 * // preparedStatements can use variables and are more efficient ps =
			 * con.prepareStatement(
			 * "insert into  WORLD.COMMENTS values (default, ?, ?, ?, ? , ?, ?)" ); //
			 * "myuser, webpage, datum, summary, COMMENTS from WORLD.COMMENTS"); // parameters start
			 * with 1 ps.setString(1, "Test"); ps.setString(2, "TestEmail"); ps.setString(3,
			 * "TestWebpage"); //preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			 * java.util.Date date = new Date(); java.sql.Date now = new
			 * java.sql.Date(date.getTime()); ps.setDate(4, now); ps.setString(5, "TestSummary");
			 * ps.setString(6, "TestComment"); ps.executeUpdate();
			 * 
			 * ps = con .prepareStatement(
			 * "SELECT myuser, webpage, datum, summary, COMMENTS from WORLD.COMMENTS" ); rs =
			 * ps.executeQuery(); writeResultSet(rs);
			 */

			/*
			 * // remove again the insert comment preparedStatement = connect .prepareStatement
			 * ("delete from WORLD.COMMENTS where myuser= ? ; "); preparedStatement.setString(1,
			 * "Test"); preparedStatement.executeUpdate();
			 * 
			 * resultSet = statement .executeQuery("select * from WORLD.COMMENTS");
			 * writeMetaData(resultSet);
			 */

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw e;
		} finally {
			close();
		}

	}
	
	private void writeMetaData(ResultSet resultSet) throws SQLException{
		// now get some metadata from the database
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}
	
	private void writeResultSet(ResultSet resultSet) throws SQLException{
		// resultSet is initialised before the first data set
		while (resultSet.next()) {
			// it is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g., resultSet.getSTring(2);
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summary = resultSet.getString("summary");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("Summary: " + summary);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
	}

	// you need to close all three to make sure
	private void close(){
		/*
		 * close(resultSet); close(statement); close(connect);
		 */

		try {
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.getMessage();
		}

	}
	
	private void close(Closeable c){
		try {
			if (c != null) {
				c.close();
			}
		} catch (Exception e) {
			// don't throw now as it might leave following closables in
			// undefined state
		}
	}
	
	public static void main(String[] args) throws Exception{
		// prompt the user to enter their name
		System.out.println("Enter the JDBC url(or \"default\"):");
		System.out
			.println("Example: jdbc:mysql://localhost/DCGBP01?user=elexisuser&password=elexis");

		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		System.out.println("Reading input from console using Scanner in Java ");
		System.out.println("Please enter your input: ");
		String input = scanner.nextLine();
		if (!input.startsWith("jdbc:mysql://")) {
			if (!input.equals("default")) {
				System.out.println("Invalid Url, it must start with jdbc:mysql://");
				System.exit(0);
				
			} else {
				input = "jdbc:mysql://localhost/elexisprod?user=root&password=root";
				System.out.println("Using default: " + input);
			}
		}

		CorrectLaborwerte dao = new CorrectLaborwerte();
		dao.readDataBase(input);
	}

}
