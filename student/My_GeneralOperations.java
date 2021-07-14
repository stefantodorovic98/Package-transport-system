/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import rs.etf.sab.operations.GeneralOperations;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan
 */
public class ts170426_GeneralOperations implements GeneralOperations {
    
    Connection connection = DB.getInstance().getConnection();

    @Override
    public void eraseAll() {
        String query1 = "delete from PONUDA";
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate(query1);
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query2 = "delete from ADMINISTRATOR";
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate(query2);
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query3 = "delete from ZAHTEVATI";
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate(query3);
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query4 = "delete from PAKET";
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate(query4);
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query5 = "delete from OPSTINA";
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate(query5);
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query6 = "delete from KURIR";
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate(query6);
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query7 = "delete from VOZILO";
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate(query7);
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query8 = "delete from KORISNIK";
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate(query8);
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query9 = "delete from GRAD";
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate(query9);
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
}
