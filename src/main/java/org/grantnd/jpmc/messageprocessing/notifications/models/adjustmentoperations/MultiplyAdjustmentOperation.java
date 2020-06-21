package org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations;

import java.math.BigDecimal;

public class MultiplyAdjustmentOperation extends AdjustmentOperation {
    private final BigDecimal factor;

    public MultiplyAdjustmentOperation(String productType, BigDecimal factor) {
        super(productType);
        this.factor = factor;
    }

    public BigDecimal getFactor() {
        return factor;
    }
}