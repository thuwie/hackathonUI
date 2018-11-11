package org.hackaton.coffeeservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PredictResponse {
    private double percentage;
    private String message;

    public enum STATUS {
        BEST("Coffeepot is full more than half and proceeds to filling!"),
        GOOD("Coffeepot is full more than half but going to be empty!"),
        BAD("Coffeepot is less than half but going to be fully!"),
        WORST("Coffeepot is less than half and proceeds to be empty!");

        private final String message;

        STATUS(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
