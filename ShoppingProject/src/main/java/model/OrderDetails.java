package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDetails {
	
	int id;
	
	int userId;
	
	double total;
	
	Date orderDateTime;
	
	List<ItemDetails> items;

	public OrderDetails(int id, int userId, double total, Date orderDateTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.total = total;
		this.orderDateTime = orderDateTime;
		this.items = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public double getTotal() {
		return total;
	}

	public Date getOrderDateTime() {
		return orderDateTime;
	}

	@Override
	public int hashCode() {
		return id;
	}
	
	

	public List<ItemDetails> getItems() {
		return items;
	}

	public void addItem(ItemDetails item) {
		this.items.add(item);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetails other = (OrderDetails) obj;
		if (id != other.getId())
			return false;
		return true;
	}

	
}
