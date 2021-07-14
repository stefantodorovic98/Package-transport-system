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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.PackageOperations;

/**
 *
 * @author Stefan
 */
public class ts170426_PackageOperations implements PackageOperations {
    
    Connection connection = DB.getInstance().getConnection();
    
    private int x = Integer.MAX_VALUE;
    private int y = Integer.MAX_VALUE;

    @Override
    public int insertPackage(int idOpstinaPreuzima, int idOpstinaDostavlja, String korisnik, int tip, BigDecimal tezina) {
        String query = "insert into PAKET(OpstinaPreuzima, OpstinaDostavlja, Korisnik, Tip, Tezina)"
                + " values (?, ?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setInt(1, idOpstinaPreuzima);
            ps.setInt(2, idOpstinaDostavlja);
            ps.setString(3, korisnik);
            ps.setInt(4, tip);
            ps.setBigDecimal(5, tezina);
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys();){
                if(rs.next()){
                    return rs.getInt(1);
                }
            } catch (SQLException ex) {
                 //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) { 
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int insertTransportOffer(String korisnickoIme, int idPaket, BigDecimal procenat) {
        if(procenat == null) {
            procenat = new BigDecimal(new Random().nextDouble() * 10);
            System.out.println(procenat.toString());
        }
        String query1 = "select * from KURIR where KorisnickoIme = ? and Status = ?";
        try(PreparedStatement ps = connection.prepareStatement(query1)){
            ps.setString(1, korisnickoIme);
            ps.setInt(2, 0);
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()){
                    return -1;
                }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query2 = "select * from PAKET where IdPaket = ? and Status = ?";
        try(PreparedStatement ps2 = connection.prepareStatement(query2)){
            ps2.setInt(1, idPaket);
            ps2.setInt(2, 0);
            try(ResultSet rs2 = ps2.executeQuery()){
                if(!rs2.next()){
                    return -1;
                }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Provera da jedan kurir ne moze dati vise ponuda za jedan paket
        
        String query3 = "insert into PONUDA(KorisnickoIme, IdPaket, Procenat) values (?, ?, ?)";
        try(PreparedStatement ps3 = connection.prepareStatement(query3, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps3.setString(1, korisnickoIme);
            ps3.setInt(2, idPaket);
            ps3.setBigDecimal(3, procenat);
            ps3.executeUpdate();
            try(ResultSet rs3 = ps3.getGeneratedKeys()){
                if(rs3.next()){
                    return rs3.getInt(1);
                } 
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public boolean acceptAnOffer(int idPonuda) {
        String kurir = "";
        int idPaket = -1;
        BigDecimal procenat = new BigDecimal(0);
        
        int tip = -1;
        BigDecimal tezina = new BigDecimal(0);
        
        int opstinaPreuzima = -1;
        int opstinaDostavlja = -1;
        
        int xPreuzima = -1;
        int yPreuzima = -1;
        int xDostavlja = -1;
        int yDostavlja = -1;
        
        String query1 = "select * from PONUDA where IdPonuda = ?";
        try(PreparedStatement ps1 = connection.prepareStatement(query1)){
            ps1.setInt(1, idPonuda);
            try(ResultSet rs1 = ps1.executeQuery()){
                if(rs1.next()){
                    kurir = rs1.getString("KorisnickoIme");
                    idPaket = rs1.getInt("IdPaket");
                    procenat = rs1.getBigDecimal("Procenat");
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query2 = "select * from PAKET where IdPaket = ? and Status = ?";
        try(PreparedStatement ps2 = connection.prepareStatement(query2)){
            ps2.setInt(1, idPaket);
            ps2.setInt(2, 0);
            try(ResultSet rs2 = ps2.executeQuery()){
                if(rs2.next()){
                    tip = rs2.getInt("Tip");
                    tezina = rs2.getBigDecimal("Tezina");
                    opstinaPreuzima = rs2.getInt("OpstinaPreuzima");
                    opstinaDostavlja = rs2.getInt("OpstinaDostavlja");
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query3 = "select * from OPSTINA where IdOpstina = ?";
        try(PreparedStatement ps3 = connection.prepareStatement(query3)){
            ps3.setInt(1, opstinaPreuzima);
            try(ResultSet rs3 = ps3.executeQuery()){
                if(rs3.next()){
                    xPreuzima = rs3.getInt("x");
                    yPreuzima = rs3.getInt("y");
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query4 = "select * from OPSTINA where IdOpstina = ?";
        try(PreparedStatement ps4 = connection.prepareStatement(query4)){
            ps4.setInt(1, opstinaDostavlja);
            try(ResultSet rs4 = ps4.executeQuery()){
                if(rs4.next()){
                    xDostavlja = rs4.getInt("x");
                    yDostavlja = rs4.getInt("y");
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BigDecimal Cena;
        BigDecimal OsnovnaCena = new BigDecimal(0);
        BigDecimal TezinskiFaktor = new BigDecimal(0);
        BigDecimal Tezina = tezina;
        BigDecimal CenaPoKg = new BigDecimal(0);
        BigDecimal Procenat = procenat;
        BigDecimal EuklidskaDistanca = new BigDecimal(Math.sqrt(Math.pow(xPreuzima - xDostavlja, 2) + Math.pow(yPreuzima - yDostavlja, 2)));
        switch (tip) {
            case 0:
                OsnovnaCena = new BigDecimal(10);
                TezinskiFaktor = new BigDecimal(0);
                CenaPoKg = new BigDecimal(0);
                break;
            case 1:
                OsnovnaCena = new BigDecimal(25);
                TezinskiFaktor = new BigDecimal(1);
                CenaPoKg = new BigDecimal(100);
                break;
            case 2:
                OsnovnaCena = new BigDecimal(75);
                TezinskiFaktor = new BigDecimal(2);
                CenaPoKg = new BigDecimal(300);
                break;
            default:
                break;
        }
        
        Cena = TezinskiFaktor.multiply(Tezina);
        Cena = Cena.multiply(CenaPoKg);
        Cena = Cena.add(OsnovnaCena);
        Cena = Cena.multiply(EuklidskaDistanca);
        
        Procenat = Procenat.add(new BigDecimal(100));
        Cena = Cena.multiply(Procenat);
        Cena = Cena.divide(new BigDecimal(100));
        
        java.sql.Timestamp vreme = new java.sql.Timestamp(new java.util.Date().getTime());
        
        String query5 = "update PAKET set Kurir = ?, Cena = ?, VremePrihvatanja = ?, Status = ? where IdPaket = ?";
        try(PreparedStatement ps5 = connection.prepareStatement(query5)){
            ps5.setString(1, kurir);
            ps5.setBigDecimal(2, Cena);
            ps5.setTimestamp(3, vreme);
            ps5.setInt(4, 1);
            ps5.setInt(5, idPaket);
            int a = ps5.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public List<Integer> getAllOffers() {
        List<Integer> lista = new LinkedList<>();
        String query = "select * from PONUDA";
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            while(rs.next()){
                lista.add(rs.getInt("IdPonuda"));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public List<Pair<Integer, BigDecimal>> getAllOffersForPackage(int idPaket) {
        List<Pair<Integer, BigDecimal>> lista = new LinkedList<>();
        String query = "select * from PONUDA where IdPaket = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, idPaket);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    lista.add(new ts170426_PackageOperationsPair(rs.getInt("IdPonuda"), rs.getBigDecimal("Procenat")));
                }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public boolean deletePackage(int idPaket) {
        String query = "delete from PAKET where IdPaket = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, idPaket);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeWeight(int idPaket, BigDecimal tezina) {
        String query = "update PAKET set Tezina = ? where IdPaket = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setBigDecimal(1, tezina);
            ps.setInt(2, idPaket);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeType(int idPaket, int tip) {
        String query = "update PAKET set Tip = ? where IdPaket = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, tip);
            ps.setInt(2, idPaket);
            int a = ps.executeUpdate();
            if(a > 0) return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Integer getDeliveryStatus(int idPaket) {
        Integer retVal = null;
        String query = "select * from PAKET where IdPaket = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, idPaket);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    retVal = rs.getInt("Status");
                }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }

    @Override
    public BigDecimal getPriceOfDelivery(int idPaket) {
        BigDecimal retVal = null;
        String query = "select * from PAKET where IdPaket = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, idPaket);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    retVal = rs.getBigDecimal("Cena");
                }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }

    @Override
    public Date getAcceptanceTime(int idPaket) {
        Date datum = null;
        String query = "select * from PAKET where IdPaket = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, idPaket);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    if(rs.getTimestamp("VremePrihvatanja") != null){
                        datum = Date.valueOf(rs.getTimestamp("VremePrihvatanja").toLocalDateTime().toLocalDate());
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datum;
    }

    @Override
    public List<Integer> getAllPackagesWithSpecificType(int tip) {
        List<Integer> lista = new LinkedList<>();
        String query = "select * from PAKET where Tip = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, tip);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    lista.add(rs.getInt("IdPaket"));
                }
            } catch (SQLException ex) {
                //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public List<Integer> getAllPackages() {
        List<Integer> lista = new LinkedList<>();
        String query = "select * from PAKET";
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            while(rs.next()){
                lista.add(rs.getInt("IdPaket"));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public List<Integer> getDrive(String korisnickoIme) {
        List<Integer> lista = new LinkedList<>();
        String query = "select * from PAKET where Kurir = ? and Status = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, korisnickoIme);
            ps.setInt(2, 2);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    lista.add(rs.getInt("IdPaket"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(lista.isEmpty()) return null;
        else return lista;
    }

    @Override
    public int driveNextPackage(String korisnickoIme) {
        if(setStatus(korisnickoIme, 1) == -2) return -2;
        
        int idPaket = -1;
        
        BigDecimal cena = new BigDecimal(0);
        String regBr = "";
        BigDecimal potrosnja = new BigDecimal(0);
        int tip = -1;
        
        int opstinaPreuzima = -1;
        int opstinaDostavlja = -1;
        
        int xPreuzima = -1;
        int yPreuzima = -1;
        int xDostavlja = -1;
        int yDostavlja = -1;
        
        String query1 = "update PAKET set Status = ? where Kurir = ? and Status = ?";
        try(PreparedStatement ps = connection.prepareStatement(query1)){
            ps.setInt(1, 2);
            ps.setString(2, korisnickoIme);
            ps.setInt(3, 1);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query2 = "select * from PAKET where Kurir = ? and Status = ?";
        try(PreparedStatement ps = connection.prepareStatement(query2)){
            ps.setString(1, korisnickoIme);
            ps.setInt(2, 2);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    idPaket = rs.getInt("IdPaket");
                    cena = rs.getBigDecimal("Cena");
                    opstinaPreuzima = rs.getInt("OpstinaPreuzima");
                    opstinaDostavlja = rs.getInt("OpstinaDostavlja");
                } else {
                    setStatus(korisnickoIme, 0);
                    x = Integer.MAX_VALUE;
                    y = Integer.MAX_VALUE;
                    return -1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query3 = "select * from KURIR where KorisnickoIme = ?";
        try(PreparedStatement ps = connection.prepareStatement(query3)){
            ps.setString(1, korisnickoIme);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    regBr = rs.getString("RegBr");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query4 = "select * from VOZILO where RegBr = ?";
        try(PreparedStatement ps = connection.prepareStatement(query4)){
            ps.setString(1, regBr);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    potrosnja = rs.getBigDecimal("Potrosnja");
                    tip = rs.getInt("tip");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query5 = "select * from OPSTINA where IdOpstina = ?";
        try(PreparedStatement ps = connection.prepareStatement(query5)){
            ps.setInt(1, opstinaPreuzima);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    xPreuzima = rs.getInt("x");
                    yPreuzima = rs.getInt("y");
                } 
            } catch (SQLException ex) {
                Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query6 = "select * from OPSTINA where IdOpstina = ?";
        try(PreparedStatement ps = connection.prepareStatement(query6)){
            ps.setInt(1, opstinaDostavlja);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    xDostavlja = rs.getInt("x");
                    yDostavlja = rs.getInt("y");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BigDecimal Profit = new BigDecimal(0);
        BigDecimal Cena = cena;
        BigDecimal Potrosnja = potrosnja;
        BigDecimal CenaGoriva = new BigDecimal(0);
        BigDecimal putDoPreuzima = new BigDecimal(0);
        if(x != Integer.MAX_VALUE && y != Integer.MAX_VALUE){
            putDoPreuzima = new BigDecimal(Math.sqrt(Math.pow(xPreuzima - x, 2) + Math.pow(yPreuzima - y, 2)));
        }
        BigDecimal EuklidskaDistanca = new BigDecimal(Math.sqrt(Math.pow(xPreuzima - xDostavlja, 2) + Math.pow(yPreuzima - yDostavlja, 2)));
        EuklidskaDistanca = EuklidskaDistanca.add(putDoPreuzima);
        
        switch (tip) {
            case 0:
                CenaGoriva = new BigDecimal(15);
                break;
            case 1:
                CenaGoriva = new BigDecimal(32);
                break;
            case 2:
                CenaGoriva = new BigDecimal(36);
                break;
            default:
                break;
        }
        
        BigDecimal Trosak = Potrosnja.multiply(EuklidskaDistanca);
        Trosak = Trosak.multiply(CenaGoriva);
        Profit = Cena.subtract(Trosak);
        
        String query7 = "select * from PAKET where Kurir = ? and Status = ?";
        try(PreparedStatement ps = connection.prepareStatement(query7)){
            ps.setString(1, korisnickoIme);
            ps.setInt(2, 2);
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()){
                    setStatus(korisnickoIme, 0);
                    x = Integer.MAX_VALUE;
                    y = Integer.MAX_VALUE;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query8 = "update KURIR set Profit = Profit + ?, BrIsporucenihPaketa = BrIsporucenihPaketa + ?"
                + " where KorisnickoIme = ?";
        try(PreparedStatement ps = connection.prepareStatement(query8)){
            ps.setBigDecimal(1, Profit);
            ps.setInt(2, 1);
            ps.setString(3, korisnickoIme);
            int a = ps.executeUpdate();
            if(a == 0) return -2;
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query9 = "update PAKET set Status = ? where IdPaket = ?";
        try(PreparedStatement ps = connection.prepareStatement(query9)){
            ps.setInt(1, 3);
            ps.setInt(2, idPaket);
            int a = ps.executeUpdate();
            if(a > 0){
                x = xDostavlja;
                y = yDostavlja;
                return idPaket;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public int setStatus(String korisnickoIme, int status){
        String query = "update KURIR set Status = ? where KorisnickoIme = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, status);
            ps.setString(2, korisnickoIme);
            int a = ps.executeUpdate();
            if(a == 0) return -2;
        } catch (SQLException ex) {
            Logger.getLogger(ts170426_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
}
