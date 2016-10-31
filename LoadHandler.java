package exercise;

import java.util.ArrayList;
import java.util.List;

public class LoadHandler {

	private final Consumer consumer;
	private static List<PriceUpdate> priceUpdateList;

	/**
	 * Modified the constructor to start the timer thread which will send
	 * updates to the consumer. Added list to hold the producer updates
	 */	
	public LoadHandler(Consumer consumer) throws Exception{
		this.consumer = consumer;
		priceUpdateList = new ArrayList<PriceUpdate>();
		SendUpdate sendUpdate = new SendUpdate(consumer);
		sendUpdate.start();
	}
	/**
	 * Modified the receive method to only receive the producer updates.
	 * Decoupled the send part and moved it to new thread
	 */
	public void receive(PriceUpdate priceUpdate) {
		//System.out.println("priceUpdate received"+priceUpdate);
		priceUpdateList.add(priceUpdate);
	}
	
	/**
	 * Getter method for incoming list of updates
	 */
	public static List<PriceUpdate> getPriceUpdateList() {
		return priceUpdateList;
	}
	
	/**
	 * Getter method for consumer
	 */
	public Consumer getConsumer() {
		return consumer;
	}

}
