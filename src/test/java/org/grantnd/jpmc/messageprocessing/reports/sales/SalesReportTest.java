package org.grantnd.jpmc.messageprocessing.reports.sales;

import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.grantnd.jpmc.messageprocessing.repositories.SaleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SalesReportTest {
    @Mock
    private SaleRepository saleRepository;

    @Test
    public void getLines_noSales_emptyReport() {
        when(saleRepository.getSalesGroupedByProductType()).thenReturn(new HashMap<>());
        SalesReport salesReport = new SalesReport(saleRepository);

        List<SalesReport.ReportLine> lines = salesReport.getLines();

        assertTrue(lines.isEmpty());
    }

    @Test
    public void getLines_multipleSales_countAndTotalValuesAreCorrect() {
        Map<String, List<Sale>> salesByProductType = buildSalesByProductType();
        when(saleRepository.getSalesGroupedByProductType()).thenReturn(salesByProductType);
        SalesReport salesReport = new SalesReport(saleRepository);

        List<SalesReport.ReportLine> lines = salesReport.getLines();

        SalesReport.ReportLine appleLine = lines.stream().filter(l -> l.getProductType().equals("Apple")).findFirst().get();
        assertNotNull(appleLine);
        assertEquals(2, appleLine.getCount());
        assertEquals(new BigDecimal("11.0"), appleLine.getTotal());

        SalesReport.ReportLine orangeLine = lines.stream().filter(l -> l.getProductType().equals("Orange")).findFirst().get();
        assertNotNull(orangeLine);
        assertEquals(3, orangeLine.getCount());
        assertEquals(new BigDecimal("6.7"), orangeLine.getTotal());
    }

    private Map<String, List<Sale>> buildSalesByProductType() {
        Map<String, List<Sale>> salesByProductType = new HashMap<>();
        List<Sale> appleSales = new ArrayList<>();
        salesByProductType.put("Apple", appleSales);
        appleSales.add(new Sale("Apple", new BigDecimal("4.0")));
        appleSales.add(new Sale("Apple", new BigDecimal("7.0")));

        List<Sale> orangeSales = new ArrayList<>();
        salesByProductType.put("Orange", orangeSales);
        orangeSales.add(new Sale("Orange", new BigDecimal("1.0")));
        orangeSales.add(new Sale("Orange", new BigDecimal("3.5")));
        orangeSales.add(new Sale("Orange", new BigDecimal("2.2")));

        return salesByProductType;
    }
}