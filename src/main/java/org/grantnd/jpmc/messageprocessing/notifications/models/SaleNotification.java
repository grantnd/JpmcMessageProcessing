package org.grantnd.jpmc.messageprocessing.notifications.models;

import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.AdjustmentOperation;

import java.math.BigDecimal;

public class SaleNotification {
    private final String productType;
    private final BigDecimal value;
    private final int occurrences;
    private final AdjustmentOperation adjustmentOperation;

    private SaleNotification(String productType, BigDecimal value, int occurrences, AdjustmentOperation adjustmentOperation) {
        this.productType = productType;
        this.value = value;
        this.occurrences = occurrences;
        this.adjustmentOperation = adjustmentOperation;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public boolean hasAdjustmentOperation() {
        return adjustmentOperation != null;
    }

    public AdjustmentOperation getAdjustmentOperation() {
        return adjustmentOperation;
    }

    public static SaleNotification createSaleNotification(String productType, BigDecimal value) {
        return new SaleNotification(productType, value, 1, null);
    }

    public static SaleNotification createSaleNotificationWithMultipleOccurrence(String productType, BigDecimal value, int occurrences) {
        return new SaleNotification(productType, value, occurrences, null);
    }

    public static SaleNotification createSaleNotificationWithAdjustment(String productType, BigDecimal value, AdjustmentOperation adjustmentType) {
        return new SaleNotification(productType, value, 1, adjustmentType);
    }
}