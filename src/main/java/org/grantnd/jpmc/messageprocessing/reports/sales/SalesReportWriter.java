package org.grantnd.jpmc.messageprocessing.reports.sales;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class SalesReportWriter {
    public void writeSalesReport(SalesReport salesReport) {
        StringBuilder reportBuilder = new StringBuilder();
        appendTitle(reportBuilder);

        if (salesReport.getLines().isEmpty()) {
            reportBuilder.append("No sales to report\n");
        } else {
            appendHeader(reportBuilder);
            List<SalesReport.ReportLine> reportLines = sortReportLinesByProductName(salesReport.getLines());
            appendReportLines(reportBuilder, reportLines);
        }

        System.out.println(reportBuilder.toString());
    }

    private void appendTitle(StringBuilder reportBuilder) {
        reportBuilder.append("Sales Report\n");
        reportBuilder.append("------------\n");
    }

    private void appendHeader(StringBuilder reportBuilder) {
        reportBuilder.append("Product Type\t");
        reportBuilder.append("Count\t");
        reportBuilder.append("Total\n");
    }

    private List<SalesReport.ReportLine> sortReportLinesByProductName(List<SalesReport.ReportLine> lines) {
        return lines.stream()
                .sorted(comparing(SalesReport.ReportLine::getProductType))
                .collect(Collectors.toList());
    }

    private void appendReportLines(StringBuilder reportBuilder, List<SalesReport.ReportLine> reportLines) {
        for (SalesReport.ReportLine reportLine : reportLines) {
            reportBuilder.append(reportLine.getProductType());
            reportBuilder.append("\t");
            reportBuilder.append(reportLine.getCount());
            reportBuilder.append("\t");
            reportBuilder.append(reportLine.getTotal());
            reportBuilder.append("\n");
        }
    }
}