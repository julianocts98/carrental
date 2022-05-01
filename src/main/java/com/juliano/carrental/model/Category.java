package com.juliano.carrental.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {
    int id;
    String name;
    String description;
    Date createdAt;
}
