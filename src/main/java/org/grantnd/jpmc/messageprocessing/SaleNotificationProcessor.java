package org.grantnd.jpmc.messageprocessing;

import org.grantnd.jpmc.messageprocessing.models.Adjustment;
import org.grantnd.jpmc.messageprocessing.models.adjustments.AdjustmentFactory;
import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.grantnd.jpmc.messageprocessing.notifications.SaleNotification;
import org.grantnd.jpmc.messageprocessing.notifications.consumer.SaleNotificationConsumer;
import org.grantnd.jpmc.messageprocessing.reports.adjustments.AdjustmentReportWriter;
import org.grantnd.jpmc.messageprocessing.reports.sales.SalesReportWriter;
import org.grantnd.jpmc.messageprocessing.repository.AdjustmentRepository;
import org.grantnd.jpmc.messageprocessing.repository.SalesRepository;

public class SaleNotificationProcessor implements SaleNotificationConsumer {
    private static final int REPORT_INTERVAL_SALES = 10;
    private static final int REPORT_INTERVAL_ADJUSTMENTS = 50;

    private final SalesRepository salesRepository;
    private final SalesReportWriter salesReportWriter;
    private final AdjustmentFactory adjustmentFactory;
    private final AdjustmentRepository adjustmentRepository;
    private final AdjustmentReportWriter adjustmentReportWriter;

    private int numberOfNotificationsProcessed = 0;

    public SaleNotificationProcessor(SalesRepository salesRepository,
                                     SalesReportWriter salesReportWriter,
                                     AdjustmentFactory adjustmentFactory,
                                     AdjustmentRepository adjustmentRepository,
                                     AdjustmentReportWriter adjustmentReportWriter) {
        this.salesRepository = salesRepository;
        this.salesReportWriter = salesReportWriter;
        this.adjustmentFactory = adjustmentFactory;
        this.adjustmentRepository = adjustmentRepository;
        this.adjustmentReportWriter = adjustmentReportWriter;
    }

    @Override
    public void handleSaleNotification(SaleNotification saleNotification) {
        storeSalesFromNotification(saleNotification);

        if (saleNotification.hasAdjustmentOperation()) {
            handleAdjustment(saleNotification);
        }

        numberOfNotificationsProcessed++;

        writeReports();
    }

    private void storeSalesFromNotification(SaleNotification saleNotification) {
        for (int i = 0; i < saleNotification.getOccurrences(); i++) {
            Sale sale = new Sale(saleNotification.getProductType(), saleNotification.getValue());
            salesRepository.add(sale);
        }
    }

    private void handleAdjustment(SaleNotification saleNotification) {
        Adjustment adjustment = adjustmentFactory.getAdjustmentFromSaleNotification(saleNotification);
        adjustmentRepository.add(adjustment);
        salesRepository.applyAdjustment(adjustment);
    }

    private void writeReports() {
        if (numberOfNotificationsProcessed % REPORT_INTERVAL_SALES == 0) {
            salesReportWriter.writeSalesNumberAndTotalByProductTypeReport();
        }

        if (numberOfNotificationsProcessed % REPORT_INTERVAL_ADJUSTMENTS == 0) {
            adjustmentReportWriter.writeAdjustmentsByProductTypeReport();
        }
    }
}
