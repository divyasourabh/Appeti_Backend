package com.appeti.main.timer.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class TaskScheduler {
	
	@Scheduled(fixedDelay = 60*60*1000)
	public void scheduleTasks(){
		System.out.println("Scheduling BaseCtr");
		//TimerJob.schedule(new BaseCtrTask(), 60);
	}
}
