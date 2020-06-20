package org.grantnd.jpmc.messageprocessing.notifications.producer;

import org.grantnd.jpmc.messageprocessing.notifications.AdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.SaleNotification;
import org.grantnd.jpmc.messageprocessing.notifications.consumer.SaleNotificationConsumer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.grantnd.jpmc.messageprocessing.notifications.SaleNotification.*;

public class InMemorySaleNotificationProducer implements SaleNotificationProducer {
    private SaleNotificationConsumer consumer;
    private List<SaleNotification> salesNotifications;

    public InMemorySaleNotificationProducer() {
        generateSampleData();
    }

    @Override
    public void start() {
        for (SaleNotification saleNotification : salesNotifications) {
            consumer.handleSaleNotification(saleNotification);
        }
    }

    @Override
    public void registerConsumer(SaleNotificationConsumer consumer) {
        this.consumer = consumer;
    }

    private void generateSampleData() {
        salesNotifications = new ArrayList<>();
        salesNotifications.add(createSaleNotification("Apple", new BigDecimal("0.2")));
        salesNotifications.add(createSaleNotification("Apple", new BigDecimal("0.2")));
        salesNotifications.add(createSaleNotificationWithMultipleOccurrence("Pear", new BigDecimal("0.4"), 5));
        salesNotifications.add(createSaleNotification("Pear", new BigDecimal("0.4")));
        salesNotifications.add(createSaleNotificationWithAdjustment("Pear", new BigDecimal("50"), AdjustmentOperation.Subtract));
        salesNotifications.add(createSaleNotification("Orange", new BigDecimal("2.1")));
        salesNotifications.add(createSaleNotificationWithAdjustment("Orange", new BigDecimal("5.1"), AdjustmentOperation.Add));
        salesNotifications.add(createSaleNotification("Orange", new BigDecimal("7.1")));
        salesNotifications.add(createSaleNotification("Orange", new BigDecimal("2.1")));
    }
}