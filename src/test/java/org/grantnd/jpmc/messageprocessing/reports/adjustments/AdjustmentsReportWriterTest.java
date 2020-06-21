package org.grantnd.jpmc.messageprocessing.reports.adjustments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AdjustmentsReportWriterTest {
    @Mock
    private AdjustmentsReport salesReport;

    private AdjustmentsReportWriter target;

    @Before
    public void setUp() {
        target = new AdjustmentsReportWriter();
    }

    @Test
    public void writeAdjustmentsReport_reportLinesAccessed() {
        target.writeAdjustmentsReport(salesReport);

        verify(salesReport).getLinesGroupedByProductType();
    }
}