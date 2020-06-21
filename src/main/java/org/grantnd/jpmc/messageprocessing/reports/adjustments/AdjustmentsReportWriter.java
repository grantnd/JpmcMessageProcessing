package org.grantnd.jpmc.messageprocessing.reports.adjustments;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjustmentsReportWriter {
    public void writeAdjustmentsReport(AdjustmentsReport adjustmentsReport) {
        StringBuilder reportBuilder = new StringBuilder();
        appendTitle(reportBuilder);

        if (adjustmentsReport.getLinesGroupedByProductType().isEmpty()) {
            reportBuilder.append("No adjustments to report\n");
        } else {
            appendAdjustmentsPerProductType(reportBuilder, adjustmentsReport.getLinesGroupedByProductType());
        }

        System.out.println(reportBuilder.toString());
    }

    private void appendTitle(StringBuilder reportBuilder) {
        reportBuilder.append("Adjustments Report\n");
        reportBuilder.append("------------------\n");
    }

    private void appendAdjustmentsPerProductType(StringBuilder reportBuilder, Map<String, List<AdjustmentsReport.ReportLine>> adjustmentsGroupedByProductType) {
        List<String> sortedProductTypes = adjustmentsGroupedByProductType.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        for (String productType : sortedProductTypes) {
            reportBuilder.append(productType);
            reportBuilder.append("\n");

            for (AdjustmentsReport.ReportLine line : adjustmentsGroupedByProductType.get(productType)) {
                reportBuilder.append("\t");
                reportBuilder.append(line.getAdjustment());
                reportBuilder.append("\n");
            }
        }
    }
}