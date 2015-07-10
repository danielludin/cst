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
package ch.gpb.elexis.cst.test;



import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class MySQLAccess2 {
	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static final String AB = "abcdefghijklmnopqrstuvwxyz";
	static final String NUM = "0123456789";
	static Random rnd = new Random();

	String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	String randomNumericString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(NUM.charAt(rnd.nextInt(NUM.length())));
		return sb.toString();
	}

	public void readDataBase(String sJdbcUrl) throws Exception {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// setup the connection with the DB.
			//con = DriverManager.getConnection("jdbc:mysql://localhost/world?user=root&password=root");

			con = DriverManager.getConnection(sJdbcUrl);

			// statements allow to issue SQL queries to the database
			stmt = con.createStatement();
			// resultSet gets the result of the SQL query
			rs = stmt.executeQuery("select ID,  Bezeichnung1, Bezeichnung2, Bezeichnung3, Strasse, Plz, Ort, Telefon1, Telefon2, Fax, NatelNr, Email from elexisprod.Kontakt");

			while (rs.next()) {
				/*
				 * System.out.println("ID: " + rs.getString(0));
				 * System.out.println("Name: " + rs.getString(1));
				 * System.out.println("Bezeichnung1: " + rs.getString(2));
				 * System.out.println("Bezeichnung2: " + rs.getString(3));
				 * System.out.println("Telefon: " + rs.getString(4));
				 */
				String sID = rs.getString("ID");
				String sBezeichnung1 = rs.getString("Bezeichnung1");
				String sBezeichnung2 = rs.getString("Bezeichnung2");
				String sBezeichnung3 = rs.getString("Bezeichnung3");
				String sStrasse = rs.getString("Strasse");
				String sPlz = rs.getString("Plz");
				String sOrt = rs.getString("Ort");
				String sTelefon1 = rs.getString("Telefon1");
				String sTelefon2 = rs.getString("Telefon2");
				String sFax = rs.getString("Fax");
				String sNatelNr = rs.getString("NatelNr");
				String sEmail = rs.getString("Email");

				System.out.println("Processing Kontakt with ID: " 
				+ sID + "\n"
				+ sBezeichnung1 + "\n" 
				+ sBezeichnung2 + "\n"
				+ sBezeichnung3 + "\n"
				+ sStrasse + "\n"
				+ sPlz + "\n"
				+ sOrt + "\n"
				+ sTelefon1 + "\n"
				+ sTelefon2 + "\n"
				+ sFax + "\n"
				+ sNatelNr + "\n"
				+ sEmail + "\n"
				);

				// ps =
				// con.prepareStatement("update  elexisprod.Kontakt set (Name,  Bezeichnung1, Bezeichnung2, Telefon) values (?, ?, ?, ?) where ID = ?");
				//ps = con.prepareStatement("update  elexisprod.Kontakt set  Bezeichnung1 = ?, Bezeichnung2 = ?, Bezeichnung3 = ?, Strasse = ?, Plz = ?, Ort = ?, Telefon1 = ?, Telefon2 = ?, Fax = ?, NatelNr = ?, Email = ?  where ID = ? AND ID NOT IN ('c8546ab5fc7a0114b071', 'd4edd73bde6654160112', 'Hd66df35621e7c5d60540', 'j746354d02a107807016', 'n68c66e52251a0e60224', 's47cabc9b1fd87bdc02011', 't127363d1fed54c7a0504', 'T5fa69fac04b22c0036')");
				ps = con.prepareStatement("update  elexisprod.Kontakt set  Bezeichnung2 = ? where ID = ? AND ID NOT IN ('c8546ab5fc7a0114b071', 'd4edd73bde6654160112', 'Hd66df35621e7c5d60540', 'j746354d02a107807016', 'n68c66e52251a0e60224', 's47cabc9b1fd87bdc02011', 't127363d1fed54c7a0504', 'T5fa69fac04b22c0036')");

				// "myuser, webpage, datum, summary, COMMENTS from WORLD.COMMENTS");
				// parameters start with 1

				ps.setString(1, randomString(10));
			
				ps.setString(2, sID);
/*
				ps.setString(1, randomString(10));
				ps.setString(2, randomString(10));
				ps.setString(3, randomString(10));
				ps.setString(4, randomString(10));
				ps.setString(5, randomString(4));
				ps.setString(6, randomString(10));
				ps.setString(7, "079 " + randomNumericString(7));
				ps.setString(8, "031 " + randomNumericString(7));
				ps.setString(9, randomString(10));
				ps.setString(10, randomString(10));
				ps.setString(11, randomString(10));
				ps.setString(12, sID);
*/
				/*
				 * ps.setString(1, "adfddsf3334343d"); ps.setString(2, "aaaa");
				 * ps.setString(3, "bbbbb"); ps.setString(4, "ccccc");
				 * ps.setString(5, sID);
				 */

				int result = ps.executeUpdate();
				System.out.println("updated: " + result);
				if (result == 0) {
					System.out.println("ATTENTION: nothing updated: ");
				}
				

			}

			/*
			 * // preparedStatements can use variables and are more efficient ps
			 * = con.prepareStatement(
			 * "insert into  WORLD.COMMENTS values (default, ?, ?, ?, ? , ?, ?)"
			 * ); //
			 * "myuser, webpage, datum, summary, COMMENTS from WORLD.COMMENTS");
			 * // parameters start with 1 ps.setString(1, "Test");
			 * ps.setString(2, "TestEmail"); ps.setString(3, "TestWebpage");
			 * //preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			 * java.util.Date date = new Date(); java.sql.Date now = new
			 * java.sql.Date(date.getTime()); ps.setDate(4, now);
			 * ps.setString(5, "TestSummary"); ps.setString(6, "TestComment");
			 * ps.executeUpdate();
			 * 
			 * ps = con .prepareStatement(
			 * "SELECT myuser, webpage, datum, summary, COMMENTS from WORLD.COMMENTS"
			 * ); rs = ps.executeQuery(); writeResultSet(rs);
			 */

			/*
			 * // remove again the insert comment preparedStatement = connect
			 * .prepareStatement
			 * ("delete from WORLD.COMMENTS where myuser= ? ; ");
			 * preparedStatement.setString(1, "Test");
			 * preparedStatement.executeUpdate();
			 * 
			 * resultSet = statement
			 * .executeQuery("select * from WORLD.COMMENTS");
			 * writeMetaData(resultSet);
			 */

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			throw e;
		} finally {
			close();
		}

	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// now get some metadata from the database
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " "
					+ resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
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
	private void close() {
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

	private void close(Closeable c) {
		try {
			if (c != null) {
				c.close();
			}
		} catch (Exception e) {
			// don't throw now as it might leave following closables in
			// undefined state
		}
	}

	public static void main(String[] args) throws Exception {
		// prompt the user to enter their name
		System.out.println("Enter the JDBC url(or \"default\"):");
		System.out.println("Example: jdbc:mysql://localhost/DCGBP01?user=root&password=root");

		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		System.out.println("Reading input from console using Scanner in Java ");
		System.out.println("Please enter your input: ");
		String input = scanner.nextLine();
		if (!input.startsWith("jdbc:mysql://")) {
			if (!input.equals("default")) {
				System.out.println("Invalid Url, it must start with jdbc:mysql://");
				System.exit(0);

			}
			else {
				input = "jdbc:mysql://localhost/elexisprod?user=root&password=root";
				System.out.println("Using default: " + input);
			}
		}

		MySQLAccess2 dao = new MySQLAccess2();
		dao.readDataBase(input);
	}

}
