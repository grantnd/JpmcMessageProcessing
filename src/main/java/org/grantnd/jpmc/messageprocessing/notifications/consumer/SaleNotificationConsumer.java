package org.grantnd.jpmc.messageprocessing.notifications.consumer;

import org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification;

public interface SaleNotificationConsumer {
    void handleSaleNotification(SaleNotification saleNotification);
}
