package org.grantnd.jpmc.messageprocessing.reports.sales;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SalesReportWriterTest {
    @Mock
    private SalesReport salesReport;

    private SalesReportWriter target;

    @Before
    public void setUp() {
        target = new SalesReportWriter();
    }

    @Test
    public void writeSalesReport_reportLinesAccessed() {
        target.writeSalesReport(salesReport);

        verify(salesReport).getLines();
    }
}