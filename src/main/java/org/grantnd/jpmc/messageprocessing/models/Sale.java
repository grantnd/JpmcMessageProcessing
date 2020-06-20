package org.grantnd.jpmc.messageprocessing.models;

import java.math.BigDecimal;

import static org.grantnd.jpmc.messageprocessing.models.validation.Validate.validateProductType;
import static org.grantnd.jpmc.messageprocessing.models.validation.Validate.validateSaleValue;

public class Sale {
    private final String productType;
    private BigDecimal value;

    public Sale(String productType, BigDecimal value) {
        this.productType = validateProductType(productType);
        this.value = validateSaleValue(value);
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = validateSaleValue(value);
    }

    @Override
    public String toString() {
        return String.format("Product Type: %s, Value: %s", this.productType, this.value);
    }
}