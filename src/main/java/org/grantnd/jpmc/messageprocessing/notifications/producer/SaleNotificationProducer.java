package org.grantnd.jpmc.messageprocessing.notifications.producer;

import org.grantnd.jpmc.messageprocessing.notifications.consumer.SaleNotificationConsumer;

public interface SaleNotificationProducer {
    void start();

    void registerConsumer(SaleNotificationConsumer consumer);
}
