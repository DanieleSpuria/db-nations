package org.java.nations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		final String url = "jdbc:mysql://localhost:3306/db-nations";
		final String user = "root";
		final String password = "daniele89";
		
		
//		MILESTONE 2
//		
//		final String sql = " SELECT c.name country_name, c.country_id  country_id, r.name region_name, c2.name continent_name "
//				+ " FROM countries c "
//				+ " JOIN regions r "
//				+ " ON c.region_id = r.region_id "
//				+ " JOIN continents c2 "
//				+ " ON r.continent_id = c2.continent_id ";
		
		
		
//		MILESTONE 3 
		
		Scanner scan = new Scanner(System.in);
		
		final String sql = " SELECT c.name country_name, c.country_id  country_id, r.name region_name, c2.name continent_name "
				+ " FROM countries c "
				+ " JOIN regions r "
				+ " ON c.region_id = r.region_id "
				+ " JOIN continents c2 "
				+ " ON r.continent_id = c2.continent_id "
				+ " WHERE c.name LIKE ? ";
		
		System.out.print("Cerca: ");
		String country = "%" + scan.nextLine() + "%";		
		
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			
			PreparedStatement ps = con.prepareStatement(sql);	
			ps.setString(1, country);
			ResultSet rs = ps.executeQuery();
			System.out.println("");
//			System.out.println(" ------------------------------------------------------------------------------------------ ");
//			System.out.print("| " + " country_id " 
//					+ " | " + "     country_name     " 
//					+ " | " + "     region_name     "
//					+ " | " + "     continent_name     " + " |\n");
//			System.out.print(" ------------------------------------------------------------------------------------------ \n");
			
			System.out.println(" ------------------------------------------------------------------------------------------ ");
			System.out.print("| ");
			System.out.printf("%12s", "country_id");
			System.out.print(" | ");
			System.out.printf("%22s", "country_name");
			System.out.print(" | ");
			System.out.printf("%21s", "region_name");
			System.out.print(" | ");
			System.out.printf("%24s", "continent_name");
			System.out.print(" |\n");
			System.out.print(" ------------------------------------------------------------------------------------------ \n");
			
			while(rs.next()) {
				int country_id = rs.getInt("country_id");
				String country_name = rs.getString("country_name");
				String region_name = rs.getString("region_name");
				String continent_name = rs.getString("continent_name");
				
				System.out.print("| ");
				System.out.printf("%12s", country_id);
				System.out.print(" | ");
				System.out.printf("%22s", country_name);
				System.out.print(" | ");
				System.out.printf("%21s", region_name);
				System.out.print(" | ");
				System.out.printf("%24s", continent_name);
				System.out.print(" |\n");
				
//				System.out.println("| " + country_id 
//						+ " | " + country_name 
//						+ " | " + region_name
//						+ " | " + continent_name + " |");
			} 
			
		} catch (Exception e) {
			
			System.out.println("Errore di connessione: " + e.getMessage());
		}		
	}
}
