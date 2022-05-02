package com.juliano.carrental.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Brand {
    int id;
    String name;
    Date createdAt;
}
