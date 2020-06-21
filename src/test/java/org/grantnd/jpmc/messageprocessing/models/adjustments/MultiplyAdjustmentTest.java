package org.grantnd.jpmc.messageprocessing.models.adjustments;

import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class MultiplyAdjustmentTest {
    @Test(expected = IllegalArgumentException.class)
    public void construct_productNameIsNull_throwsException() {
        new MultiplyAdjustment(null, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_productNameIsBlank_throwsException() {
        new MultiplyAdjustment("", BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_factorIsZero_throwsException() {
        new MultiplyAdjustment("Apple", BigDecimal.ZERO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_factorIsLessThanZero_throwsException() {
        new MultiplyAdjustment("Apple", new BigDecimal("-1"));
    }

    @Test
    public void applyToSale_saleValueIsMultipliedByFactor() {
        Sale sale = new Sale("Apple", new BigDecimal("9"));
        Adjustment adjustment = new MultiplyAdjustment("Apple", new BigDecimal("4"));

        adjustment.applyToSale(sale);

        assertEquals(new BigDecimal("36"), sale.getValue());
    }
}