/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.VehicleOperations;

/**
 *
 * @author Stefan
 */
public class ts170426_VehicleOperations implements VehicleOperations {
    
    Connection connection = DB.getInstance().getConnection();

    @Override
    public boolean insertVehicle(String regBr, int tip, BigDecimal potrosnja) {
        String query = "insert into VOZILO(RegBr, Tip, Potrosnja)"
                + " values (?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, regBr);
            ps.setInt(2, tip);
            ps.setBigDecimal(3, potrosnja);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public int deleteVehicles(String... regBrojevi) {
        int brojac = 0;
        String query = "delete from VOZILO where RegBr = ?";
        for( int i = 0; i < regBrojevi.length; i++){
            try(PreparedStatement ps = connection.prepareStatement(query)){
               ps.setString(1, regBrojevi[i]);
               int a = ps.executeUpdate();
               brojac += a;
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return brojac;
    }

    @Override
    public List<String> getAllVehichles() {
        List<String> lista = new LinkedList<>();
        String query = "select * from VOZILO";
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            while(rs.next()){
                lista.add(rs.getString("RegBr"));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public boolean changeFuelType(String regBr, int tip) {
        String query = "update VOZILO set Tip = ? where RegBr = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, tip);
            ps.setString(2, regBr);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeConsumption(String regBr, BigDecimal potrosnja) {
        String query = "update VOZILO set Potrosnja = ? where RegBr = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setBigDecimal(1, potrosnja);
            ps.setString(2, regBr);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
