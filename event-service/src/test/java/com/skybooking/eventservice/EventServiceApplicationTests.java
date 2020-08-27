package com.skybooking.eventservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Executable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class EventServiceApplicationTests {

	@Test
	void contextLoads() {

		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i=0 ; i< 100; i++) {
			service.execute(new Task());
		}

		System.out.println("Thread Name acd: " + Thread.currentThread().getName());

	}

}

class Task implements Runnable {

	@Override
	public void run() {
		System.out.println("Thread Name: " + Thread.currentThread().getName());
	}

}