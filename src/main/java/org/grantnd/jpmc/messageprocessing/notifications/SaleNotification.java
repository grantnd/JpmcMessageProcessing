package org.grantnd.jpmc.messageprocessing.notifications;

import java.math.BigDecimal;

public class SaleNotification {
    private final String productType;
    private final BigDecimal value;
    private final int occurrences;
    private final AdjustmentOperation adjustmentOperation;

    private SaleNotification(String productType, BigDecimal value, int occurrences, AdjustmentOperation adjustmentOperation) {
        this.productType = validateProductType(productType);
        this.value = validateValue(value);
        this.occurrences = validateOccurrences(occurrences);
        this.adjustmentOperation = adjustmentOperation;
    }

    private String validateProductType(String productType) {
        if(productType == null || productType.equals(""))
            throw new IllegalArgumentException("Product type must not be null or empty");

        return productType;
    }

    private BigDecimal validateValue(BigDecimal value) {
        if(value.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Value must not be negative");

        return value;
    }

    private int validateOccurrences(int occurrences) {
        if(occurrences < 1)
            throw new IllegalArgumentException("Occurrences must be greater than 0");

        return occurrences;
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