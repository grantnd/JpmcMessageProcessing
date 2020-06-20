package org.grantnd.jpmc.messageprocessing.models;

public interface Adjustment {
    String getProductType();

    void applyToSale(Sale sale);
}
