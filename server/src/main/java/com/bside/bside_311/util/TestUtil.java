package com.bside.bside_311.util;

public class TestUtil {

	public static int sum(int a, int b) {
		callPrivateMethod();
		return a + b;
	}

	private static void callPrivateMethod() {
		System.out.println("call private method");
	}

	public int sumNonStatic(int a, int b) {
		this.callPrivateNonStaticMethod();
		return a + b;
	}

	private void callPrivateNonStaticMethod() {
		System.out.println("call private non-static method");
	}
}
