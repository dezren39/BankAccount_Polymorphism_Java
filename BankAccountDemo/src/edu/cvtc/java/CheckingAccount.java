package edu.cvtc.java;


public class CheckingAccount extends BankAccount {
	public CheckingAccount(double initialBalance) throws InsufficientFundsException, MaxFundsException
	{
		super(initialBalance);
	}
	
	public void getReport()
	{
		System.out.println("Account Type: Checking (0% Interest)\r\n"
				+ "(Minimum Balance: " + dollars.format(getMinBalance()) + ")\r\n"
				+ "Balance: " + dollars.format(getBalance()) + "\r\n"
				+ "Available: " + dollars.format(getBalance() - getMinBalance())) ;
	}
}
