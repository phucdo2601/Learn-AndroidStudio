package com.phucdn.learnaddtocartwithmssqlb1.listener;

import com.phucdn.learnaddtocartwithmssqlb1.model.ItemModel;

import java.util.List;

public interface IItemLoadListener {
    void onDrinkLoadSuccess(List<ItemModel> drinkModelList);

    void onDrinkLoadFailed(String message);
}
