package org.grantnd.jpmc.messageprocessing.reports.adjustments;

import org.grantnd.jpmc.messageprocessing.models.adjustments.AddAdjustment;
import org.grantnd.jpmc.messageprocessing.models.adjustments.Adjustment;
import org.grantnd.jpmc.messageprocessing.models.adjustments.MultiplyAdjustment;
import org.grantnd.jpmc.messageprocessing.models.adjustments.SubtractAdjustment;
import org.grantnd.jpmc.messageprocessing.repositories.AdjustmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdjustmentsReportTest {
    @Mock
    private AdjustmentRepository adjustmentRepository;

    @Test
    public void getLinesGroupedByProductType_noAdjustments_emptyReport() {
        when(adjustmentRepository.getAdjustmentsGroupedByProductType()).thenReturn(new HashMap<>());
        AdjustmentsReport adjustmentsReport = new AdjustmentsReport(adjustmentRepository);

        Map<String, List<AdjustmentsReport.ReportLine>> linesGroupedByProductType = adjustmentsReport.getLinesGroupedByProductType();

        assertTrue(linesGroupedByProductType.isEmpty());
    }

    @Test
    public void getLinesGroupedByProductType_multipleAdjustments_groupedCorrectly() {
        Map<String, List<Adjustment>> adjustmentsByProductType = buildAdjustmentsByProductType();
        when(adjustmentRepository.getAdjustmentsGroupedByProductType()).thenReturn(adjustmentsByProductType);
        AdjustmentsReport adjustmentsReport = new AdjustmentsReport(adjustmentRepository);

        Map<String, List<AdjustmentsReport.ReportLine>> linesGroupedByProductType = adjustmentsReport.getLinesGroupedByProductType();

        List<AdjustmentsReport.ReportLine> appleLines = linesGroupedByProductType.get("Apple");
        assertEquals(2, appleLines.size());
        appleLines.stream()
                .map(l -> l.getAdjustment())
                .forEach(a -> assertTrue(adjustmentsByProductType.get("Apple").contains(a)));

        List<AdjustmentsReport.ReportLine> orangeLines = linesGroupedByProductType.get("Orange");
        assertEquals(3, orangeLines.size());
        orangeLines.stream()
                .map(l -> l.getAdjustment())
                .forEach(a -> assertTrue(adjustmentsByProductType.get("Orange").contains(a)));
    }

    private Map<String, List<Adjustment>> buildAdjustmentsByProductType() {
        Map<String, List<Adjustment>> adjustmentsByProductType = new HashMap<>();
        List<Adjustment> appleAdjustments = new ArrayList<>();
        adjustmentsByProductType.put("Apple", appleAdjustments);
        appleAdjustments.add(new AddAdjustment("Apple", new BigDecimal("4.0")));
        appleAdjustments.add(new SubtractAdjustment("Apple", new BigDecimal("7.0")));

        List<Adjustment> orangeAdjustments = new ArrayList<>();
        adjustmentsByProductType.put("Orange", orangeAdjustments);
        orangeAdjustments.add(new AddAdjustment("Orange", new BigDecimal("1.0")));
        orangeAdjustments.add(new SubtractAdjustment("Orange", new BigDecimal("3.5")));
        orangeAdjustments.add(new MultiplyAdjustment("Orange", new BigDecimal("2.2")));

        return adjustmentsByProductType;
    }
}