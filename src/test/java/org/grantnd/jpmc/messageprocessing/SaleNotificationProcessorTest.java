package org.grantnd.jpmc.messageprocessing;

import org.grantnd.jpmc.messageprocessing.models.adjustments.AddAdjustment;
import org.grantnd.jpmc.messageprocessing.models.adjustments.Adjustment;
import org.grantnd.jpmc.messageprocessing.models.adjustments.AdjustmentFactory;
import org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.AddAdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.reports.adjustments.AdjustmentsReportWriter;
import org.grantnd.jpmc.messageprocessing.reports.sales.SalesReportWriter;
import org.grantnd.jpmc.messageprocessing.repository.AdjustmentsRepository;
import org.grantnd.jpmc.messageprocessing.repository.SalesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.grantnd.jpmc.messageprocessing.notifications.models.SaleNotification.*;
import static org.junit.Assert.assertTrue;
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
    private AdjustmentsRepository adjustmentsRepository;

    @Mock
    private AdjustmentsReportWriter adjustmentsReportWriter;

    private SaleNotificationProcessor target;

    @Before
    public void setUp() {
        target = new SaleNotificationProcessor(salesRepository, salesReportWriter, adjustmentFactory, adjustmentsRepository, adjustmentsReportWriter);
    }

    @Test
    public void handleSaleNotification_oneNotification_saleRecorded() {
        callHandleSaleNotificationNTimes(1);

        verify(salesRepository, times(1)).add(any());
    }

    @Test
    public void handleSaleNotification_multipleNotifications_allSalesRecorded() {
        callHandleSaleNotificationNTimes(5);

        verify(salesRepository, times(5)).add(any());
    }

    @Test
    public void handleSaleNotification_notificationWithAdjustment_adjustmentAppliedToSales() {
        AddAdjustmentOperation adjustmentOperation = new AddAdjustmentOperation("Apple", new BigDecimal("2"));
        SaleNotification saleNotificationWithAdjustment = createSaleNotificationWithAdjustment("Apple", BigDecimal.ONE, adjustmentOperation);
        Adjustment adjustment = new AddAdjustment("Apple", new BigDecimal("2"));
        when(adjustmentFactory.createAdjustmentFromAdjustmentOperation(adjustmentOperation)).thenReturn(adjustment);

        target.handleSaleNotification(saleNotificationWithAdjustment);

        verify(salesRepository, times(1)).applyAdjustment(adjustment);
    }

    @Test
    public void handleSaleNotification_notificationWithMultipleOccurrences_allSalesRecorded() {
        callHandleSaleNotificationNTimes(5);

        target.handleSaleNotification(createSaleNotificationWithMultipleOccurrence("Apple", BigDecimal.ONE, 4));

        verify(salesRepository, times(9)).add(any());
    }

    @Test
    public void handleSaleNotification_receiveLessThanTenNotifications_salesReportNotWritten() {
        callHandleSaleNotificationNTimes(5);

        verifyZeroInteractions(salesReportWriter);
    }

    @Test
    public void handleSaleNotification_receiveTenNotifications_salesReportWrittenOnce() {
        callHandleSaleNotificationNTimes(10);

        verify(salesReportWriter, times(1)).writeSalesReport(any());
    }

    @Test
    public void handleSaleNotification_receiveThirtyFiveNotifications_salesReportWrittenThreeTimes() {
        callHandleSaleNotificationNTimes(35);

        verify(salesReportWriter, times(3)).writeSalesReport(any());
    }

    @Test
    public void handleSaleNotification_receiveFiftyNotifications_adjustmentReportWrittenAndProcessingPaused() {
        NonHaltingSaleNotificationProcessor nonHaltingTarget = new NonHaltingSaleNotificationProcessor(salesRepository, salesReportWriter, adjustmentFactory, adjustmentsRepository, adjustmentsReportWriter);
        callHandleSaleNotificationNTimes(50, nonHaltingTarget);

        verify(salesReportWriter, times(5)).writeSalesReport(any());
        verify(adjustmentsReportWriter).writeAdjustmentsReport(any());
        assertTrue(nonHaltingTarget.processingWasPaused);
    }

    private void callHandleSaleNotificationNTimes(int numberOfCalls) {
        callHandleSaleNotificationNTimes(numberOfCalls, target);
    }

    private void callHandleSaleNotificationNTimes(int numberOfCalls, SaleNotificationProcessor target) {
        for (int i = 0; i < numberOfCalls; i++) {
            target.handleSaleNotification(createSaleNotification("Apple", new BigDecimal("0.2")));
        }
    }

    private static class NonHaltingSaleNotificationProcessor extends SaleNotificationProcessor {
        private boolean processingWasPaused;

        public NonHaltingSaleNotificationProcessor(SalesRepository salesRepository, SalesReportWriter salesReportWriter, AdjustmentFactory adjustmentFactory, AdjustmentsRepository adjustmentsRepository, AdjustmentsReportWriter adjustmentsReportWriter) {
            super(salesRepository, salesReportWriter, adjustmentFactory, adjustmentsRepository, adjustmentsReportWriter);
        }

        @Override
        protected void pauseProcessing() {
            this.processingWasPaused = true;
        }
    }
}