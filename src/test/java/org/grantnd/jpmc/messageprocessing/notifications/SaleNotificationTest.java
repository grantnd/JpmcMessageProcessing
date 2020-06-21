package org.grantnd.jpmc.messageprocessing.notifications;

import org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.AddAdjustmentOperation;
import org.junit.Test;

import java.math.BigDecimal;

import static org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification.*;
import static org.junit.Assert.*;

public class SaleNotificationTest {

    @Test
    public void createSaleNotification_valuesStored() {
        SaleNotification saleNotification = createSaleNotification("Apple", BigDecimal.ONE);

        assertEquals("Apple", saleNotification.getProductType());
        assertEquals(BigDecimal.ONE, saleNotification.getValue());
        assertEquals(1, saleNotification.getOccurrences());
    }

    @Test
    public void createSaleNotificationWithMultipleOccurrence_valuesStored() {
        SaleNotification saleNotification = createSaleNotificationWithMultipleOccurrence("Apple", BigDecimal.ONE, 3);

        assertEquals("Apple", saleNotification.getProductType());
        assertEquals(BigDecimal.ONE, saleNotification.getValue());
        assertEquals(3, saleNotification.getOccurrences());
        assertNull(saleNotification.getAdjustmentOperation());
    }

    @Test
    public void createSaleNotificationWithAdjustment_valuesStored() {
        AddAdjustmentOperation adjustmentOperation = new AddAdjustmentOperation("Apple", new BigDecimal("2"));
        SaleNotification saleNotification = createSaleNotificationWithAdjustment("Apple", BigDecimal.ONE, adjustmentOperation);

        assertEquals("Apple", saleNotification.getProductType());
        assertEquals(BigDecimal.ONE, saleNotification.getValue());
        assertEquals(1, saleNotification.getOccurrences());
        assertEquals(adjustmentOperation, saleNotification.getAdjustmentOperation());
    }

    @Test
    public void hasAdjustmentOperation_hasAdjustment_returnsTrue() {
        SaleNotification saleNotificationWithAdjustment = createSaleNotificationWithAdjustment("Apple", BigDecimal.ONE,
                new AddAdjustmentOperation("Apple", new BigDecimal("2")));

        assertTrue(saleNotificationWithAdjustment.hasAdjustmentOperation());
    }

    @Test
    public void hasAdjustmentOperation_hasNoAdjustment_returnsFalse() {
        SaleNotification saleNotificationWithAdjustment = createSaleNotification("Apple", BigDecimal.ONE);

        assertFalse(saleNotificationWithAdjustment.hasAdjustmentOperation());
    }
}