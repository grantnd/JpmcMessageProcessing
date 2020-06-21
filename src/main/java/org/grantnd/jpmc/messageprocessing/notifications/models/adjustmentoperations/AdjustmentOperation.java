package org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations;

import static org.grantnd.jpmc.messageprocessing.models.validation.Validate.validateProductType;

public abstract class AdjustmentOperation {
    private final String productType;

    public AdjustmentOperation(String productType) {
        this.productType = validateProductType(productType);
    }

    public String getProductType() {
        return this.productType;
    }
}