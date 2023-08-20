package com.smartcode.notificator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableAsync
@EnableKafka
@EnableScheduling
public class NotificatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificatorApplication.class, args);


	}

	@Bean
	public TaskExecutor notificationSenderExecutors() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(100);
		taskExecutor.setMaxPoolSize(10000);
		taskExecutor.setQueueCapacity(10000);
		return taskExecutor;
	}

	@Bean
	@Primary
	public TaskExecutor scheduleExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(1);
		taskExecutor.setMaxPoolSize(1);
		taskExecutor.setQueueCapacity(1);
		return taskExecutor;
	}

}
