package org.hackaton.coffeeservice.repository;

import org.hackaton.coffeeservice.model.CoffeeShotMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends MongoRepository<CoffeeShotMongo, String>, CoffeeRepositoryCustom {

}
