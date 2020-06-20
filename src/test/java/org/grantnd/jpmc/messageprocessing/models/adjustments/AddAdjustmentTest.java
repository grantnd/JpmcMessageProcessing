package org.grantnd.jpmc.messageprocessing.models.adjustments;

import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AddAdjustmentTest {
    @Test(expected = IllegalArgumentException.class)
    public void construct_productNameIsNull_throwsException() {
        new AddAdjustment(null, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_productNameIsBlank_throwsException() {
        new AddAdjustment("", BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_deltaIsZero_throwsException() {
        new AddAdjustment("Apple", BigDecimal.ZERO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_deltaIsLessThanZero_throwsException() {
        new AddAdjustment("Apple", new BigDecimal(-1));
    }

    @Test
    public void applyToSale_deltaIsAddedToSale() {
        Sale sale = new Sale("Apple", new BigDecimal(4));
        AddAdjustment adjustment = new AddAdjustment("Apple", new BigDecimal(7));

        adjustment.applyToSale(sale);

        assertEquals(new BigDecimal(11), sale.getValue());
    }
}