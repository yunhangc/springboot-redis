package afm.repositories;

import org.springframework.data.repository.CrudRepository;
import afm.entities.Account;
import org.springframework.stereotype.Repository;

/**
 * Created by rchen on 3/22/16.
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
}
