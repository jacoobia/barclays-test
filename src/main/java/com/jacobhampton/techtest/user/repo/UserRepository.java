package com.jacobhampton.techtest.user.repo;

import com.jacobhampton.techtest.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
