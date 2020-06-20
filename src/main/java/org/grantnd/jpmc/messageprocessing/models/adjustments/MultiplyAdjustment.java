package org.grantnd.jpmc.messageprocessing.models.adjustments;

import org.grantnd.jpmc.messageprocessing.models.Sale;

import java.math.BigDecimal;

import static org.grantnd.jpmc.messageprocessing.models.validation.Validate.validateAdjustmentFactor;

public class MultiplyAdjustment extends BaseAdjustment {
    private final BigDecimal factor;

    public MultiplyAdjustment(String productType, int factor) {
        super(productType);
        this.factor = validateAdjustmentFactor(new BigDecimal(factor));
    }

    @Override
    public void applyToSale(Sale sale) {
        sale.setValue(sale.getValue().multiply(factor));
    }

    @Override
    public String toString() {
        return "Multiply " + factor.toBigInteger();
    }
}