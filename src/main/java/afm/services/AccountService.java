package afm.services;

import afm.cache.AccountCache;
import afm.entities.Account;
import afm.exceptions.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rchen on 3/22/16.
 */
@Service
public class AccountService {

    @Autowired
    AccountCache cache;

    public Account getAccount(Integer id) {
        Account account = cache.getAccount(id);
        if (account == null) {
            throw new DataException("No Account With Id" + id + " Found");
        }
        return account;
    }

    public Account addAccount(Account account) {
        return cache.saveAccount(account);
    }

    public Account deleteAccount(Account account) {
        return cache.deleteAccount(account);
    }

    public Account updateAccount(Account newAccount) {
        Account account = cache.updateAccount(newAccount);
        if (account == null) {
            throw new DataException("Account Update Failed. Account Id is " + newAccount.getId());
        }
        return account;
    }

}
