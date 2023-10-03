package org.java.nations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		final String url = "jdbc:mysql://localhost:3306/db-nations";
		final String user = "root";
		final String password = "";
				
//		final String sql = " SELECT c.name country_name, c.country_id  country_id, r.name region_name, c2.name continent_name "
//				+ " FROM countries c "
//				+ " JOIN regions r "
//				+ " ON c.region_id = r.region_id "
//				+ " JOIN continents c2 "
//				+ " ON r.continent_id = c2.continent_id ";
			
		Scanner scan = new Scanner(System.in);
		
		final String sql1 = " SELECT c.name country_name, c.country_id  country_id, r.name region_name, c2.name continent_name "
				+ " FROM countries c "
				+ " JOIN regions r "
				+ " ON c.region_id = r.region_id "
				+ " JOIN continents c2 "
				+ " ON r.continent_id = c2.continent_id "
				+ " WHERE c.name LIKE ? ";
		
		final String sql2 = " SELECT c.name country_name, l.`language` `language`, cs.`year` `year`, cs.population `population`, cs.gdp `gdp` "
				+ " FROM countries c "
				+ " JOIN country_languages cl "
				+ " ON c.country_id = cl.country_id "
				+ " JOIN languages l "
				+ " ON cl.language_id = l.language_id "
				+ " JOIN country_stats cs "
				+ " ON c.country_id = cs.country_id "
				+ " WHERE cs.`year` = 2018 "
				+ " AND c.country_id = ? ";
		
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			boolean r = true;		
			while(r) {
				System.out.print("Search: ");
				String country = "%" + scan.nextLine() + "%";		
				
				PreparedStatement ps = con.prepareStatement(sql1);	
				ps.setString(1, country);
				ResultSet rs = ps.executeQuery();
				
	//			System.out.println(" ------------------------------------------------------------------------------------------ ");
	//			System.out.print("| " + " country_id " 
	//					+ " | " + "     country_name     " 
	//					+ " | " + "     region_name     "
	//					+ " | " + "     continent_name     " + " |\n");
	//			System.out.print(" ------------------------------------------------------------------------------------------ \n");
				if (rs.next()) {
					r = false;	
					
					System.out.println("\n --------------------------------------------------------------------------------------- ");
					System.out.print("| ");
					System.out.printf("%10s", "country_id");
					System.out.print(" | ");
					System.out.printf("%22s", "country_name");
					System.out.print(" | ");
					System.out.printf("%22s", "region_name");
					System.out.print(" | ");
					System.out.printf("%22s", "continent_name");
					System.out.print(" |\n");
					System.out.print(" --------------------------------------------------------------------------------------- \n");
					do {					
						int    country_id     = rs.getInt("country_id");
						String country_name   = rs.getString("country_name");
						String region_name    = rs.getString("region_name");
						String continent_name = rs.getString("continent_name");
						
						
						System.out.print("| ");
						System.out.printf("%10s", country_id);
						System.out.print(" | ");
						
						if (country_name.length() > 19) {
							String c_n = country_name.substring(0, 19) + "...";					
							System.out.printf("%22s", c_n);
						} else
							System.out.printf("%22s", country_name);
						
						System.out.print(" | ");
						
						if (region_name.length() > 19) {
							String r_n = region_name.substring(0, 19) + "...";
							System.out.printf("%22s", r_n);
						} else
							System.out.printf("%22s", region_name);
						
						System.out.print(" | ");
						System.out.printf("%22s", continent_name);
						System.out.print(" |\n");
						
		//				System.out.println("| " + country_id 
		//						+ " | " + country_name 
		//						+ " | " + region_name
		//						+ " | " + continent_name + " |");
					} while (rs.next()); 
					
					System.out.print(" --------------------------------------------------------------------------------------- \n");
					
					boolean r2 = true;
					while(r2) {
						System.out.print("\nId: ");
						int id = Integer.valueOf(scan.nextLine());
						
						PreparedStatement ps2 = con.prepareStatement(sql2);	
						ps2.setInt(1, id);
						ResultSet rs2 = ps2.executeQuery();
						
						List<String> languages = new ArrayList<>();
						
						if(rs2.next()) {
							r2 = false;
							
							String name       = rs2.getString("country_name");
							String year       = rs2.getString("year");
							String population = rs2.getString("population");
							String gdp        = rs2.getString("gdp");
							
							do {
								String language = rs2.getString("language");
								languages.add(language);
							} while (rs2.next());
							
							String lng = "";
							
							for (int i = 0; i < languages.size(); i++) {
								String language = languages.get(i); 
								lng += i < languages.size() - 1 ? language + ", " : language;
							}
							
							System.out.println("\nCountry name:  " + name);
							System.out.println("Languages:     " + lng + "\n");
							System.out.println("Statistic of " + year);
							System.out.println("Population:    " + population);
							System.out.println("Gdp:           " + gdp);	
						} else
							System.out.println("No results.\n");
					}	
				} else 
					System.out.println("No results.\n");	
			}
		} catch (Exception e) {	
			System.out.println("Errore di connessione: " + e.getMessage());
		} finally {			
			scan.close();
		}
	}
}
