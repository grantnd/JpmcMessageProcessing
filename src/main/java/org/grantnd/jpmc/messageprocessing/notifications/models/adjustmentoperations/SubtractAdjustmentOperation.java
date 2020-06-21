package org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations;

import java.math.BigDecimal;

public class SubtractAdjustmentOperation extends AdjustmentOperation {
    private final BigDecimal delta;

    public SubtractAdjustmentOperation(String productType, BigDecimal delta) {
        super(productType);
        this.delta = delta;
    }

    public BigDecimal getDelta() {
        return delta;
    }
}