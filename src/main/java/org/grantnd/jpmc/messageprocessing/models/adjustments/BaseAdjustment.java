package org.grantnd.jpmc.messageprocessing.models.adjustments;

import org.grantnd.jpmc.messageprocessing.models.Adjustment;
import org.grantnd.jpmc.messageprocessing.models.Sale;

import static org.grantnd.jpmc.messageprocessing.models.validation.Validate.validateProductType;

public abstract class BaseAdjustment implements Adjustment {
    private final String productType;

    public BaseAdjustment(String productType) {
        this.productType = validateProductType(productType);
    }

    public String getProductType() {
        return this.productType;
    }

    @Override
    public abstract void applyToSale(Sale sale);
}
