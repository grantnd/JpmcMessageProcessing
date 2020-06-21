package org.grantnd.jpmc.messageprocessing.models.adjustments;

import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class SubtractAdjustmentTest {
    @Test(expected = IllegalArgumentException.class)
    public void construct_productTypeIsNull_throwsException() {
        new SubtractAdjustment(null, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_productTypeIsBlank_throwsException() {
        new SubtractAdjustment("", BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_deltaIsZero_throwsException() {
        new SubtractAdjustment("Apple", BigDecimal.ZERO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_deltaIsLessThanZero_throwsException() {
        new SubtractAdjustment("Apple", new BigDecimal("-1"));
    }

    @Test
    public void applyToSale_deltaIsSubtractedFromSale() {
        Sale sale = new Sale("Apple", new BigDecimal("9"));
        Adjustment adjustment = new SubtractAdjustment("Apple", new BigDecimal("3"));

        adjustment.applyToSale(sale);

        assertEquals(new BigDecimal("6"), sale.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void applyToSale_resultIsNegative_throwsException() {
        Sale sale = new Sale("Apple", new BigDecimal("9"));
        Adjustment adjustment = new SubtractAdjustment("Apple", new BigDecimal("28"));

        adjustment.applyToSale(sale);
    }
}