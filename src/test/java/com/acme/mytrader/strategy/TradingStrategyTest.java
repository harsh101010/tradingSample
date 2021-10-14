package com.acme.mytrader.strategy;

import org.junit.Test;

public class TradingStrategyTest {
    @Test
    public void testNothing() {
        TradingStrategy tradingStrategy = new TradingStrategy(null,null);
        tradingStrategy.listenTransaction("IBM", 55.0d);
    }

}
