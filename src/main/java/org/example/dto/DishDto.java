package org.example.dto;

import lombok.Data;
import org.example.pojo.Dish;
import org.example.pojo.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
