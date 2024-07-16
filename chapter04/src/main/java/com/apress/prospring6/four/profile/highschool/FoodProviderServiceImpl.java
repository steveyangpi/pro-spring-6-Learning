package com.apress.prospring6.four.profile.highschool;

import com.apress.prospring6.four.profile.Food;
import com.apress.prospring6.four.profile.FoodProviderService;

import java.util.List;

public class FoodProviderServiceImpl implements FoodProviderService {

    @Override
    public List<Food> provideLunchSet() {
        return List.of(new Food("Coke"),new Food("Hamburger"),new Food("Fries"));
    }
}
