package org.grantnd.jpmc.messageprocessing.models.validation;

import java.math.BigDecimal;

public class Validate {
    public static String validateProductType(String productType) {
        if(productType == null || productType.equals(""))
            throw new IllegalArgumentException("Product type must not be null or empty");

        return productType;
    }

    public static BigDecimal validateSaleValue(BigDecimal value) {
        if(value.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Sale value must not be negative");

        return value;
    }

    public static BigDecimal validateAdjustmentDelta(BigDecimal delta) {
        if(delta.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Adjustment delta must not be zero or negative");

        return delta;
    }

    public static BigDecimal validateAdjustmentFactor(BigDecimal factor) {
        if(factor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Adjustment factor not be zero or negative");

        return factor;
    }
}
