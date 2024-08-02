package com.bside.bside_311.utils;

// 추상 클래스
abstract class Animal {
	abstract void makeSound();
}

// Dog 클래스
class Dog extends Animal {
	@Override
	void makeSound() {
		System.out.println("Woof!");
	}
}

// Cat 클래스
class Cat extends Animal {
	@Override
	void makeSound() {
		System.out.println("Meow!");
	}
}

public class Main3 {
	public static void main(String[] args) {
		Animal dog = new Dog();
		Animal cat = new Cat();

		dog.makeSound();
		cat.makeSound();
	}
}
