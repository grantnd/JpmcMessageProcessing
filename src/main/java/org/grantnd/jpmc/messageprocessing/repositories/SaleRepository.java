package org.grantnd.jpmc.messageprocessing.repositories;

import org.grantnd.jpmc.messageprocessing.models.Sale;
import org.grantnd.jpmc.messageprocessing.models.adjustments.Adjustment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SaleRepository {
    private final List<Sale> sales;

    public SaleRepository() {
        this.sales = new ArrayList<>();
    }

    public void add(Sale sale) {
        this.sales.add(sale);
    }

    public void applyAdjustment(final Adjustment adjustment) {
        for (Sale sale : getSalesForProductType(adjustment.getProductType())) {
            try {
                adjustment.applyToSale(sale);
            } catch (Exception e) {
                System.err.println(String.format("Exception raised applying adjustment: [%s] to sale: [%s]. %s", adjustment, sale, e.getMessage()));
            }
        }
    }

    private List<Sale> getSalesForProductType(String productType) {
        return sales.stream()
                .filter(sale -> sale.getProductType().equals(productType))
                .collect(Collectors.toList());
    }

    public Map<String, List<Sale>> getSalesGroupedByProductType() {
        return sales.stream()
                .collect(Collectors.groupingBy(Sale::getProductType));
    }

    public List<Sale> getAllSales() {
        return sales;
    }
}