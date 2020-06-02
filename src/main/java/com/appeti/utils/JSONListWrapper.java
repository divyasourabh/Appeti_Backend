package com.appeti.utils;

import java.util.List;

public class JSONListWrapper {
	private List list;
	public JSONListWrapper(List list){
		this.list = list;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
}
