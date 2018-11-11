package org.hackaton.coffeeservice.repository;

import org.hackaton.coffeeservice.model.CoffeeShot;
import org.hackaton.coffeeservice.model.CoffeeShotMongo;

import java.util.List;

public interface CoffeeRepositoryCustom {

    void insert(CoffeeShot cs);

    void insert(List<CoffeeShot> css);

    List<CoffeeShotMongo> getHistory(int cameraId, int count);
    List<CoffeeShotMongo> getHistory(int cameraId, int countBefore, int countAfter);

    List<CoffeeShotMongo> getHistory(int cameraId, long from, long to);

    CoffeeShotMongo getLatest();
}
