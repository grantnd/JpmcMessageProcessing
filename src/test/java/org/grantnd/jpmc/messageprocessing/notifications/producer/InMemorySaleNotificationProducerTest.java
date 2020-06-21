package org.grantnd.jpmc.messageprocessing.notifications.producer;

import org.grantnd.jpmc.messageprocessing.notifications.consumer.SaleNotificationConsumer;
import org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification.createSaleNotification;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InMemorySaleNotificationProducerTest {
    @Mock
    private SaleNotificationConsumer consumer;

    private InMemorySaleNotificationProducer target;

    @Test
    public void start_consumerRegisteredButNoNotificationsProvided_registeredConsumerIsNotCalled() {
        target = new InMemorySaleNotificationProducer(new ArrayList<>());
        target.registerConsumer(consumer);

        target.start();

        verifyNoMoreInteractions(consumer);
    }

    @Test
    public void start_consumerRegisteredAndNotificationsProvided_registeredConsumerIsCalled() {
        ArrayList<SaleNotification> saleNotifications = new ArrayList<>();
        saleNotifications.add(createSaleNotification("Apple", new BigDecimal("1.0")));
        saleNotifications.add(createSaleNotification("Orange", new BigDecimal("3.0")));
        saleNotifications.add(createSaleNotification("Pear", new BigDecimal("6.0")));
        target = new InMemorySaleNotificationProducer(saleNotifications);
        target.registerConsumer(consumer);

        target.start();

        verify(consumer, times(3)).handleSaleNotification(any());
    }
}