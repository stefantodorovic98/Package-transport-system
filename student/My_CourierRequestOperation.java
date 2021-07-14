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
import rs.etf.sab.operations.CourierRequestOperation;

/**
 *
 * @author Stefan
 */
public class ts170426_CourierRequestOperation implements CourierRequestOperation {
    
    Connection connection = DB.getInstance().getConnection();

    @Override
    public boolean insertCourierRequest(String korisnickoIme, String regBr) {
        String query = "insert into ZAHTEVATI(KorisnickoIme, RegBr) values (?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, korisnickoIme);
            ps.setString(2, regBr);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteCourierRequest(String korisnickoIme) {
        String query = "delete from ZAHTEVATI where KorisnickoIme = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, korisnickoIme);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeVehicleInCourierRequest(String korisnickoIme, String regBr) {
        String query = "update ZAHTEVATI set RegBr = ? where KorisnickoIme = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, regBr);
            ps.setString(2, korisnickoIme);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<String> getAllCourierRequests() {
        List<String> lista = new LinkedList<>();
        String query = "select * from ZAHTEVATI";
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            while(rs.next()){
                lista.add(rs.getString("KorisnickoIme"));
            }  
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public boolean grantRequest(String korisnickoIme) {
        String sql = "{call SPOdobriZahtevZaKurira (?, ?)}";
        try(CallableStatement cs = connection.prepareCall(sql)){
            cs.setString(1, korisnickoIme);
            cs.registerOutParameter(2, java.sql.Types.INTEGER);
            cs.execute();
            if(cs.getInt(2) == 1) return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
