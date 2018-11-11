package org.hackaton.coffeeservice.repository;

import org.hackaton.coffeeservice.model.CoffeeShot;
import org.hackaton.coffeeservice.model.CoffeeShotMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.List;

public class CoffeeRepositoryCustomImpl implements CoffeeRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CoffeeRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void insert(CoffeeShot cs) {
        try {
            CoffeeShotMongo csm = new CoffeeShotMongo(cs);
            this.mongoTemplate.insert(csm);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(List<CoffeeShot> css) {
        css.forEach(cs -> {
            try {
                this.mongoTemplate.insert(new CoffeeShotMongo(cs));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<CoffeeShotMongo> getHistory(int cameraId, int count) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "timestamp"));
        query.addCriteria(Criteria.where("timestamp").lte(System.currentTimeMillis()));
        query.limit(count);
        query.addCriteria(Criteria.where("cameraId").is(cameraId));
        return mongoTemplate.find(query, CoffeeShotMongo.class);
    }

    @Override
    public List<CoffeeShotMongo> getHistory(int cameraId, int countBefore, int countAfter) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "timestamp"));
        long timeStamp = System.currentTimeMillis();
        query.addCriteria(Criteria.where("timestamp").lte(timeStamp));
        query.limit(countBefore);
        List<CoffeeShotMongo> before = mongoTemplate.find(query, CoffeeShotMongo.class);

        query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        query.addCriteria(Criteria.where("timestamp").gte(timeStamp));
        query.limit(countAfter);
        List<CoffeeShotMongo> after = mongoTemplate.find(query, CoffeeShotMongo.class);

        before.addAll(after);
        return before;
    }

    @Override
    public List<CoffeeShotMongo> getHistory(int cameraId, long from, long to) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "timestamp"));
        query.addCriteria(Criteria.where("timestamp").gte(from).lte(to));
        query.addCriteria(Criteria.where("cameraId").is(cameraId));
        return mongoTemplate.find(query, CoffeeShotMongo.class);
    }

    @Override
    public CoffeeShotMongo getLatest() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "timestamp"));
        query.limit(1);
        return mongoTemplate.find(query, CoffeeShotMongo.class).get(0);
    }
}
