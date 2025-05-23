package com.overengineered.aggregator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The PlanetType enum represents different types of planets.
 * This is used to specify which planet to generate greetings for.
 */
public enum PlanetType {
    EARTH,
    MARS,
    VENUS,
    JUPITER,
    SATURN,
    NEPTUNE,
    URANUS,
    MERCURY,
    PLUTO
}
