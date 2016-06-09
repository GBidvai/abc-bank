package com.abc;


import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

@SuppressWarnings("ALL")
public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testAccountBalance(){
        Account account = new Account(AccountType.CHECKING);
        account.setAccountBalance(2000); // initial account balance is 2000
        account.withdraw(200);
        assertEquals(1800, account.getAccountBalance(), DOUBLE_DELTA);
        account.deposit(1000);
        assertEquals(2800, account.getAccountBalance(), DOUBLE_DELTA);
        assertEquals(2, account.getTransactions().size());
        assertEquals(2.8, account.interestEarned(), DOUBLE_DELTA);

    }

    @Test
    public void testWithDrawalInPastTenDays(){
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100);
        account.deposit(10000);
        account.withdraw(100, new Date(2016, 05, 20));
        assertTrue(account.hasWithdrawalInPastTenDays());
    }

    @Test
    public void testNoWithDrawalInPastTenDays(){
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100);
        account.deposit(10000);
        assertFalse(account.hasWithdrawalInPastTenDays());
    }
}
