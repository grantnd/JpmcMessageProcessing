package org.grantnd.jpmc.messageprocessing.models.adjustments;

import org.grantnd.jpmc.messageprocessing.models.Sale;

import java.math.BigDecimal;

import static org.grantnd.jpmc.messageprocessing.models.validation.Validate.validateAdjustmentDelta;

public class AddAdjustment extends BaseAdjustment {
    private final BigDecimal delta;

    public AddAdjustment(String productType, BigDecimal delta) {
        super(productType);
        this.delta = validateAdjustmentDelta(delta);
    }

    @Override
    public void applyToSale(Sale sale) {
        sale.setValue(sale.getValue().add(delta));
    }

    @Override
    public String toString() {
        return "Add " + delta;
    }
}
