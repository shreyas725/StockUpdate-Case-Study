package exercise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * New class created to manage the scheduler for batch updates to be sent to the
 * consumer
 */
public class SendUpdate extends Thread {
	private static final int MAX_PRICE_UPDATES = 100;
	private static final int TIMER = 1000;
	private final Consumer consumer;
	private static boolean UPDATES_COMPLETED = false;

	SendUpdate(Consumer consumer) {
		this.consumer = consumer;
	}

	@Override
	public void run() {
		try {
			sendUpdates();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method performs the throttling of sending max price updates to
	 * consumer. Also, it gets latest price from comparator and drops extra
	 * updates received from producer. It sends updates every 1 sec
	 */
	private void sendUpdates() throws Exception {
		List<PriceUpdate> priceUpdateSendList = new ArrayList<PriceUpdate>(100);
		while (!UPDATES_COMPLETED || !LoadHandler.getPriceUpdateList().isEmpty()) {
			priceUpdateSendList.addAll(LoadHandler.getPriceUpdateList());
			LoadHandler.getPriceUpdateList().clear();
			List<PriceUpdate> sortedList = getLatestUpdates(priceUpdateSendList);
			if (sortedList.size() > MAX_PRICE_UPDATES)
				sortedList.subList(0, MAX_PRICE_UPDATES - 1);
			if (!sortedList.isEmpty()) {
				System.out.println("Batch updates sending starts....");
				consumer.send(sortedList);
				System.out.println("Batch updates sending completed");
			}
			priceUpdateSendList.clear();
			sortedList.clear();
			Thread.sleep(TIMER);
		}
	}

	/**
	 * This is method removes the duplicate older updates for each stock and
	 * keeps the latest of each stock
	 */
	private List<PriceUpdate> getLatestUpdates(List<PriceUpdate> priceUpdateSendList) {

		List<PriceUpdate> sortedList = new ArrayList<PriceUpdate>();
		List<String> stockNameList = new ArrayList<String>();

		for (PriceUpdate pUpdate : priceUpdateSendList) {
			stockNameList.add(pUpdate.getCompanyName());

		}
		Set<String> uniqePUSet = new HashSet<String>(stockNameList);

		PriceUpdate puObj = null;

		int i = 0;
		int j;
		for (String sName : uniqePUSet) {
			puObj = null;
			j = i++;
			Iterator<PriceUpdate> itr = priceUpdateSendList.iterator();
			while (itr.hasNext()) {
				PriceUpdate pu = (PriceUpdate) itr.next();
				if (pu.getCompanyName().equals(sName)) {
					if (puObj == null) {
						sortedList.add(j, pu);
						puObj = pu;

					} else {
						if (pu.equals(puObj)) {
							sortedList.remove(j);
							sortedList.add(j, pu);
							puObj = pu;

						}
					}

				}
			}
		}
		return sortedList;
	}

	/**
	 * @param UPDATES_COMPLETED
	 *            the UPDATES_COMPLETED to set
	 */
	public static void setUPDATES_COMPLETED(boolean updatesCompleted) {
		UPDATES_COMPLETED = updatesCompleted;
	}
}
