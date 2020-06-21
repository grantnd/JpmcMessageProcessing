package org.grantnd.jpmc.messageprocessing.reports.adjustments;

import org.grantnd.jpmc.messageprocessing.models.adjustments.Adjustment;
import org.grantnd.jpmc.messageprocessing.repository.AdjustmentsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjustmentsReport {
    private final AdjustmentsRepository adjustmentsRepository;
    private final Map<String, List<ReportLine>> linesGroupedByProductType;

    public AdjustmentsReport(AdjustmentsRepository adjustmentsRepository) {
        this.adjustmentsRepository = adjustmentsRepository;
        this.linesGroupedByProductType = buildReport();
    }

    public Map<String, List<ReportLine>> buildReport() {
        Map<String, List<ReportLine>> linesGroupedByProductType = new HashMap<>();

        for (Map.Entry<String, List<Adjustment>> entry : adjustmentsRepository.getAdjustmentsGroupedByProductType().entrySet()) {
            linesGroupedByProductType.put(
                    entry.getKey(),
                    buildReportLinesForAdjustmentsGroupedByProductType(entry.getValue()));
        }

        return linesGroupedByProductType;
    }

    private List<ReportLine> buildReportLinesForAdjustmentsGroupedByProductType(List<Adjustment> adjustments) {
        return adjustments.stream()
                .map(ReportLine::new)
                .collect(Collectors.toList());
    }

    public Map<String, List<ReportLine>> getLinesGroupedByProductType() {
        return linesGroupedByProductType;
    }

    public static class ReportLine {
        private final Adjustment adjustment;

        public ReportLine(Adjustment adjustment) {
            this.adjustment = adjustment;
        }

        public Adjustment getAdjustment() {
            return adjustment;
        }
    }
}