package org.grantnd.jpmc.messageprocessing.repository;

import org.grantnd.jpmc.messageprocessing.models.Adjustment;

import java.util.ArrayList;
import java.util.List;

public class AdjustmentRepository {
    private final List<Adjustment> adjustments;

    public AdjustmentRepository() {
        adjustments = new ArrayList<>();
    }

    public void add(Adjustment adjustment) {
        adjustments.add(adjustment);
    }

    public List<Adjustment> getAllAdjustments() {
        return adjustments;
    }

    public boolean hasAdjustments() {
        return !adjustments.isEmpty();
    }
}
