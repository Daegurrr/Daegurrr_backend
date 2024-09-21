package com.daegurrr.daefree.repository;

import com.daegurrr.daefree.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
