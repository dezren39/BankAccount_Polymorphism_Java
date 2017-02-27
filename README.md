# BankAccount_Polymorphism_Java

    package edu.cvtc.java;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.PrintStream;
    import java.text.NumberFormat;

    public class BankAccountDemo {

      public static void main(String[] args) throws InsufficientFundsException, MaxFundsException
      {
        //Array of 3 BankAccounts
        BankAccount accounts[] = new BankAccount[3];

        //Assign each to a specific subclass using a method.
        //Each exception is tested and the last creation 'attempt'
        //is the legitimate one for each account.
        accounts[0] = createChecking(accounts[0], 0, -450);
        accounts[0] = createChecking(accounts[0], 0, 50);

        accounts[1] = createSavings(accounts[1], 1, 50);
        accounts[1] = createSavings(accounts[1], 1, 550);

        accounts[2] = createHealthSavings(accounts[2], 2, -500);		
        accounts[2] = createHealthSavings(accounts[2], 2, 2500);
        accounts[2] = createHealthSavings(accounts[2], 2, 50);

        displayReports(accounts);

        System.out.println("50 withdrawn from each.");
        withdraw(accounts, 50);

        displayReports(accounts);

        System.out.println("50 withdrawn from each. Should error, lack of funds.");
        withdraw(accounts, 50);

        displayReports(accounts);

        System.out.println("1900 deposited to each.");
        deposit(accounts, 1900);

        displayReports(accounts);

        System.out.println("200 deposited to each. HSA account should error for exceeding Max Balance.");
        deposit(accounts, 200);

        displayReports(accounts);

        //I assumed 'amount' meant the amount of cycles to accrueInterest. 
        //This runs 10 times (== 10yrs), against each account with the interface.
        System.out.println("10 cycles of interest pass.\r\n"
            + "HSA Account will stop gaining interest at Maximum Balance.");
        accrue(accounts, 10);

        displayReports(accounts);

        System.out.println("The program will now attempt to save account information to the file 'accounts.txt'");

        PrintStream console = System.out;
        try
        {
          File file = new File("accounts.txt");
          FileOutputStream fos = new FileOutputStream(file);
          PrintStream ps = new PrintStream(fos);
          System.setOut(ps);
          displayReports(accounts);
          ps.close();
          fos.close();
        }
        catch (FileNotFoundException e)
        {
          System.setOut(console);
          System.out.println("Sorry, the file could not be found/created.");
          System.out.println(e);
        }
        catch (IOException e)
        {
          System.setOut(console);
          System.out.println("Sorry, there was an IO exception.");
          System.out.println(e);
        }
      }
      public static void accrue(BankAccount[] accounts, double amount)
      {
        for (int i = 0; i < accounts.length; i++) 
        {
          if(accounts[i] instanceof AccrueInterest) 
          {
             ((AccrueInterest)accounts[i]).accrueInterest(amount);
          }
        }
      }
      private static BankAccount createChecking(BankAccount original, int actNum, double initialBalance)
      {
        NumberFormat dollars = NumberFormat.getCurrencyInstance();

        try
        {
          return new CheckingAccount(initialBalance);
        }
        catch (InsufficientFundsException e)
        {
          System.out.println("BAD1 ERROR: Initial deposit puts balance below minimum limit. Deposit: " + dollars.format(initialBalance) 
            + " | Min: " + dollars.format(CheckingAccount.MIN_BALANCE)
            + "\r\nPlease add at least " + dollars.format(e.getAmount())
            + " more, in order to open this kind of account.\r\n");
          return original;
        }
        catch (MaxFundsException e)
        {
          System.out.println("BAD2 ERROR: Initial deposit puts balance above maximum limit. Deposit: " + dollars.format(initialBalance) + " | Deposit: " + dollars.format(initialBalance) 
          + "\r\nPlease use at least " + dollars.format(e.getAmount()) + " less, in order to open this kind of account.\r\n");
          return original;
        }
      }

      private static BankAccount createHealthSavings (BankAccount original, int actNum, double initialBalance)
      {
        NumberFormat dollars = NumberFormat.getCurrencyInstance();

        try
        {
          return new HealthSavingsAccount(initialBalance);
        }
        catch (InsufficientFundsException e)
        {
          System.out.println("BAD5 ERROR: Initial deposit puts balance below minimum limit. Deposit: " + dollars.format(initialBalance) 
            + " | Min: " + dollars.format(HealthSavingsAccount.MIN_BALANCE)
            + "\r\nPlease add at least " + dollars.format(e.getAmount())
            + " more, in order to open this kind of account.\r\n");
          return original;
        }
        catch (MaxFundsException e)
        {
          System.out.println("BAD6 ERROR: Initial deposit puts balance above maximum limit. Deposit: " + dollars.format(initialBalance) 
            + " | Max: " + dollars.format(HealthSavingsAccount.MAX_BALANCE)
            + "\r\nPlease use at least " + dollars.format(e.getAmount())
            + " less, in order to open this kind of account.\r\n");
          return original;
        }
      }

      private static BankAccount createSavings (BankAccount original, int actNum, double initialBalance)
      {
        NumberFormat dollars = NumberFormat.getCurrencyInstance();

        try
        {
          return new SavingsAccount(initialBalance);
        }
        catch (InsufficientFundsException e)
        {
          System.out.println("BAD3 ERROR: Initial deposit puts balance below minimum limit. Deposit: " + dollars.format(initialBalance) 
            + " | Min: " + dollars.format(SavingsAccount.MIN_BALANCE)
            + "\r\nPlease add at least " + dollars.format(e.getAmount())
            + " more, in order to open this kind of account.\r\n");
          return original;
        }
        catch (MaxFundsException e)
        {
          System.out.println("BAD4 ERROR: Initial deposit puts balance above maximum limit. Deposit: " + dollars.format(initialBalance) + " | Deposit: " + dollars.format(initialBalance) 
            + "\r\nPlease use at least " + dollars.format(e.getAmount()) + " less, in order to open this kind of account.\r\n");
          return original;
        }
      }


      public static void deposit(BankAccount[] accounts, double amount)
      {
        for (int i = 0; i < accounts.length; i++)
        {
          if (accounts[i] instanceof BankAccount)
          {
            ((BankAccount)accounts[i]).deposit(amount);
          }
        }
      }

      private static void displayReports(BankAccount[] accounts)
      {
        System.out.println("\r\n--- ---\r\nAccounts: \r\n");
        for (int i = 0; i < accounts.length; i++)
        {
          if (accounts[i] instanceof BankAccount)
          {
            System.out.println(i + ": ");
            accounts[i].getReport();
          }
        }
        System.out.println("--- ---\r\n");
      }

      public static void withdraw(BankAccount[] accounts, double amount)
      {
        for (int i = 0; i < accounts.length; i++)
        {
          if (accounts[i] instanceof BankAccount)
          {
            ((BankAccount)accounts[i]).withdraw(amount);
          }
        }
      }


    }
