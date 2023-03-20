package model;

public class ItemDetails {
	public int quantity;
	public String itemName;
	public double itemPrice;

	public ItemDetails(int quantity, String itemName, double itemPrice) {
		super();
		this.quantity = quantity;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getItemName() {
		return itemName;
	}

	public double getItemPrice() {
		return itemPrice;
	}
	
}