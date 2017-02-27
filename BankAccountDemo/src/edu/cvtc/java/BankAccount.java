package edu.cvtc.java;

import java.text.NumberFormat;

public abstract class BankAccount {
	public static final double MIN_BALANCE = 0.00;
	private double balance;
	protected NumberFormat dollars = NumberFormat.getCurrencyInstance();
	
	public BankAccount(double initialBalance) throws InsufficientFundsException, MaxFundsException
	{	
		if ((BankAccount)this instanceof HealthSavingsAccount)
		{
			if (initialBalance > HealthSavingsAccount.MAX_BALANCE)
			{ 
				throw new MaxFundsException(initialBalance - HealthSavingsAccount.MAX_BALANCE);
			}			
		}
		if (initialBalance >= getMinBalance())
		{
			setBalance(initialBalance);
		}
		else
		{
			double needs = getMinBalance() - initialBalance;
			throw new InsufficientFundsException(needs);
		}
	}

	public void deposit(double amount)
	{
		setBalance(balance + amount);
	}
	
	public double getBalance()
	{
		try
		{
			return balance;
		}
		catch (Exception e)
		{
			return 0;
		}
	}
	
	public double getMinBalance()
	{
		return MIN_BALANCE;
	}
	
	public abstract void getReport() ;
	
	protected void setBalance(double balance)
	{
		try
		{
			if (balance >= getMinBalance())
			{
				this.balance = balance;
			}
			else
			{
				double needs = getMinBalance() - balance;
				throw new InsufficientFundsException(needs);
			}
		}
		catch (InsufficientFundsException e)
		{
			System.out.println("BA1 ERROR: Cannot set balance, puts balance below minimum limit. Balance Attempted: " + dollars.format(balance) 
				+ " | Balance: " + dollars.format(getBalance()) + " | Min: " + dollars.format(getMinBalance())
				+ "\r\nPlease add at least " + dollars.format(e.getAmount())
				+ " more, in order to set this balance.\r\n");
		}		
	}
	
	public void withdraw(double amount)
	{
		try
		{
			if(amount <= (getBalance() - getMinBalance()))
			{
				setBalance(getBalance() - amount);
			}
			
			else
			{
				double needs = amount - (getBalance() - getMinBalance());
				throw new InsufficientFundsException(needs);
			}	
		}
		catch (InsufficientFundsException e)
		{
			System.out.println("BA2 ERROR: Withdrawal would put balance below minimum limit. Withdrawal: " + dollars.format(amount) 
			+ " | Balance: " + dollars.format(getBalance()) + " | Min: " + dollars.format(getMinBalance()));
		}
	}
}
 