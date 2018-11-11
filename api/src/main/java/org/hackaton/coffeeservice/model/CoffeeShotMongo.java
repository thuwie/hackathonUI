package org.hackaton.coffeeservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class CoffeeShotMongo extends CoffeeShot {

    private int cameraId;
    private int vol;
    private long timestamp;
    private double percent;

    public CoffeeShotMongo(String name, double percent, int cameraId, long timestamp) {
        this.setName(name);
        this.percent = percent;
        this.cameraId = cameraId;
        this.timestamp = timestamp;
    }

    public CoffeeShotMongo(CoffeeShot cs) throws ParseException {
        super(cs.getName(), cs.getHash(), cs.getPot(), cs.getLinePos());

        int coffeeHeight = (cs.getPot().getHeight() - cs.getLinePos());

        this.cameraId = Integer.valueOf(cs.getName().split("-")[1]);
        this.vol = cs.getPot().getWidth() * coffeeHeight;
        this.percent = (double) coffeeHeight / cs.getPot().getHeight();
        this.timestamp = getTimeStamp(cs.getName());
    }

    private long getTimeStamp(String name) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd.hhmmss");

        String date = name.split("-")[2];
        String time = name.split("-")[3];

        Date parsedTimeStamp = dateFormat.parse(date + "." + time);
        Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
        return timestamp.getTime();
    }
}
