package com.tnf.product_service.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.tnf.product_service.Entities.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
