package org.grantnd.jpmc.messageprocessing.reports.sales;

import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.grantnd.jpmc.messageprocessing.repositories.SaleRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesReport {
    private final SaleRepository saleRepository;
    private final List<ReportLine> lines;

    public SalesReport(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
        this.lines = buildReport();
    }

    private List<ReportLine> buildReport() {
        List<ReportLine> lines = new ArrayList<>();

        for (Map.Entry<String, List<Sale>> entry : saleRepository.getSalesGroupedByProductType().entrySet()) {
            lines.add(buildReportLineForSalesGroupedByProductType(entry));
        }

        return lines;
    }

    private ReportLine buildReportLineForSalesGroupedByProductType(Map.Entry<String, List<Sale>> entry) {
        String productType = entry.getKey();
        int count = entry.getValue().size();
        BigDecimal total = getTotalOfSaleValues(entry.getValue());

        return new ReportLine(productType, count, total);
    }

    private BigDecimal getTotalOfSaleValues(List<Sale> value) {
        BigDecimal total = BigDecimal.ZERO;

        for (Sale sale : value) {
            total = total.add(sale.getValue());
        }

        return total;
    }

    public List<ReportLine> getLines() {
        return lines;
    }

    public static class ReportLine {
        private final String productType;
        private final int count;
        private final BigDecimal total;

        public ReportLine(String productType, int count, BigDecimal total) {
            this.productType = productType;
            this.count = count;
            this.total = total;
        }

        public String getProductType() {
            return productType;
        }

        public int getCount() {
            return count;
        }

        public BigDecimal getTotal() {
            return total;
        }
    }
}