package org.grantnd.jpmc.messageprocessing;

import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.grantnd.jpmc.messageprocessing.models.adjustments.Adjustment;
import org.grantnd.jpmc.messageprocessing.models.adjustments.AdjustmentFactory;
import org.grantnd.jpmc.messageprocessing.notifications.consumer.SaleNotificationConsumer;
import org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.AdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.reports.adjustments.AdjustmentsReport;
import org.grantnd.jpmc.messageprocessing.reports.adjustments.AdjustmentsReportWriter;
import org.grantnd.jpmc.messageprocessing.reports.sales.SalesReport;
import org.grantnd.jpmc.messageprocessing.reports.sales.SalesReportWriter;
import org.grantnd.jpmc.messageprocessing.repository.AdjustmentsRepository;
import org.grantnd.jpmc.messageprocessing.repository.SalesRepository;

import java.io.IOException;

public class SaleNotificationProcessor implements SaleNotificationConsumer {
    private static final int REPORT_INTERVAL_SALES = 10;
    private static final int REPORT_INTERVAL_ADJUSTMENTS = 50;

    private final SalesRepository salesRepository;
    private final SalesReportWriter salesReportWriter;
    private final AdjustmentFactory adjustmentFactory;
    private final AdjustmentsRepository adjustmentsRepository;
    private final AdjustmentsReportWriter adjustmentsReportWriter;

    private int numberOfNotificationsProcessed = 0;

    public SaleNotificationProcessor(SalesRepository salesRepository,
                                     SalesReportWriter salesReportWriter,
                                     AdjustmentFactory adjustmentFactory,
                                     AdjustmentsRepository adjustmentsRepository,
                                     AdjustmentsReportWriter adjustmentsReportWriter) {
        this.salesRepository = salesRepository;
        this.salesReportWriter = salesReportWriter;
        this.adjustmentFactory = adjustmentFactory;
        this.adjustmentsRepository = adjustmentsRepository;
        this.adjustmentsReportWriter = adjustmentsReportWriter;
    }

    @Override
    public void handleSaleNotification(SaleNotification saleNotification) {
        storeSalesFromNotification(saleNotification);

        if (saleNotification.hasAdjustmentOperation()) {
            handleAdjustmentOperation(saleNotification.getAdjustmentOperation());
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

    private void handleAdjustmentOperation(AdjustmentOperation adjustmentOperation) {
        Adjustment adjustment = adjustmentFactory.createAdjustmentFromAdjustmentOperation(adjustmentOperation);
        adjustmentsRepository.add(adjustment);
        salesRepository.applyAdjustment(adjustment);
    }

    private void writeReports() {
        if (numberOfNotificationsProcessed % REPORT_INTERVAL_SALES == 0) {
            salesReportWriter.writeSalesReport(new SalesReport(salesRepository));
        }

        if (numberOfNotificationsProcessed % REPORT_INTERVAL_ADJUSTMENTS == 0) {
            System.out.println("Pausing application...");
            adjustmentsReportWriter.writeAdjustmentsReport(new AdjustmentsReport(adjustmentsRepository));
            pauseProcessing();
        }
    }

    protected void pauseProcessing() {
        System.out.print("Press any key to continue: ");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}