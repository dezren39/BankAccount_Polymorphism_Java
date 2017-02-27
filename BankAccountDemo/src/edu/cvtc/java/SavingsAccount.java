package edu.cvtc.java;

public class SavingsAccount extends BankAccount implements AccrueInterest {
	public static final double MIN_BALANCE = 500.00;
	
	public SavingsAccount(double initialBalance) throws InsufficientFundsException, MaxFundsException
	{
		super(initialBalance);
	}
	
	public void accrueInterest(double amount)
	{
		for (int i = 0; i < amount; i++)
		{
			setBalance(1.09 * getBalance());
		}
	}
	
	@Override public double getMinBalance()
	{
		return MIN_BALANCE;
	}
	
	public void getReport()
	{
		System.out.println("Account Type: Savings (9% Interest/Cycle)\r\n"
				+ "(Minimum Balance: " + dollars.format(getMinBalance()) + ")\r\n"
				+ "Balance: " + dollars.format(getBalance()) + "\r\n"
				+ "Available: " + dollars.format(getBalance() - getMinBalance())) ;
	}
}