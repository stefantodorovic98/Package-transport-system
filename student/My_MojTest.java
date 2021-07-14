/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.PackageOperations.Pair;

/**
 *
 * @author Stefan
 */
public class ts170426_MojTest {

    /**
     * @param args the command line arguments
     */
    
    public static void ispisiKorisnike(){
        Connection connection = DB.getInstance().getConnection();
        try(PreparedStatement ps = connection.prepareStatement("select * from Korisnik");
            ResultSet rs = ps.executeQuery();)
        {    
            while(rs.next())
                System.out.println(rs.getString("Ime") + " " + rs.getString("Prezime"));
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_MojTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
        ts170426_CityOperations obj = new ts170426_CityOperations();
        
        obj.deleteCity(694);
    }
    
}
