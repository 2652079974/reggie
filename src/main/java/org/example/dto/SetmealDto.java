package org.example.dto;
import lombok.Data;
import org.example.pojo.Setmeal;
import org.example.pojo.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
