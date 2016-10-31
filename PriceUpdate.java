package exercise;

import java.util.concurrent.atomic.AtomicInteger;

public class PriceUpdate {

	private final String companyName;
	private final double price;
	static AtomicInteger nextId = new AtomicInteger();
	private int id;

	public PriceUpdate(String companyName, double price) {
		this.companyName = companyName;
		this.price = price;

	}

	public String getCompanyName() {
		return this.companyName;
	}

	public double getPrice() {
		return this.price;
	}

	@Override
	public boolean equals(Object o) {

		if (this.hashCode() > o.hashCode()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int hashCode() {

		id = nextId.incrementAndGet();

		return id;
	}

	@Override
	public String toString() {
		return companyName + " - " + price;
	}
}