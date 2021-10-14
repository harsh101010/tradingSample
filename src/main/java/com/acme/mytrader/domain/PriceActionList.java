package com.acme.mytrader.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Domain class to represent price and ACTION (BUY or SELL)
 */

public class PriceActionList {

    public PriceActionList(){
        this.tradeCriterias = new ArrayList<>();
    }
    private List<TradeCriteria> tradeCriterias;
    public void addPriceAction(TradeCriteria tradeCriteria){
        tradeCriterias.add(tradeCriteria);
    }

    public void removePriceAction(TradeCriteria tradeCriteria){
        tradeCriterias.remove(tradeCriteria);
    }

    public List<TradeCriteria> getTradeCriterias() {
        return tradeCriterias;
    }

    public static class TradeCriteria{
        int quantity;
        Action action;

        public TradeCriteria(int quantity, Action action) {
            this.quantity = quantity;
            this.action = action;
        }

        public int getQuantity() {
            return quantity;
        }

        public Action getAction() {
            return action;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TradeCriteria that = (TradeCriteria) o;
            return quantity == that.quantity && action == that.action;
        }

        @Override
        public int hashCode() {
            return Objects.hash(quantity, action);
        }
    }

    public enum Action {
        BUY,
        SELL
    }
}
