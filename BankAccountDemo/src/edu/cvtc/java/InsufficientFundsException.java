package edu.cvtc.java;

// Created with the gracious assistance of the IBM page located at:
// https://www.ibm.com/developerworks/community/blogs/738b7897-cd38-4f24-9f05-48dd69116837/entry/declare_your_own_java_exceptions?lang=en
public class InsufficientFundsException extends Exception {
	private static final long serialVersionUID = 8671094368558358263L;
	private double amount;
	
	public InsufficientFundsException(double amount)
	{
		this.amount = amount;
	}
	
	public double getAmount()
	{
		return amount;
	}
}
