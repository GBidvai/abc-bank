package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                AccountType.CHECKING+
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                AccountType.SAVINGS +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransferFundsSuccess() throws Exception{
        Customer oscar = new Customer("Oscar");
        Account accountChecking = new Account(AccountType.CHECKING);
        accountChecking.setAccountId(1);
        accountChecking.deposit(50000);

        Account accountSavings = new Account(AccountType.SAVINGS);
        accountSavings.setAccountId(2);

        oscar.openAccount(accountChecking);
        oscar.openAccount(accountSavings);

        oscar.transferFunds(accountChecking.getAccountId(), accountSavings.getAccountId(), 5000);

        assertEquals(45000, accountChecking.getAccountBalance(), DOUBLE_DELTA);

    }

    @Test(expected=Exception.class)
    public void testTransferFundsFailure() throws Exception{
        Customer oscar = new Customer("Oscar");
        Account accountChecking = new Account(AccountType.CHECKING);
        accountChecking.setAccountId(1);
        accountChecking.deposit(100);

        Account accountSavings = new Account(AccountType.SAVINGS);
        accountSavings.setAccountId(2);

        oscar.openAccount(accountChecking);
        oscar.openAccount(accountSavings);

        oscar.transferFunds(accountChecking.getAccountId(), accountSavings.getAccountId(), 200);

    }

}
