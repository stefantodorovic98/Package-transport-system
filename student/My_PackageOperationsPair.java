/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.math.BigDecimal;
import rs.etf.sab.operations.PackageOperations;
import rs.etf.sab.operations.PackageOperations.Pair;

/**
 *
 * @author Stefan
 */
public class ts170426_PackageOperationsPair implements Pair<Integer, BigDecimal> {
    
    Integer idPonuda;
    BigDecimal procenat;
    
    public ts170426_PackageOperationsPair(Integer idPonuda, BigDecimal procenat){
        this.idPonuda = idPonuda;
        this.procenat = procenat;
    }

    @Override
    public Integer getFirstParam() {
        return idPonuda;
    }

    @Override
    public BigDecimal getSecondParam() {
        return procenat;
    }
    
}
