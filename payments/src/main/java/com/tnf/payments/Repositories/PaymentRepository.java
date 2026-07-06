package com.tnf.payments.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tnf.payments.Entities.Payments;

public interface PaymentRepository extends MongoRepository<Payments, String> {

}
