package org.xsnake.web.log;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LoggerListener implements ApplicationListener<LoggerEvent>{

	public void onApplicationEvent(LoggerEvent event) {
		System.out.println("==============="+event);
	}

}
