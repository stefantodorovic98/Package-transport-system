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
import rs.etf.sab.operations.UserOperations;

/**
 *
 * @author Stefan
 */
public class ts170426_UserOperations implements UserOperations {
    
    Connection connection = DB.getInstance().getConnection();

    @Override
    public boolean insertUser(String korisnickoIme, String ime, String prezime, String sifra) {
        String sablon = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        int a = -1;
        if(Character.isUpperCase(ime.charAt(0)) &&
                Character.isUpperCase(prezime.charAt(0)) &&
                sifra.matches(sablon)){
            String query = "insert into KORISNIK(Ime, Prezime, KorisnickoIme, Sifra)"
                + " values (?, ?, ?, ?)";
            try(PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);){
                ps.setString(1, ime);
                ps.setString(2, prezime);
                ps.setString(3, korisnickoIme);
                ps.setString(4, sifra);
                ps.executeUpdate();
                try(ResultSet rs = ps.getGeneratedKeys();){
                    if(rs.next()){
                        a = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                 //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(a == -1) return false;
        else return true;
    }

    @Override
    public int declareAdmin(String korisnickoIme) {
        String query = "insert into ADMINISTRATOR(KorisnickoIme) "
                + "select KorisnickoIme from KORISNIK where KorisnickoIme = ?";
        int a = 1;
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, korisnickoIme);
            int b = ps.executeUpdate();
            if(b > 0) a = 0;
            else a = 2;
        } catch (SQLException ex) {
            System.out.println("Ovde smo");
            //Logger.getLogger(ts170426_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    @Override
    public Integer getSentPackages(String... korisnickaImena) {
        int brojac = 0;
        boolean ima = false;
        String query = "select * from KORISNIK where KorisnickoIme = ?";
        for( int i = 0; i < korisnickaImena.length; i++){
            try(PreparedStatement ps = connection.prepareStatement(query)){
               ps.setString(1, korisnickaImena[i]);
               try(ResultSet rs = ps.executeQuery()){
                   if(rs.next()) {
                       ima = true;
                       brojac += rs.getInt("BrPoslatihPaketa");
                   }
               } catch (SQLException ex) {
                   //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
               }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(ima) return brojac;
        else return null;
    }

    @Override
    public int deleteUsers(String... korisnickaImena) {
        int brojac = 0;
        String query = "delete from KORISNIK where KorisnickoIme = ?";
        for( int i = 0; i < korisnickaImena.length; i++){
            try(PreparedStatement ps = connection.prepareStatement(query)){
               ps.setString(1, korisnickaImena[i]);
               int a = ps.executeUpdate();
               brojac += a;
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return brojac;
    }

    @Override
    public List<String> getAllUsers() {
        List<String> lista = new LinkedList<>();
        String query = "select KorisnickoIme from KORISNIK";
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
    
}
