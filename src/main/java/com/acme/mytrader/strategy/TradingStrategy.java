package com.acme.mytrader.strategy;

import com.acme.mytrader.domain.PriceActionList;
import com.acme.mytrader.domain.PriceEvent;
import com.acme.mytrader.execution.ExecutionService;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 *
 * Developer Note: Only buy strategy is implemented in current version
 * </pre>
 */
public class TradingStrategy {

    // trading strategy buy or sell triggers are stored in map with format <securityName
    //                                                                          -> price
    //                                                                                -> List of actions(BUY,100; sell,200)>
    private final Map<String,TreeMap<Double, PriceActionList>> strategyData;
    ExecutionService executionService;

    /**
     * load the security trading strategy here
     * The data contains security name as key , value is a map of pricePoint and PriceAction(BUY,SELL)
     */
    public TradingStrategy(Map<String, TreeMap<Double, PriceActionList>> strategyData, ExecutionService executionService){
        this.strategyData = strategyData;
        this.executionService = executionService;
    }

    public void addPriceAction(PriceEvent priceEvent, PriceActionList.TradeCriteria tradeCriteria) {
        strategyData.computeIfAbsent(priceEvent.getSecurity(), k -> new TreeMap<Double, PriceActionList>()).computeIfAbsent(priceEvent.getPrice(), k->new PriceActionList()).addPriceAction(tradeCriteria);
    }

    public void removePriceAction(PriceEvent priceEvent, PriceActionList.TradeCriteria tradeCriteria) {
        Optional<Map<Double, PriceActionList>> doublePriceActionListMap = Optional.ofNullable(strategyData.get(priceEvent.getSecurity()));
        if(doublePriceActionListMap.isPresent()){

            Optional<PriceActionList> priceActionList = Optional.ofNullable(doublePriceActionListMap.get().get(priceEvent.getPrice()));
            priceActionList.ifPresent(actionList -> actionList.removePriceAction(tradeCriteria));
        }
    }

    /**
     * Listen to events , in case they match strategy criteria, trigger transaction using execution service
     * @param security name of stock
     * @param price price supplied in event
     */
    public void listenTransaction(String security, double price) {
        Objects.requireNonNull(security, "price Event empty");
        TreeMap<Double, PriceActionList> securityData = strategyData.get(security);
        if(securityData == null)
            return;
        // if price is 54.9 - look for immediate higher entry in strategy data i.e. when buy value breached certain price, place buy order - Implemented
        // if price is 55.1 - look for immediate lower entry in strategy data i.e. when price is higher than certain price, place sell order - Not implemented
        Map.Entry<Double, PriceActionList> higherPriceEntry = securityData.higherEntry(price);
        if (higherPriceEntry == null)
            return ;

        PriceActionList priceActionList = higherPriceEntry.getValue();
        priceActionList.getTradeCriterias().stream().forEach(
                tradeCriteria -> {
                    switch (tradeCriteria.getAction()) {
                        case BUY:
                            executionService.buy(security, price, tradeCriteria.getQuantity());
                        case SELL:
                            // do nothing - as we have filtered criteria BUY when price lower than X
                    }
                }
        );

    }
}
