package com.acme.mytrader.strategy;

import com.acme.mytrader.domain.PriceActionList;
import com.acme.mytrader.domain.PriceEvent;
import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceListenerImpl;
import com.acme.mytrader.price.PriceSource;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.listeners.InvocationListener;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test two scenarios
 *  1. when no strategy data is defined ( meaning buy IBM less than 55 ) - nothing happens
 *  2. when strategy data is defined ( buy IBM less than 55) - then executiong service is called with buy order*
 *
 */
public class TradingStrategyTest {

    // Dumb implementation for priceSource Interface
    InvocationListener priceSourceInvokationListener = methodInvocationReport -> {
        if(methodInvocationReport.threwException()){
            methodInvocationReport.getThrowable().printStackTrace();
        }else{
            if(methodInvocationReport.getInvocation().toString().contains("addPriceListener")) {
                // it is assumed that priceListener is listening to priceSource object in real implementation
                System.out.println("price listener is listening to the priceSource");
            }else if(methodInvocationReport.getInvocation().toString().contains("removePriceListener")) {
                System.out.println("price listener is no longer listening to the priceSource");
            }
        }
    };

    @Test
    public void testNothing() {
        TradingStrategy tradingStrategy = new TradingStrategy(null,null);
        tradingStrategy.listenTransaction("IBM", 55.0d);
    }

    @Test
    public void shouldDoNothingWhenStrategyCriteriaIsEmpty(){

        ExecutionService executionService = Mockito.mock(ExecutionService.class);
        // given the trading strategy is empty
        TradingStrategy strategy = TradingStrategyFactory.createEmptyStrategy(executionService);
        TradingStrategy tradingStrategySpy = spy(strategy);
        PriceListener priceListener = new PriceListenerImpl(tradingStrategySpy);
        // given priceListener is listening to priceSource
        PriceSource priceSourceMock = Mockito.mock(PriceSource.class, Mockito.withSettings().invocationListeners(priceSourceInvokationListener));
        priceSourceMock.addPriceListener(priceListener);

        // when a price event is received and strategy criteria is empty
        priceListener.priceUpdate("IBM", 55);

        // then no trade is executed
        verify(tradingStrategySpy).listenTransaction("IBM", 55);
        verify(executionService, never()).buy(anyString(), anyDouble(), anyInt());
    }

    @Test
    public void shouldBuyWhenBuyCriteriaEventIsTriggered(){
        ExecutionService executionService = Mockito.mock(ExecutionService.class);
        // given the trading strategy is not empty
        TradingStrategy strategy = TradingStrategyFactory.createEmptyStrategy(executionService);
        strategy.addPriceAction(new PriceEvent("IBM", 55.0d), new PriceActionList.TradeCriteria(100, PriceActionList.Action.BUY));
        strategy.addPriceAction(new PriceEvent("IBM", 56.0d), new PriceActionList.TradeCriteria(50, PriceActionList.Action.BUY));
        strategy.addPriceAction(new PriceEvent("IBM", 54.0d), new PriceActionList.TradeCriteria(20, PriceActionList.Action.BUY));
        TradingStrategy tradingStrategySpy = spy(strategy);

        PriceListener priceListener = new PriceListenerImpl(tradingStrategySpy);
        // given priceListener is listening to priceSource
        PriceSource priceSourceMock = Mockito.mock(PriceSource.class, Mockito.withSettings().invocationListeners(priceSourceInvokationListener));
        priceSourceMock.addPriceListener(priceListener);

        // when a price event is received
        priceListener.priceUpdate("IBM", 54.9d);

        // then no trade is executed
        verify(tradingStrategySpy, only()).listenTransaction("IBM", 55);
        verify(executionService, only()).buy("IBM", 55.0d,100  );
    }

}
