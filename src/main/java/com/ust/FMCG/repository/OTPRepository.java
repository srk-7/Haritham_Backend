package com.ust.FMCG.repository;

import com.ust.FMCG.model.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OTPRepository extends MongoRepository<OTP,String> {
}
