package com.intractiv.userapi.repository;

import com.intractiv.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
   Boolean existsByName(String name);
   User findByName(String name);
}
