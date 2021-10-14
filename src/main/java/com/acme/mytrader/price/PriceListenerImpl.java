package com.acme.mytrader.price;

import com.acme.mytrader.domain.PriceEvent;
import com.acme.mytrader.strategy.TradingStrategy;

public class PriceListenerImpl implements PriceListener {
    PriceEvent priceEvent = null;
    TradingStrategy tradingStrategy;
    public PriceEvent getPriceEvent() {
        return priceEvent;
    }


    public PriceListenerImpl(TradingStrategy tradingStrategy) {
        this.tradingStrategy = tradingStrategy;
    }

    @Override
    public void priceUpdate(String security, double price) {
        System.out.println("price update" + security + " price :"+ price);
        tradingStrategy.listenTransaction(security, price);
    }
}