package com.bside.bside_311.utils;

// 인터페이스
interface Movable {
	void move();
}

// Car 클래스
class Car implements Movable {
	@Override
	public void move() {
		System.out.println("The car is driving.");
	}
}

// Bicycle 클래스
class Bicycle implements Movable {
	@Override
	public void move() {
		System.out.println("The bicycle is pedaling.");
	}
}

public class Main2 {
	public static void main(String[] args) {
		Movable car = new Car();
		Movable bicycle = new Bicycle();

		car.move();
		bicycle.move();
	}
}
