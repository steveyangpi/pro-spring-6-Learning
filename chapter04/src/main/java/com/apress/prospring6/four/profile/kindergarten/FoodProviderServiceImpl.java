package com.apress.prospring6.four.profile.kindergarten;

import com.apress.prospring6.four.profile.Food;
import com.apress.prospring6.four.profile.FoodProviderService;

import java.util.List;

public class FoodProviderServiceImpl implements FoodProviderService {

    @Override
    public List<Food> provideLunchSet() {
        return List.of(new Food("Milk"),new Food("Biscuits"));
    }
}
