package com.appeti.database.table.order.wrap;

import java.util.ArrayList;
import java.util.List;

import com.appeti.database.table.order.Tax;

public class TaxWrap {
	private String name;
	private double percent;
	private double amount;
	
	public static TaxWrap getWrap(Tax tax, double amount){
		if(tax == null)
			return null;
		TaxWrap wrap = new TaxWrap();
		wrap.setName(tax.getName());
		wrap.setPercent(tax.getPercent());
		wrap.setAmount(amount * tax.getPercent()/100);
		return wrap;
	}
	
	public static List<TaxWrap> getWrapList(double amount){
		List<TaxWrap> list = new ArrayList<TaxWrap>();
		List<Tax> taxes = Tax.getAllTaxes();
		if(taxes != null){
			for(Tax tax : taxes){
				TaxWrap wrap = getWrap(tax, amount);
				if(wrap != null)
					list.add(wrap);
			}
		}
		return list;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
