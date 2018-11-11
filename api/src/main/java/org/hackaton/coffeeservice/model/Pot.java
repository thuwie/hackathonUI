package org.hackaton.coffeeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pot {
    private int x;
    private int y;

    private int width;
    private int height;
}
