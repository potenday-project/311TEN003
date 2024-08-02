package com.bside.bside_311.utils;

// 추상 클래스
abstract class Shape {
	abstract double calculateArea();
}

// Circle 클래스
class Circle extends Shape {
	private final double radius;

	public Circle(double radius) {
		this.radius = radius;
	}

	@Override
	double calculateArea() {
		return Math.PI * radius * radius;
	}
}

// Rectangle 클래스
class Rectangle extends Shape {
	private final double width;
	private final double height;

	public Rectangle(double width, double height) {
		this.width = width;
		this.height = height;
	}

	@Override
	double calculateArea() {
		return width * height;
	}
}

public class Main {
	public static void main(String[] args) {
		Shape circle = new Circle(5);
		Shape rectangle = new Rectangle(4, 5);

		System.out.println("Circle area: " + circle.calculateArea());
		System.out.println("Rectangle area: " + rectangle.calculateArea());
	}
}
