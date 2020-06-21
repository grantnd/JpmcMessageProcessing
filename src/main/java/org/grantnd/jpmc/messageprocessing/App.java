package org.grantnd.jpmc.messageprocessing;

import org.grantnd.jpmc.messageprocessing.models.adjustments.AdjustmentFactory;
import org.grantnd.jpmc.messageprocessing.notifications.consumer.SaleNotificationConsumer;
import org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.AddAdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.MultiplyAdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.SubtractAdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.producer.InMemorySaleNotificationProducer;
import org.grantnd.jpmc.messageprocessing.notifications.producer.SaleNotificationProducer;
import org.grantnd.jpmc.messageprocessing.reports.adjustments.AdjustmentsReportWriter;
import org.grantnd.jpmc.messageprocessing.reports.sales.SalesReportWriter;
import org.grantnd.jpmc.messageprocessing.repository.AdjustmentsRepository;
import org.grantnd.jpmc.messageprocessing.repository.SalesRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification.*;

public class App {
    public static void main(String[] args) {
        SaleNotificationConsumer saleNotificationConsumer = composeNotificationConsumer();
        SaleNotificationProducer saleNotificationProducer = new InMemorySaleNotificationProducer(buildSampleSaleNotifications());
        saleNotificationProducer.registerConsumer(saleNotificationConsumer);

        saleNotificationProducer.start();
    }

    private static SaleNotificationConsumer composeNotificationConsumer() {
        SalesRepository salesRepository = new SalesRepository();
        SalesReportWriter salesReportWriter = new SalesReportWriter();

        AdjustmentFactory adjustmentFactory = new AdjustmentFactory();
        AdjustmentsRepository adjustmentsRepository = new AdjustmentsRepository();
        AdjustmentsReportWriter adjustmentsReportWriter = new AdjustmentsReportWriter();

        return new SaleNotificationProcessor(salesRepository,
                salesReportWriter,
                adjustmentFactory,
                adjustmentsRepository,
                adjustmentsReportWriter);
    }

    private static List<SaleNotification> buildSampleSaleNotifications() {
        List<SaleNotification> saleNotifications = new ArrayList<>();

        saleNotifications.add(createSaleNotification("Apple", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotification("Apple", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotificationWithMultipleOccurrence("Pear", new BigDecimal("1.4"), 30));
        saleNotifications.add(createSaleNotification("Pear", new BigDecimal("3.4")));
        saleNotifications.add(createSaleNotification("Grapes", new BigDecimal("7.0")));
        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("2.1")));
        saleNotifications.add(createSaleNotification("Grapes", new BigDecimal("7.1")));
        saleNotifications.add(createSaleNotification("Pear", new BigDecimal("1.4")));
        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("2.1")));
        saleNotifications.add(createSaleNotificationWithAdjustment("Strawberries", new BigDecimal("4.0"), new MultiplyAdjustmentOperation("Strawberries", new BigDecimal("1.4"))));

        saleNotifications.add(createSaleNotification("Strawberries", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotification("Apple", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotificationWithMultipleOccurrence("Orange", new BigDecimal("0.5"), 3));
        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("2.1")));
        saleNotifications.add(createSaleNotificationWithAdjustment("Apple", new BigDecimal("3.0"), new AddAdjustmentOperation("Apple", new BigDecimal("0.25"))));
        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("7.1")));
        saleNotifications.add(createSaleNotification("Pear", new BigDecimal("1.4")));
        saleNotifications.add(createSaleNotification("Apple", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotification("Apple", new BigDecimal("5.2")));
        saleNotifications.add(createSaleNotification("Pear", new BigDecimal("1.7")));

        saleNotifications.add(createSaleNotification("Apple", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotification("Strawberries", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotificationWithAdjustment("Apple", new BigDecimal("5.1"), new AddAdjustmentOperation("Apple", new BigDecimal("0.1"))));
        saleNotifications.add(createSaleNotificationWithAdjustment("Strawberries", new BigDecimal("50"), new SubtractAdjustmentOperation("Strawberries", new BigDecimal("0.1"))));
        saleNotifications.add(createSaleNotification("Apple", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("7.1")));
        saleNotifications.add(createSaleNotificationWithMultipleOccurrence("Apple", new BigDecimal("0.4"), 20));
        saleNotifications.add(createSaleNotificationWithMultipleOccurrence("Strawberries", new BigDecimal("2.5"), 5));
        saleNotifications.add(createSaleNotification("Pear", new BigDecimal("3.0")));
        saleNotifications.add(createSaleNotification("Plum", new BigDecimal("2.1")));

        saleNotifications.add(createSaleNotification("Banana", new BigDecimal("0.5")));
        saleNotifications.add(createSaleNotification("Banana", new BigDecimal("0.3")));
        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("7.1")));
        saleNotifications.add(createSaleNotificationWithMultipleOccurrence("Pear", new BigDecimal("0.4"), 5));
        saleNotifications.add(createSaleNotification("Strawberries", new BigDecimal("2.1")));
        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("7.1")));
        saleNotifications.add(createSaleNotification("Strawberries", new BigDecimal("2.1")));
        saleNotifications.add(createSaleNotification("Apple", new BigDecimal("1.1")));
        saleNotifications.add(createSaleNotificationWithAdjustment("Orange", new BigDecimal("5.1"), new AddAdjustmentOperation("Orange", new BigDecimal("4.2"))));
        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("7.1")));

        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("2.1")));
        saleNotifications.add(createSaleNotification("Strawberries", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotification("Strawberries", new BigDecimal("2.1")));
        saleNotifications.add(createSaleNotification("Plum", new BigDecimal("2.1")));
        saleNotifications.add(createSaleNotification("Apple", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotification("Banana", new BigDecimal("0.2")));
        saleNotifications.add(createSaleNotification("Pear", new BigDecimal("0.9")));
        saleNotifications.add(createSaleNotificationWithAdjustment("Pear", new BigDecimal("50"), new SubtractAdjustmentOperation("Pear", new BigDecimal("0.25"))));
        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("2.1")));
        saleNotifications.add(createSaleNotificationWithAdjustment("Banana", new BigDecimal("4.0"), new MultiplyAdjustmentOperation("Banana", new BigDecimal("1.1"))));

        return saleNotifications;
    }
}