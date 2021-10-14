package com.acme.mytrader.price;

import com.acme.mytrader.domain.PriceEvent;
import com.acme.mytrader.strategy.TradingStrategy;

/**
 * A priceListener implementation which is supplied with a trading strategy
 * on an event of priceUpdate - the trading strategy is notified
 *
 * this class is just responsible for connecting event to strategy - single responsibility
 */
public class PriceListenerImpl implements PriceListener {
    TradingStrategy tradingStrategy;

    public PriceListenerImpl(TradingStrategy tradingStrategy) {
        this.tradingStrategy = tradingStrategy;
    }

    @Override
    public void priceUpdate(String security, double price) {
        System.out.println("price update" + security + " price :"+ price);
        tradingStrategy.listenTransaction(security, price);
    }
}