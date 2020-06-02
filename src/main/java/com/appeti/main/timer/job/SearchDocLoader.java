package com.appeti.main.timer.job;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

import com.appeti.database.table.aggregation.BaseCtr;
import com.appeti.database.table.aggregation.Click;
import com.appeti.database.table.aggregation.SalesTrack;

public class SearchDocLoader extends TimerJob{
	
	private static String ADMIN_KEY = "com.appeti.task.base.ctr.last.run";
	
	public SearchDocLoader() {
		super("SearchDocLoader", ADMIN_KEY);
	}

	@Override
	protected void aggregate() {
		Date startDate = DateUtils.truncate(adminDate, Calendar.DATE);
		Date endDate = DateUtils.addDays(startDate, 1);
		Map<String,Long> clicksMap = Click.getClicks(startDate, endDate);
		Map<String,Long> salesMap = SalesTrack.getSales(startDate, endDate);
		
		Set<String> keySet = salesMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			if(clicksMap.get(key) == null)
				clicksMap.put(key, 0l);
		}
		
		Set<String> clickKeySet = clicksMap.keySet();
		Iterator<String> iterator1 = clickKeySet.iterator();
		while(iterator1.hasNext()){
			String key = iterator1.next();
			System.out.println("processing key: " + key);
			long clicks = clicksMap.get(key);
			long sales = salesMap.get(key) != null ? salesMap.get(key) : 0l;
			String[] ids = key.split("#");
			long productId = ids.length > 0 ? Long.valueOf(ids[0]) : -1;
			long ptitleId = ids.length > 1 ? Long.valueOf(ids[1]) : -1;
			long tagId = ids.length > 2 ? Long.valueOf(ids[2]) : -1;
			BaseCtr.upsertCtr(productId, ptitleId, tagId, clicks, sales);
		}
		
	}

	@Override
	protected void completeTask() {
		
	}
	
}
