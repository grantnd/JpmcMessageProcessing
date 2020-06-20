package org.grantnd.jpmc.messageprocessing;

import org.grantnd.jpmc.messageprocessing.models.adjustments.AdjustmentFactory;
import org.grantnd.jpmc.messageprocessing.notifications.consumer.SaleNotificationConsumer;
import org.grantnd.jpmc.messageprocessing.notifications.producer.InMemorySaleNotificationProducer;
import org.grantnd.jpmc.messageprocessing.notifications.producer.SaleNotificationProducer;
import org.grantnd.jpmc.messageprocessing.reports.adjustments.AdjustmentReportWriter;
import org.grantnd.jpmc.messageprocessing.reports.sales.SalesReportWriter;
import org.grantnd.jpmc.messageprocessing.repository.AdjustmentRepository;
import org.grantnd.jpmc.messageprocessing.repository.SalesRepository;

public class App {
    public static void main(String[] args) {
        SaleNotificationProducer saleNotificationProducer = new InMemorySaleNotificationProducer();
        saleNotificationProducer.registerConsumer(composeNotificationConsumer());

        saleNotificationProducer.start();
    }

    private static SaleNotificationConsumer composeNotificationConsumer() {
        SalesRepository salesRepository = new SalesRepository();
        SalesReportWriter salesReportWriter = new SalesReportWriter(salesRepository);

        AdjustmentFactory adjustmentFactory = new AdjustmentFactory();
        AdjustmentRepository adjustmentRepository = new AdjustmentRepository();
        AdjustmentReportWriter adjustmentReportWriter = new AdjustmentReportWriter(adjustmentRepository);

        return new SaleNotificationProcessor(salesRepository, salesReportWriter, adjustmentFactory, adjustmentRepository, adjustmentReportWriter);
    }
}