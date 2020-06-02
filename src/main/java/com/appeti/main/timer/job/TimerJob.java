package com.appeti.main.timer.job;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appeti.database.table.utils.AdminDate;

public abstract class TimerJob extends TimerTask{
	 
		private boolean scheduled = false;
		private String name = "";
		private String adminKey;
		protected Date adminDate;
		protected static final Logger logger = LoggerFactory.getLogger(TimerJob.class);
		
		public TimerJob(String name, String adminKey){
			this.name = name;
			this.adminKey = adminKey;
		}
		
	    @Override
	    public void run() {
	    	logger.info("Starting job: " + name);
	    	if(validateAdminDate()){
	    		logger.info("Running for " + adminDate);
		    	aggregate();
	    		completeTask();
	    		updateAdminDate();
	    	}
	    }
	    
	    protected abstract void aggregate();
	    
	    protected abstract void completeTask();
	     
	    public static void schedule(TimerJob job, long intervalInMinutes){
	    	if(job.isScheduled())
	    		return;
	    	Timer timer = new Timer(true);
	    	timer.scheduleAtFixedRate(job, 0, intervalInMinutes*60*1000);
	        job.setScheduled(true);
	    }

		public boolean isScheduled() {
			return scheduled;
		}

		public void setScheduled(boolean scheduled) {
			this.scheduled = scheduled;
		}
		
		private void updateAdminDate(){
			AdminDate.set(adminKey, DateUtils.addDays(adminDate, 1));
		}
		
		private boolean validateAdminDate(){
			adminDate = AdminDate.get(adminKey);
			if(DateUtils.addDays(adminDate, 1).getTime() < new Date().getTime()) // last run was more than one day ago
				return true;
			else
				return false;
		}
}
