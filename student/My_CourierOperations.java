/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.math.BigDecimal;
import java.util.List;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.CourierOperations;

/**
 *
 * @author Stefan
 */
public class ts170426_CourierOperations implements CourierOperations{
    
    Connection connection = DB.getInstance().getConnection();

    @Override
    public boolean insertCourier(String KorisnickoIme, String regBr) {
        String query = "insert into KURIR(KorisnickoIme, RegBr) values (?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, KorisnickoIme);
            ps.setString(2, regBr);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteCourier(String korisnickoIme) {
        String query = "delete from KURIR where KorisnickoIme = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, korisnickoIme);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<String> getCouriersWithStatus(int status) {
        List<String> lista = new LinkedList<>();
        String query = "select * from KURIR where Status = ?";
        try(PreparedStatement ps = connection.prepareStatement(query);){
            ps.setInt(1, status);
            try(ResultSet rs = ps.executeQuery();){
                while(rs.next()){
                    lista.add(rs.getString("KorisnickoIme"));
                }
            } catch(SQLException ex) {
                //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public List<String> getAllCouriers() {
        List<String> lista = new LinkedList<>();
        String query = "select * from KURIR order by Profit desc";
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            while(rs.next()){
                lista.add(rs.getString("KorisnickoIme"));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public BigDecimal getAverageCourierProfit(int brIsporucenihPaketa) {
        BigDecimal retVal = null;
        String query = "select avg(Profit) as ProsecanProfit from KURIR where BrIsporucenihPaketa >= ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, brIsporucenihPaketa);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    retVal = rs.getBigDecimal("ProsecanProfit");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(retVal == null) return new BigDecimal(0);
        else return retVal;
        
        //return retVal;
    }
    
}
