package org.hackaton.coffeeservice.controller;

import org.hackaton.coffeeservice.bot.VKBot;
import org.hackaton.coffeeservice.model.CoffeeShot;
import org.hackaton.coffeeservice.model.CoffeeShotMongo;
import org.hackaton.coffeeservice.repository.CoffeeRepository;
import org.hackaton.coffeeservice.service.CoffeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CoffeeController {

    private final CoffeeRepository coffeeRepository;
    private final CoffeeService coffeeService;
    private final VKBot vkbot;

    public CoffeeController(CoffeeRepository coffeeRepository, CoffeeService coffeeService, VKBot vkbot) {
        this.coffeeRepository = coffeeRepository;
        this.coffeeService = coffeeService;
        this.vkbot = vkbot;
    }

    @GetMapping("/vkbot/notify")
    public void notifyUserWhenCoffeePotIsReady(@RequestParam int vkUserId) {
        String responseMessage = "Hi! I am coffeebot, you asked to norify you when the coffeepot is ready!";
        vkbot.notifyUserOnFullCoffeePot(vkUserId, responseMessage);
    }

    @PostMapping("/coffeeshot")
    public void add(@RequestBody CoffeeShot cs) {
        coffeeRepository.insert(cs);
    }

    @PostMapping("/coffeeshots")
    public void add(@RequestBody List<CoffeeShot> cs) {
        coffeeRepository.insert(cs);
    }

    @GetMapping("/coffeeshot/{id}")
    public CoffeeShotMongo get(@PathVariable String id) {
        return coffeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find ID " + id));
    }

    @GetMapping("/coffeeshot/latest")
    public CoffeeShotMongo getLatest() {
        return coffeeRepository.getLatest();
    }

    @GetMapping("/coffeeshot")
    public List<CoffeeShotMongo> getHistory(@RequestParam int cameraId, @RequestParam int count) {
        return coffeeRepository.getHistory(cameraId, count);
    }

    @GetMapping("/coffeeshot/extendedHistory")
    public List<CoffeeShotMongo> getHistoryWithPredict(@RequestParam int cameraId, @RequestParam int countBefore,
                                                       @RequestParam int countAfter) {
        return coffeeRepository.getHistory(cameraId, countBefore, countAfter);
    }

    @GetMapping("/coffeeshot/history")
    public List<CoffeeShotMongo> getHistoryFromTimestamp(@RequestParam int cameraId, @RequestParam long from,
                                                         @RequestParam long to) {
        return coffeeRepository.getHistory(cameraId, from, to);
    }

    @GetMapping("/coffeeshot/predict")
    public int getHistoryFromTimestamp(@RequestParam(defaultValue = "0") int cameraId) {
        return coffeeService.getFakePrediction(cameraId, System.currentTimeMillis());
    }

    @DeleteMapping("/coffeeshots/deleteAll")
    public void deleteShots() {
        coffeeRepository.deleteAll();
    }

    @GetMapping("/fakeGeneration")
    public void fakeGeneration() {
        long currentTimestamp = System.currentTimeMillis();
        long twoDays = 86400000;
        long oneMinute = 60000;

        final Random random = new Random();

        double i = 1.0;
        double r;
        while (currentTimestamp < currentTimestamp + twoDays) {
            double percent = Math.sin(i / 8.0) / 2.0 + 0.5;

            r = random.nextDouble() / 17;
            percent = Math.min(percent + r, 1.0);

            CoffeeShotMongo csm = new CoffeeShotMongo(String.valueOf(currentTimestamp), percent, 0, currentTimestamp);
            coffeeRepository.insert(csm);
            i++;
            currentTimestamp += oneMinute;
        }
    }
}
