package org.grantnd.jpmc.messageprocessing.models.adjustments;

import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.AddAdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.AdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.MultiplyAdjustmentOperation;
import org.grantnd.jpmc.messageprocessing.notifications.models.adjustmentoperations.SubtractAdjustmentOperation;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AdjustmentFactoryTest {

    private AdjustmentFactory target;

    @Before
    public void setUp() {
        target = new AdjustmentFactory();
    }

    @Test
    public void createAdjustmentFromAdjustmentOperation_addAdjustmentOperation() {
        AdjustmentOperation addAdjustmentOperation = new AddAdjustmentOperation("Apple", new BigDecimal("2.0"));

        Adjustment adjustment = target.createAdjustmentFromAdjustmentOperation(addAdjustmentOperation);

        assertTrue(adjustment instanceof AddAdjustment);
        assertEquals("Apple", adjustment.getProductType());
    }

    @Test
    public void createAdjustmentFromAdjustmentOperation_subtractAdjustmentOperation() {
        AdjustmentOperation addAdjustmentOperation = new SubtractAdjustmentOperation("Apple", new BigDecimal("2.0"));

        Adjustment adjustment = target.createAdjustmentFromAdjustmentOperation(addAdjustmentOperation);

        assertTrue(adjustment instanceof SubtractAdjustment);
        assertEquals("Apple", adjustment.getProductType());
    }

    @Test
    public void createAdjustmentFromAdjustmentOperation_multiplyAdjustmentOperation() {
        AdjustmentOperation addAdjustmentOperation = new MultiplyAdjustmentOperation("Apple", new BigDecimal("2.0"));

        Adjustment adjustment = target.createAdjustmentFromAdjustmentOperation(addAdjustmentOperation);

        assertTrue(adjustment instanceof MultiplyAdjustment);
        assertEquals("Apple", adjustment.getProductType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAdjustmentFromAdjustmentOperation_unknownAdjustmentOperation_throwsException() {
        AdjustmentOperation addAdjustmentOperation = new TestAdjustmentOperation("Apple");

        Adjustment adjustment = target.createAdjustmentFromAdjustmentOperation(addAdjustmentOperation);
    }

    public static class TestAdjustmentOperation extends AdjustmentOperation {
        public TestAdjustmentOperation(String productType) {
            super(productType);
        }
    }
}