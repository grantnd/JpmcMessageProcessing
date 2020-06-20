package org.grantnd.jpmc.messageprocessing.notifications.producer;

import org.grantnd.jpmc.messageprocessing.notifications.consumer.SaleNotificationConsumer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InMemorySaleNotificationProducerTest {
    @Mock
    private SaleNotificationConsumer consumer;

    private InMemorySaleNotificationProducer target;

    @Before
    public void setUp() {
        target = new InMemorySaleNotificationProducer();
    }

    @Test
    public void start_registeredConsumerIsCalled() {
        target.registerConsumer(consumer);

        target.start();

        verify(consumer, atLeastOnce()).handleSaleNotification(any());
    }
}