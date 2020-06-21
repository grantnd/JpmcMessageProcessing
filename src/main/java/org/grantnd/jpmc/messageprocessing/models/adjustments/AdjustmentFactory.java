package org.grantnd.jpmc.messageprocessing.models.adjustments;

import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.AddAdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.AdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.MultiplyAdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.SubtractAdjustmentOperation;

public class AdjustmentFactory {
    public Adjustment createAdjustmentFromAdjustmentOperation(AdjustmentOperation adjustmentOperation) {
        if (adjustmentOperation instanceof AddAdjustmentOperation)
            return new AddAdjustment(adjustmentOperation.getProductType(), ((AddAdjustmentOperation) adjustmentOperation).getDelta());
        else if (adjustmentOperation instanceof SubtractAdjustmentOperation)
            return new SubtractAdjustment(adjustmentOperation.getProductType(), ((SubtractAdjustmentOperation) adjustmentOperation).getDelta());
        else if (adjustmentOperation instanceof MultiplyAdjustmentOperation)
            return new MultiplyAdjustment(adjustmentOperation.getProductType(), ((MultiplyAdjustmentOperation) adjustmentOperation).getFactor());
        else
            throw new IllegalArgumentException("Unrecognised adjustment operation");
    }
}