package org.hackaton.coffeeservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeShot {
    @Id
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Double> hash;
    Pot pot;

    // wtf? Why doesn't it work w/o JsonProperty?
    @JsonProperty("linePos")
    private int linePos;
}
