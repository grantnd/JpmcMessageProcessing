package org.grantnd.jpmc.messageprocessing.repositories;

import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.grantnd.jpmc.messageprocessing.models.adjustments.AddAdjustment;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SaleRepositoryTest {

    private SaleRepository target;

    @Before
    public void setUp() {
        target = new SaleRepository();
    }

    @Test
    public void add_oneSale_isStored() {
        target.add(new Sale("Apple", BigDecimal.ONE));

        List<Sale> allSales = target.getAllSales();

        assertEquals(1, allSales.size());
    }

    @Test
    public void add_multipleSales_areStored() {
        target.add(new Sale("Apple", BigDecimal.ONE));
        target.add(new Sale("Apple", BigDecimal.ONE));
        target.add(new Sale("Apple", BigDecimal.ONE));

        List<Sale> allSales = target.getAllSales();

        assertEquals(3, allSales.size());
    }

    @Test
    public void applyAdjustment_onlyAppliedToSalesOfSameProductType() {
        target.add(new Sale("Apple", BigDecimal.ONE));
        target.add(new Sale("Orange", BigDecimal.ONE));

        target.applyAdjustment(new AddAdjustment("Orange", BigDecimal.TEN));

        List<Sale> allSales = target.getAllSales();

        Sale appleSale = allSales.get(0);
        assertEquals("Apple", appleSale.getProductType());
        assertEquals(BigDecimal.ONE, appleSale.getValue());

        Sale orangeSale = allSales.get(1);
        assertEquals("Orange", orangeSale.getProductType());
        assertEquals(new BigDecimal("11"), orangeSale.getValue());
    }

    @Test
    public void getSalesGroupedByProductType_noSales_returnsEmptyMap() {
        Map<String, List<Sale>> salesGroupedByProductType = target.getSalesGroupedByProductType();

        assertNotNull(salesGroupedByProductType);
        assertEquals(0, salesGroupedByProductType.size());
    }

    @Test
    public void getSalesGroupedByProductType_multipleSalesWithDifferentProductType_returnsCorrectGrouping() {
        Sale sale1 = new Sale("Apple", BigDecimal.ONE);
        Sale sale2 = new Sale("Apple", BigDecimal.ONE);
        Sale sale3 = new Sale("Orange", BigDecimal.ONE);
        target.add(sale1);
        target.add(sale2);
        target.add(sale3);

        Map<String, List<Sale>> salesGroupedByProductType = target.getSalesGroupedByProductType();

        assertNotNull(salesGroupedByProductType);
        assertEquals(2, salesGroupedByProductType.size());

        List<Sale> appleGrouping = salesGroupedByProductType.get("Apple");
        assertEquals(2, appleGrouping.size());
        assertTrue(appleGrouping.contains(sale1));
        assertTrue(appleGrouping.contains(sale2));

        List<Sale> orangeGrouping = salesGroupedByProductType.get("Orange");
        assertEquals(1, orangeGrouping.size());
        assertTrue(orangeGrouping.contains(sale3));
    }
}