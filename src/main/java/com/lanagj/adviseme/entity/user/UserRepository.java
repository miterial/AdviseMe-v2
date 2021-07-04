package com.lanagj.adviseme.entity.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByRole(Role role);

    User findByLogin(String username);
}
