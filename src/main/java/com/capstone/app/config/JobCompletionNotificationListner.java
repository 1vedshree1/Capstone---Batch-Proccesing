package com.capstone.app.config;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class JobCompletionNotificationListner implements JobExecutionListener {

	public JobCompletionNotificationListner() {
		super();
	}
	
	

}
