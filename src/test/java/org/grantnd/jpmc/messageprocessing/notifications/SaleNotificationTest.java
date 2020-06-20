package org.grantnd.jpmc.messageprocessing.notifications;

import org.junit.Test;

import java.math.BigDecimal;

import static org.grantnd.jpmc.messageprocessing.notifications.SaleNotification.*;
import static org.junit.Assert.*;

public class SaleNotificationTest {
    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotification_nullProductType_throwsException() {
        createSaleNotification(null, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotification_emptyProductType_throwsException() {
        createSaleNotification("", BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotification_negativeValue_throwsException() {
        createSaleNotification("Apple", new BigDecimal(-1));
    }

    @Test
    public void createSaleNotification_productTypeStored() {
        SaleNotification saleNotification = createSaleNotification("Apple", BigDecimal.ONE);

        assertEquals("Apple", saleNotification.getProductType());
    }

    @Test
    public void createSaleNotification_valueStored() {
        SaleNotification saleNotification = createSaleNotification("Apple", BigDecimal.ONE);

        assertEquals(BigDecimal.ONE, saleNotification.getValue());
    }


    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotificationWithMultipleOccurrence_nullProductType_throwsException() {
        createSaleNotificationWithMultipleOccurrence(null, BigDecimal.ONE, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotificationWithMultipleOccurrence_emptyProductType_throwsException() {
        createSaleNotificationWithMultipleOccurrence("", BigDecimal.ONE, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotificationWithMultipleOccurrence_negativeValue_throwsException() {
        createSaleNotificationWithMultipleOccurrence("Apple", new BigDecimal(-1), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotificationWithMultipleOccurrence_negativeOccurrence_throwsException() {
        createSaleNotificationWithMultipleOccurrence("Apple", BigDecimal.ONE, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotificationWithMultipleOccurrence_zeroOccurrence_throwsException() {
        createSaleNotificationWithMultipleOccurrence("Apple", BigDecimal.ONE, 0);
    }

    @Test
    public void createSaleNotificationWithMultipleOccurrence_productTypeStored() {
        SaleNotification saleNotification = createSaleNotificationWithMultipleOccurrence("Apple", BigDecimal.ONE, 1);

        assertEquals("Apple", saleNotification.getProductType());
    }

    @Test
    public void createSaleNotificationWithMultipleOccurrence_valueStored() {
        SaleNotification saleNotification = createSaleNotificationWithMultipleOccurrence("Apple", BigDecimal.ONE, 1);

        assertEquals(BigDecimal.ONE, saleNotification.getValue());
    }

    @Test
    public void createSaleNotificationWithMultipleOccurrence_occurrenceStored() {
        SaleNotification saleNotification = createSaleNotificationWithMultipleOccurrence("Apple", BigDecimal.ONE, 1);

        assertEquals(1, saleNotification.getOccurrences());
    }


    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotificationWithAdjustment_nullProductType_throwsException() {
        createSaleNotificationWithAdjustment(null, BigDecimal.ONE, AdjustmentOperation.Add);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotificationWithAdjustment_emptyProductType_throwsException() {
        createSaleNotificationWithAdjustment("", BigDecimal.ONE, AdjustmentOperation.Add);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSaleNotificationWithAdjustment_negativeValue_throwsException() {
        createSaleNotificationWithAdjustment("Apple", new BigDecimal(-1), AdjustmentOperation.Add);
    }

    @Test
    public void createSaleNotificationWithAdjustment_productTypeStored() {
        SaleNotification saleNotification = createSaleNotificationWithAdjustment("Apple", BigDecimal.ONE, AdjustmentOperation.Add);

        assertEquals("Apple", saleNotification.getProductType());
    }

    @Test
    public void createSaleNotificationWithAdjustment_valueStored() {
        SaleNotification saleNotification = createSaleNotificationWithAdjustment("Apple", BigDecimal.ONE, AdjustmentOperation.Add);

        assertEquals(BigDecimal.ONE, saleNotification.getValue());
    }

    @Test
    public void createSaleNotificationWithAdjustment_adjustmentOperationStored() {
        SaleNotification saleNotification = createSaleNotificationWithAdjustment("Apple", BigDecimal.ONE, AdjustmentOperation.Add);

        assertEquals( AdjustmentOperation.Add, saleNotification.getAdjustmentOperation());
    }


    @Test
    public void hasAdjustmentOperation_hasAdjustment_returnsTrue() {
        SaleNotification saleNotificationWithAdjustment = createSaleNotificationWithAdjustment("Apple", BigDecimal.ONE, AdjustmentOperation.Add);

        assertTrue(saleNotificationWithAdjustment.hasAdjustmentOperation());
    }

    @Test
    public void hasAdjustmentOperation_hasNoAdjustment_returnsFalse() {
        SaleNotification saleNotificationWithAdjustment = createSaleNotification("Apple", BigDecimal.ONE);

        assertFalse(saleNotificationWithAdjustment.hasAdjustmentOperation());
    }
}