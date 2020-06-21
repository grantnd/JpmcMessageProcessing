package org.grantnd.jpmc.messageprocessing.notifications.producer;

import org.grantnd.jpmc.messageprocessing.notifications.consumer.SaleNotificationConsumer;
import org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification;

import java.util.List;

public class InMemorySaleNotificationProducer implements SaleNotificationProducer {
    private final List<SaleNotification> salesNotifications;
    private SaleNotificationConsumer consumer;

    public InMemorySaleNotificationProducer(List<SaleNotification> salesNotifications) {
        this.salesNotifications = salesNotifications;
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
}