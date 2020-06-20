package org.grantnd.jpmc.messageprocessing;

import org.grantnd.jpmc.messageprocessing.models.Adjustment;
import org.grantnd.jpmc.messageprocessing.models.adjustments.AdjustmentFactory;
import org.grantnd.jpmc.messageprocessing.models.adjustments.AddAdjustment;
import org.grantnd.jpmc.messageprocessing.notifications.AdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.SaleNotification;
import org.grantnd.jpmc.messageprocessing.reports.adjustments.AdjustmentReportWriter;
import org.grantnd.jpmc.messageprocessing.reports.sales.SalesReportWriter;
import org.grantnd.jpmc.messageprocessing.repository.AdjustmentRepository;
import org.grantnd.jpmc.messageprocessing.repository.SalesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.grantnd.jpmc.messageprocessing.notifications.SaleNotification.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SaleNotificationProcessorTest {
    @Mock
    private SalesRepository salesRepository;

    @Mock
    private SalesReportWriter salesReportWriter;

    @Mock
    private AdjustmentFactory adjustmentFactory;

    @Mock
    private AdjustmentRepository adjustmentRepository;

    @Mock
    private AdjustmentReportWriter adjustmentReportWriter;

    private SaleNotificationProcessor target;

    @Before
    public void setUp() {
        target = new SaleNotificationProcessor(salesRepository, salesReportWriter, adjustmentFactory, adjustmentRepository, adjustmentReportWriter);
    }

    @Test
    public void handleSaleNotification_multipleNotifications_allSalesRecorded() {
        handleNotifications(5);

        verify(salesRepository, times(5)).add(any());
    }

    @Test
    public void handleSaleNotification_notificationWithAdjustment_adjustmentAppliedToSales() {
        SaleNotification saleNotificationWithAdjustment = createSaleNotificationWithAdjustment("Apple", BigDecimal.ONE, AdjustmentOperation.Add);
        Adjustment adjustment = new AddAdjustment("Apple", BigDecimal.ONE);
        when(adjustmentFactory.getAdjustmentFromSaleNotification(saleNotificationWithAdjustment)).thenReturn(adjustment);

        target.handleSaleNotification(saleNotificationWithAdjustment);

        verify(salesRepository, times(1)).applyAdjustment(adjustment);
    }

    @Test
    public void handleSaleNotification_notificationWithMultipleOccurrences_allSalesRecorded() {
        handleNotifications(5);

        target.handleSaleNotification(createSaleNotificationWithMultipleOccurrence("Apple", BigDecimal.ONE, 4));

        verify(salesRepository, times(9)).add(any());
    }

    @Test
    public void handleSaleNotification_receiveLessThanTenNotifications_salesReportNotWritten() {
        handleNotifications(5);

        verifyZeroInteractions(salesReportWriter);
    }

    @Test
    public void handleSaleNotification_receiveTenNotifications_salesReportWrittenOnce() {
        handleNotifications(10);

        verify(salesReportWriter, times(1)).writeSalesNumberAndTotalByProductTypeReport();
    }

    @Test
    public void handleSaleNotification_receiveThirtyFiveNotifications_salesReportWrittenThreeTimes() {
        handleNotifications(35);

        verify(salesReportWriter, times(3)).writeSalesNumberAndTotalByProductTypeReport();
    }

    private void handleNotifications(int numberOfNotificationsToHandle) {
        for (int i = 0; i < numberOfNotificationsToHandle; i++) {
            target.handleSaleNotification(createSaleNotification("Apple", new BigDecimal("0.2")));
        }
    }
}