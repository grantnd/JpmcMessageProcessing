package org.grantnd.jpmc.messageprocessing.models.adjustments;

import org.grantnd.jpmc.messageprocessing.models.Adjustment;
import org.grantnd.jpmc.messageprocessing.notifications.SaleNotification;

public class AdjustmentFactory {
    public Adjustment getAdjustmentFromSaleNotification(SaleNotification saleNotification) {
        switch (saleNotification.getAdjustmentOperation()) {
            case Add:
                return new AddAdjustment(saleNotification.getProductType(), saleNotification.getValue());
            case Subtract:
                return new SubtractAdjustment(saleNotification.getProductType(), saleNotification.getValue());
            case Multiply:
                return new MultiplyAdjustment(saleNotification.getProductType(), 10);
            default:
                throw new RuntimeException("Unrecognised adjustment operation");
        }
    }
}
