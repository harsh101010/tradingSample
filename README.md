# Notes from implementor

This program implements only BUY order strategy. The TradingStrategy contains a Map collection which stores all BUY or SELL orders at specific price levels and quantity.
I only added three tests just for the BUY order price events. It is not exhaustively unit tested.
Please note that the BUY or SELL will only trigger if price breaches the level (i.e. not at 55 but when it goes below for example 54.9)
 

# Developer Programming Exercise

## User Story

As a trader I want to be able to monitor stock prices such that when they breach a trigger level orders can be executed automatically.

Note:

The implementation of PriceSource and EecutionService is out of scope, assuming that it will be provided by third party.
You need to listen to price updates from PriceSource and act accordingly.

## Exercise

Given the following interface definitions (provided)

```
public interface ExecutionService {
    void buy(String security, double price, int volume);
    void sell(String security, double price, int volume);
}
```

```
public interface PriceListener {
    void priceUpdate(String security, double price);
}
```

```
public interface PriceSource {
    void addPriceListener(PriceListener listener);
    void removePriceListener(PriceListener listener);
}
```

Develop a basic implementation of the PriceListener interface that provides the following behaviour:

1. Monitors price movements on a specified single stock (e.g. "IBM")
1. Executes a single "buy" instruction for a specified number of lots (e.g. 100) as soon as the price of that stock is seen to be below
a specified price (e.g. 55.0). Don’t worry what units that is in.

### Considerations

* Please "work out loud" and ask questions
* This is not a test of your API knowledge so feel free to check the web as reference
* There is no specific solution we are looking for

### Some libraries already available:

* Java 8
* JUnit 4
* Mockito
* EasyMock
* JMock
