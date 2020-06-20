package org.grantnd.jpmc.messageprocessing.reports.adjustments;

import org.grantnd.jpmc.messageprocessing.models.Adjustment;
import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.grantnd.jpmc.messageprocessing.repository.AdjustmentRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjustmentReportWriter {
    private final AdjustmentRepository adjustmentRepository;

    public AdjustmentReportWriter(AdjustmentRepository adjustmentRepository) {
        this.adjustmentRepository = adjustmentRepository;
    }

    public void writeAdjustmentsByProductTypeReport() {
        if (!adjustmentRepository.hasAdjustments())
            System.out.println("No sales to report");

        StringBuilder reportBuilder = new StringBuilder();

        writeAdjustmentsPerProductType(reportBuilder);

        System.out.println(reportBuilder.toString());
    }


    private void writeAdjustmentsPerProductType(StringBuilder reportBuilder) {
        for (Map.Entry<String, List<Adjustment>> entry : getAdjustmentsGroupedByProductType().entrySet()) {
            String productType = entry.getKey();

            reportBuilder.append(productType);
            reportBuilder.append("\n");

            for (Adjustment adjustment : entry.getValue()) {
                reportBuilder.append("\t");
                reportBuilder.append(adjustment);
                reportBuilder.append("\n");
            }
        }
    }

    private Map<String, List<Adjustment>> getAdjustmentsGroupedByProductType() {
        List<Adjustment> allSales = adjustmentRepository.getAllAdjustments();
        return allSales.stream().collect(Collectors.groupingBy(Adjustment::getProductType));
    }

    private BigDecimal getTotalOfSaleValues(List<Sale> value) {
        BigDecimal total = BigDecimal.ZERO;

        for (Sale sale : value) {
            total = total.add(sale.getValue());
        }

        return total;
    }

    private void writeSalesPerProductTypeLine(StringBuilder reportBuilder, String productType, int count, BigDecimal total) {
        reportBuilder.append(productType);
        reportBuilder.append("\t");
        reportBuilder.append(count);
        reportBuilder.append("\t");
        reportBuilder.append(total);
        reportBuilder.append("\n");
    }
}
