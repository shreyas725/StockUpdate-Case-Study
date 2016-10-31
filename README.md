*************************************************** Scenario *********************************************

There is a producer (Producer) of price updates for stocks.
This producer will generate constant price updates for a fix number of stocks.
The producer should not block, every price update should be consumed as quickly as possible.
Furthermore there is a load handler (LoadHandler) which will consume the price updates of the
producer.
In the current implementation the load handler is just passing on the update to a consumer. This
should be changed.
The consumer (Consumer) will receive the price updates from the load handler.
(The current implementation will just print out all price updates for convenience sake.)
The consumer is representing a legacy system that cannot consumer more than a certain number of
price updates per second. Otherwise it would fall over.
Task
The task of this exercise is to extend the LoadHandler to limit the updates per second to the
consumer to a certain given number (MAX_PRICE_UPDATES).
In order to achieve this, it is allowed to drop price updates, since otherwise the application will run
out of memory, if the application will keep all of them.
It is important that, if a price update will be send to the consumer, it has to be the most recent price.
Example:
Updates arrive in this order from the consumer:
Apple - 97.85
Google - 160.71
Facebook - 91.66
Google - 160.73
The load balancer has received all updates and is going to send them out to the consumer like this
now:
Apple - 97.85
Google - 160.73
Facebook - 91.66
So the first Google update (160.73) has been dropped.
In order to limit the number of updates per second to the consumer, it will be necessary to write
some sort of scheduler/timer. It is acceptable to send the updates as bulk once per second.
Ideally the load should be spread out into smaller chunks during that second.
The number of stocks are considered to be bigger than the number of allowed updates per
second to the consumer.
The application is designed in such a way that it would not crash even if the number of stocks or updates
per second is bigger than MAX_PRICE_UPDATES.
