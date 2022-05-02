package com.juliano.carrental.model;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Car {
    int id;
    String name;
    String description;
    int dailyRate;
    boolean available;
    String licensePlate;
    Brand brand;
    Category category;
    String color;
    ArrayList<Specification> specifications;
    Date createdAt;
    byte[] image;
}
