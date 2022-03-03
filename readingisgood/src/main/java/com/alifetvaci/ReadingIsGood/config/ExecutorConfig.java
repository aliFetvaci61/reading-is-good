package com.alifetvaci.ReadingIsGood.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig {
	
	@Value("${alifetvaci.app.logExecuter.corePoolSize}")
	private String corePoolSize;
	
	@Value("${alifetvaci.app.logExecuter.maxPoolSize}")
	private String maxPoolSize;
	
	@Value("${alifetvaci.app.logExecuter.queueCapacity}")
	private String queueCapacity;

	@Bean(name = "logExecutor")
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Integer.parseInt(corePoolSize));
		executor.setMaxPoolSize(Integer.parseInt(maxPoolSize));
		executor.setQueueCapacity(Integer.parseInt(queueCapacity));
		executor.setThreadNamePrefix("LogThread-");
		executor.initialize();
		return executor;
	}

}
