package model;

import java.util.Date;

public class OrderDetails {
	
	int id;
	
	int userId;
	
	double total;
	
	Date orderDateTime;

	public OrderDetails(int id, int userId, double total, Date orderDateTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.total = total;
		this.orderDateTime = orderDateTime;
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
