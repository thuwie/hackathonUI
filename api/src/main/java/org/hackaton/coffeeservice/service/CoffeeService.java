package org.hackaton.coffeeservice.service;

import org.hackaton.coffeeservice.controller.PredictResponse;
import org.hackaton.coffeeservice.model.CoffeeShotMongo;
import org.hackaton.coffeeservice.prediction.LinearPredictor;
import org.hackaton.coffeeservice.repository.CoffeeRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public int getFakePrediction(int cameraId, long timestamp) {
        long thirtyMinutes = 1000 * 60 * 30;
        List<CoffeeShotMongo> shots = coffeeRepository.getHistory(cameraId, timestamp, timestamp + thirtyMinutes);
        int maxInd = 0;
        double maxPercent = 0;
        for (int i = 0; i < shots.size(); i++) {
            if (shots.get(i).getPercent() > maxPercent) {
                maxPercent = shots.get(i).getPercent();
                maxInd = i;
            }
        }
        // if the best predict between 0 and 11 - go now (in 5 minutes)
        if (maxInd < 11) {
            return 0;
        }
        // 11 - 21 - go in 15 minutes
        if (maxInd < 22) {
            return 1;
        }
        // > 21 - go in 30 minutes
        return 2;
    }

    public PredictResponse getHistoryFromTimestamp(int cameraId, long timestamp) {
        // last 2 weeks
        List<CoffeeShotMongo> shots = coffeeRepository.getHistory(cameraId, timestamp - 1814400000, timestamp);

        CoffeeShotMongo last = shots.get(shots.size() - 1);
        List<Double> x = shots.stream().map(shot -> Double.valueOf(shot.getTimestamp())).collect(toList());
        List<Double> y = shots.stream().map(shot -> Double.valueOf(shot.getVol())).collect(toList());

        double result = LinearPredictor.predict(x, y);

        if (last.getPercent() > 0.5 && result > 0) {
            return new PredictResponse(last.getPercent(), PredictResponse.STATUS.BEST.getMessage());
        }
        if (last.getPercent() > 0.5 && result < 0) {
            return new PredictResponse(last.getPercent(), PredictResponse.STATUS.GOOD.getMessage());
        }

        if (last.getPercent() < 0.5 && result > 0) {
            return new PredictResponse(last.getPercent(), PredictResponse.STATUS.BAD.getMessage());
        }
        return new PredictResponse(last.getPercent(), PredictResponse.STATUS.WORST.getMessage());
    }

    public void notifyUser(int cameraId, Function<String, String> callback) {
        scheduler.scheduleAtFixedRate(() -> {
            CoffeeShotMongo last = coffeeRepository.getHistory(cameraId, 1).get(0);
            System.out.println("Checking coffeepot: " + last.getPercent());
            if (last.getPercent() > 0.8) {
                String message = "Volume of coffeepot #"
                        + cameraId + " is more than 80%! Best time to have a rest and make a cup of coffee!";
                // how to terminate repetitions?
                callback.apply(message);
                throw new RuntimeException();
            }
        }, 0, 8, TimeUnit.SECONDS);
    }
}
