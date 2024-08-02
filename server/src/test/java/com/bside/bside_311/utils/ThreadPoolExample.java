package com.bside.bside_311.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolExample {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		List<Future<Integer>> resultList = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			int index = i;
			Callable<Integer> task = () -> {
				Thread.sleep(1000);
				return index * index;
			};
			Future<Integer> result = executor.submit(task);
			resultList.add(result);
		}

		for (Future<Integer> future : resultList) {
			try {
				System.out.println("Result: " + future.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		executor.shutdown();
	}
}
