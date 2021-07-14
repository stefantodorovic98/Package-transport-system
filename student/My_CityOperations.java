/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.CityOperations;

/**
 *
 * @author Stefan
 */
public class ts170426_CityOperations implements CityOperations {

    Connection connection = DB.getInstance().getConnection();
    
    @Override
    public int insertCity(String naziv, String broj) {
        String query = "insert into GRAD(Naziv, Broj)"
                + " values (?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, naziv);
            ps.setString(2, broj);
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    return rs.getInt(1);
                }
            } catch (SQLException ex) {
                 //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int deleteCity(String... nazivi) {
        int brojac = 0;
        String query = "delete from GRAD where Naziv = ?";
        for( int i = 0; i < nazivi.length; i++){
            try(PreparedStatement ps = connection.prepareStatement(query)){
               ps.setString(1, nazivi[i]);
               int a = ps.executeUpdate();
               brojac += a;
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return brojac;
    }

    @Override
    public boolean deleteCity(int idGrad) {
        String query = "delete from GRAD where IdGrad = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, idGrad);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Integer> getAllCities() {
        List<Integer> lista = new LinkedList<>();
        String query = "select * from GRAD";
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            while(rs.next()){
                lista.add(rs.getInt("IdGrad"));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
}
