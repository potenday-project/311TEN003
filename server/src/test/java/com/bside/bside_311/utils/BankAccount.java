package com.bside.bside_311.utils;

public class BankAccount {
	private int balance = 1000;

	public static void main(String[] args) {
		BankAccount account = new BankAccount();

		Thread t1 = new Thread(() -> {
			account.deposit(500);
			System.out.println("Deposited 500, balance: " + account.getBalance());
		});

		Thread t2 = new Thread(() -> {
			account.withdraw(300);
			System.out.println("Withdrew 300, balance: " + account.getBalance());
		});

		t1.start();
		t2.start();
	}

	public synchronized void deposit(int amount) {
		balance += amount;
	}

	public synchronized void withdraw(int amount) {
		if (balance >= amount) {
			balance -= amount;
		} else {
			System.out.println("Insufficient funds");
		}
	}

	public synchronized int getBalance() {
		return balance;
	}
}
