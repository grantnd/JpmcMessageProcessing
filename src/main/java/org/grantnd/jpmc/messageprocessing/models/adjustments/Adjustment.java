package org.grantnd.jpmc.messageprocessing.models.adjustments;

import org.grantnd.jpmc.messageprocessing.models.Sale;

import static org.grantnd.jpmc.messageprocessing.models.validation.Validate.validateProductType;

public abstract class Adjustment {
    private final String productType;

    public Adjustment(String productType) {
        this.productType = validateProductType(productType);
    }

    public String getProductType() {
        return this.productType;
    }

    public abstract void applyToSale(Sale sale);
}