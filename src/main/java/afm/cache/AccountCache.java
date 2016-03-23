package afm.cache;

import afm.entities.Account;
import afm.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

/**
 * Created by rchen on 3/23/16.
 */
@Service
public class AccountCache {

    @Autowired
    private AccountRepository repository;

    @CachePut(cacheManager = "cacheManager", keyGenerator = "accountKeyGenerator")
    public Account getAccount(Integer id) {
        return repository.findOne(id);
    }

    @CacheEvict(cacheManager = "cacheManager", keyGenerator = "accountKeyGenerator")
    public void evictAccountCache(Integer id) {

    }

    public Account saveAccount(Account account) {
        account = repository.save(account);
        evictAccountCache(account.getId());
        return getAccount(account.getId());
    }

    public Account deleteAccount(Account account) {
        repository.delete(account);
        evictAccountCache(account.getId());
        return account;
    }

    public Account updateAccount(Account newAccount) {
        Account account = repository.save(newAccount);
        evictAccountCache(account.getId());
        return getAccount(account.getId());
    }

}
