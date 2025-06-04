package com.ust.FMCG.repository;

import com.ust.FMCG.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByMobile(String mobile);

    Optional<Object> findByMobile(String mobile);

}
