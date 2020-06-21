package org.grantnd.jpmc.messageprocessing.repositories;

import org.grantnd.jpmc.messageprocessing.models.adjustments.AddAdjustment;
import org.grantnd.jpmc.messageprocessing.models.adjustments.Adjustment;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AdjustmentRepositoryTest {

    private AdjustmentRepository target;

    @Before
    public void setUp() {
        target = new AdjustmentRepository();
    }

    @Test
    public void add_oneAdjustment_isStored() {
        target.add(new AddAdjustment("Apple", BigDecimal.ONE));

        List<Adjustment> allAdjustments = target.getAllAdjustments();

        assertEquals(1, allAdjustments.size());
    }

    @Test
    public void add_multipleAdjustments_areStored() {
        target.add(new AddAdjustment("Apple", BigDecimal.ONE));
        target.add(new AddAdjustment("Apple", BigDecimal.ONE));
        target.add(new AddAdjustment("Apple", BigDecimal.ONE));

        List<Adjustment> allAdjustments = target.getAllAdjustments();

        assertEquals(3, allAdjustments.size());
    }

    @Test
    public void getAdjustmentsGroupedByProductType_noAdjustments_returnsEmptyMap() {
        Map<String, List<Adjustment>> adjustmentsGroupedByProductType = target.getAdjustmentsGroupedByProductType();

        assertNotNull(adjustmentsGroupedByProductType);
        assertEquals(0, adjustmentsGroupedByProductType.size());
    }

    @Test
    public void getAdjustmentsGroupedByProductType_multipleAdjustmentsWithDifferentProductType_returnsCorrectGrouping() {
        Adjustment adjustment1 = new AddAdjustment("Apple", BigDecimal.ONE);
        Adjustment adjustment2 = new AddAdjustment("Apple", BigDecimal.ONE);
        Adjustment adjustment3 = new AddAdjustment("Orange", BigDecimal.ONE);
        target.add(adjustment1);
        target.add(adjustment2);
        target.add(adjustment3);

        Map<String, List<Adjustment>> adjustmentsGroupedByProductType = target.getAdjustmentsGroupedByProductType();

        assertNotNull(adjustmentsGroupedByProductType);
        assertEquals(2, adjustmentsGroupedByProductType.size());

        List<Adjustment> appleGrouping = adjustmentsGroupedByProductType.get("Apple");
        assertEquals(2, appleGrouping.size());
        assertTrue(appleGrouping.contains(adjustment1));
        assertTrue(appleGrouping.contains(adjustment2));

        List<Adjustment> orangeGrouping = adjustmentsGroupedByProductType.get("Orange");
        assertEquals(1, orangeGrouping.size());
        assertTrue(orangeGrouping.contains(adjustment3));
    }
}