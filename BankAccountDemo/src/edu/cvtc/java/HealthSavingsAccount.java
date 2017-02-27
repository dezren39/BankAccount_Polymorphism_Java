package edu.cvtc.java;

public class HealthSavingsAccount extends BankAccount implements AccrueInterest {
	public static final double MIN_BALANCE = 0.00;
	public static final double MAX_BALANCE = 2000.00;
	
	public HealthSavingsAccount(double initialBalance) throws InsufficientFundsException, MaxFundsException
	{
		super(initialBalance);
	}
	
	public void accrueInterest(double amount)
	{
		for (int i = 0; i < amount; i++)
		{
			if (1.05 * getBalance() <= getMaxBalance()) 
			{
				setBalance(1.05 * getBalance());
			}
			else
			{
				setBalance(getMaxBalance());
			}
		}
	}
	
	@Override public void deposit(double amount)
	{
		try
		{
			if (amount + getBalance() <= getMaxBalance()) 
			{
				super.deposit(amount);
			}
			else
			{
				double over = amount + getBalance() - getMaxBalance();
				throw new MaxFundsException(over);
			}
		}
		catch (MaxFundsException e)
		{
			System.out.println("HSA1 ERROR: Deposit would put balance above maximum limit. Deposit: " + dollars.format(amount) 
				+ " | Balance: " + dollars.format(getBalance()) + " | Max: " + dollars.format(getMaxBalance()));
		}
	}
	
	public double getMaxBalance()
	{
		return MAX_BALANCE;
	}
	
	public void getReport()
	{
		System.out.println("Account Type: Health Savings (5% Interest/Cycle)\r\n"
				+ "(Minimum Balance: " + dollars.format(getMinBalance()) + ")\r\n"
				+ "(Maximum Balance: " + dollars.format(getMaxBalance()) + ")\r\n"
				+ "Balance: " + dollars.format(getBalance()) + "\r\n"
				+ "Available: " + dollars.format(getBalance() - getMinBalance())) ;
	}
}