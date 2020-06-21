package org.grantnd.jpmc.messageprocessing.models;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class SaleTest {
    @Test(expected = IllegalArgumentException.class)
    public void construct_productTypeIsNull_throwsException() {
        new Sale(null, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_productTypeIsBlank_throwsException() {
        new Sale("", BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_valueIsLessThanZero_throwsException() {
        new Sale("Apple", new BigDecimal("-1"));
    }

    @Test
    public void construct_productTypeAndValueStored() {
        Sale sale = new Sale("Apple", BigDecimal.ZERO);

        assertEquals("Apple", sale.getProductType());
        assertEquals(BigDecimal.ZERO, sale.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setValue_valueIsLessThanZero_throwsException() {
        Sale sale = new Sale("Apple", BigDecimal.ZERO);

        sale.setValue(new BigDecimal("-2"));
    }

    @Test
    public void setValue_valueIsNotLessThanZero_valueStored() {
        Sale sale = new Sale("Apple", BigDecimal.ZERO);

        sale.setValue(new BigDecimal("2"));

        assertEquals(new BigDecimal("2"), sale.getValue());
    }
}