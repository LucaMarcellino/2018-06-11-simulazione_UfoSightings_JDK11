package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.AnnoAvvistamento;
import it.polito.tdp.ufo.model.Arco;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			List<Sighting> list = new ArrayList<>() ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<AnnoAvvistamento> getAnnoAvvistamento(){
		String sql = "SELECT DISTINCT (YEAR(DATETIME)) AS years, COUNT(*) AS tot FROM sighting WHERE country='US' GROUP BY YEAR(datetime)" ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			List<AnnoAvvistamento> list = new ArrayList<>() ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				AnnoAvvistamento av = new AnnoAvvistamento(res.getInt("years"), res.getInt("tot"));
				list.add(av);
 			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getVertex(AnnoAvvistamento aa){
		String sql = "SELECT DISTINCT(state) AS v FROM sighting WHERE country='US' AND YEAR(DATETIME)=?" ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, aa.getAnno());
			List<String> list = new ArrayList<>() ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(res.getString("v"));
 			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Arco> getEdge(AnnoAvvistamento aa){
		String sql = "SELECT s1.v,s2.v1 FROM (SELECT DISTINCT(state) AS v1,id,DATETIME FROM sighting WHERE country='US' AND YEAR(DATETIME)=?) AS s2, (SELECT DISTINCT(state) AS v,id,DATETIME FROM sighting WHERE country='US' AND YEAR(DATETIME)=?) AS s1 WHERE s1.id<>s2.id AND s1.v<>s2.v1 AND s1.datetime<s2.datetime GROUP BY s1.v,s2.v1" ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, aa.getAnno());
			st.setInt(2, aa.getAnno());
			List<Arco> list = new ArrayList<>() ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Arco a= new Arco(res.getString("v"), res.getString("v1"));
				list.add(a);
 			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	
	
	

}
