package org.grantnd.jpmc.messageprocessing.reports.sales;

import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.grantnd.jpmc.messageprocessing.repository.SalesRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalesReportWriter {
    private final SalesRepository salesRepository;

    public SalesReportWriter(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public void writeSalesNumberAndTotalByProductTypeReport() {
        if (!salesRepository.hasSales())
            System.out.println("No sales to report");

        StringBuilder reportBuilder = new StringBuilder();

        writeHeader(reportBuilder);
        writeSalesPerProductType(reportBuilder);

        System.out.println(reportBuilder.toString());
    }

    private void writeHeader(StringBuilder reportBuilder) {
        reportBuilder.append("Product Type\t");
        reportBuilder.append("Count\t");
        reportBuilder.append("Total\n");
    }

    private void writeSalesPerProductType(StringBuilder reportBuilder) {
        for (Map.Entry<String, List<Sale>> entry : getSalesGroupedByProductType().entrySet()) {
            String productType = entry.getKey();
            int count = entry.getValue().size();
            BigDecimal total = getTotalOfSaleValues(entry.getValue());

            writeSalesPerProductTypeLine(reportBuilder, productType, count, total);
        }
    }

    private Map<String, List<Sale>> getSalesGroupedByProductType() {
        List<Sale> allSales = salesRepository.getAllSales();
        return allSales.stream().collect(Collectors.groupingBy(Sale::getProductType));
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
