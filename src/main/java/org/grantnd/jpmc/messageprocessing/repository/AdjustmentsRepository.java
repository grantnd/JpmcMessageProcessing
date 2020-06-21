package org.grantnd.jpmc.messageprocessing.repository;

import org.grantnd.jpmc.messageprocessing.models.adjustments.Adjustment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjustmentsRepository {
    private final List<Adjustment> adjustments;

    public AdjustmentsRepository() {
        adjustments = new ArrayList<>();
    }

    public void add(Adjustment adjustment) {
        adjustments.add(adjustment);
    }

    public List<Adjustment> getAllAdjustments() {
        return adjustments;
    }

    public Map<String, List<Adjustment>> getAdjustmentsGroupedByProductType() {
        return adjustments.stream()
                .collect(Collectors.groupingBy(Adjustment::getProductType));
    }
}