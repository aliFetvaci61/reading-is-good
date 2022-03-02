package com.alifetvaci.ReadingIsGood.models;

public class TotalCountOfPurchasedBook {
	
	private String month;
	
	private int totalOrderCount;
	
	private int totalBookCount;
	
	private double totalPuchasedAmount;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(int totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	public int getTotalBookCount() {
		return totalBookCount;
	}

	public void setTotalBookCount(int totalBookCount) {
		this.totalBookCount = totalBookCount;
	}

	public double getTotalPuchasedAmount() {
		return totalPuchasedAmount;
	}

	public void setTotalPuchasedAmount(double totalPuchasedAmount) {
		this.totalPuchasedAmount = totalPuchasedAmount;
	}
	
	

}
