package com.juliano.carrental.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {
    int id;
    String name;
    Date birthDate;
    String email;
    String driverLicense;
    String address;
    String phoneNumber;
    Date createdAt;
    Date updatedAt;
}
