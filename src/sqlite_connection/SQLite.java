/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlite_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;


public class SQLite {

    public  Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:../demo.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
    
    public void insert(String name, String md5, String creat_time) {
        String sql = "INSERT INTO file(name, md5, creat_time) VALUES(?,?,?)";
  
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, md5);
            pstmt.setString(3, creat_time);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void delete(String name ) {
        String sql = "DELETE FROM file WHERE name = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String md5(String name){
        String md5 = null;
        String sql = "SELECT md5 FROM file WHERE name = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, name);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                md5 = rs.getString("md5");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return md5;
    }
    public String[] list(){
        String sql = "SELECT name FROM file";
        String[] list = new String[100]; 
        int i = 0;
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                list[i] = rs.getString("name");
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    
    

    public static void main(String[] args) {
        // TODO code application logic here
        SQLite app = new SQLite();
        LocalDateTime localDate = LocalDateTime.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:MM:SS");
        String time  = localDate.format(formatter);
//         app.insert("nhung", "fdghjk", time);
//         app.insert("gfcvh","asdvbgt" ,time );
//         app.delete("gfcvh");

        String[] list = new String[100];
        list = app.list();
        for (int i=0; i< list.length; i++)
            if(list[i]!= null){
                System.out.print(list[i]+' ');
            }else break;
       System.out.print(app.md5("hhj,"));
            
        
         
         
    }
    
}
