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
import rs.etf.sab.operations.DistrictOperations;

/**
 *
 * @author Stefan
 */
public class ts170426_DistrictOperations implements DistrictOperations {
    
    Connection connection = DB.getInstance().getConnection();

    @Override
    public int insertDistrict(String naziv, int idGrad, int x, int y) {
        String query = "insert into OPSTINA(Naziv, x, y, IdGrad)"
                + " values (?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, naziv);
            ps.setInt(2, x);
            ps.setInt(3, y);
            ps.setInt(4, idGrad);
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys();){
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
    public int deleteDistricts(String... nazivi) {
        int brojac = 0;
        String query = "delete from OPSTINA where Naziv = ?";
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
    public boolean deleteDistrict(int idOpstina) {
        String query = "delete from OPSTINA where IdOpstina = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, idOpstina);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public int deleteAllDistrictsFromCity(String naziv) {
        int brojac = 0;
        String query = "delete OPSTINA from OPSTINA inner join GRAD "
                + " on OPSTINA.IdGrad = GRAD.IdGrad where GRAD.Naziv = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, naziv);
            int a = ps.executeUpdate();
            brojac += a;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return brojac;
    }

    @Override
    public List<Integer> getAllDistrictsFromCity(int idGrad) {
        List<Integer> lista = new LinkedList<>();
        String query = "select * from OPSTINA where IdGrad = ?";
        try(PreparedStatement ps = connection.prepareStatement(query);){
            ps.setInt(1, idGrad);
            try(ResultSet rs = ps.executeQuery();){
                while(rs.next()){
                    lista.add(rs.getInt("IdOpstina"));
                }
            } catch(SQLException ex) {
                //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(lista.isEmpty()) return null;
        else return lista;
    }

    @Override
    public List<Integer> getAllDistricts() {
        List<Integer> lista = new LinkedList<>();
        String query = "select * from OPSTINA";
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            while(rs.next()){
                lista.add(rs.getInt("IdOpstina"));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
}
