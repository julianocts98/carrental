package com.juliano.carrental.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rental {
    int id;
    Car car;
    Customer customer;
    Date startDate;
    Date endDate;
    int total;
    Date createdAt;
    Date updatedAt;
}
