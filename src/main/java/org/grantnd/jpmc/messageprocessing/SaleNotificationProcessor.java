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
import org.grantnd.jpmc.messageprocessing.repositories.AdjustmentRepository;
import org.grantnd.jpmc.messageprocessing.repositories.SaleRepository;

import java.io.IOException;

public class SaleNotificationProcessor implements SaleNotificationConsumer {
    private static final int REPORT_INTERVAL_SALES = 10;
    private static final int REPORT_INTERVAL_ADJUSTMENTS = 50;

    private final SaleRepository saleRepository;
    private final SalesReportWriter salesReportWriter;
    private final AdjustmentFactory adjustmentFactory;
    private final AdjustmentRepository adjustmentRepository;
    private final AdjustmentsReportWriter adjustmentsReportWriter;

    private int numberOfNotificationsProcessed = 0;

    public SaleNotificationProcessor(SaleRepository saleRepository,
                                     SalesReportWriter salesReportWriter,
                                     AdjustmentFactory adjustmentFactory,
                                     AdjustmentRepository adjustmentRepository,
                                     AdjustmentsReportWriter adjustmentsReportWriter) {
        this.saleRepository = saleRepository;
        this.salesReportWriter = salesReportWriter;
        this.adjustmentFactory = adjustmentFactory;
        this.adjustmentRepository = adjustmentRepository;
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
            saleRepository.add(sale);
        }
    }

    private void handleAdjustmentOperation(AdjustmentOperation adjustmentOperation) {
        Adjustment adjustment = adjustmentFactory.createAdjustmentFromAdjustmentOperation(adjustmentOperation);
        adjustmentRepository.add(adjustment);
        saleRepository.applyAdjustment(adjustment);
    }

    private void writeReports() {
        if (shouldWriteSalesReport()) {
            salesReportWriter.writeSalesReport(new SalesReport(saleRepository));
        }

        if (shouldWriteAdjustmentReportAndPause()) {
            System.out.println("Pausing application...");
            adjustmentsReportWriter.writeAdjustmentsReport(new AdjustmentsReport(adjustmentRepository));
            pauseProcessing();
        }
    }

    private boolean shouldWriteSalesReport() {
        return numberOfNotificationsProcessed % REPORT_INTERVAL_SALES == 0;
    }

    private boolean shouldWriteAdjustmentReportAndPause() {
        return numberOfNotificationsProcessed % REPORT_INTERVAL_ADJUSTMENTS == 0;
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