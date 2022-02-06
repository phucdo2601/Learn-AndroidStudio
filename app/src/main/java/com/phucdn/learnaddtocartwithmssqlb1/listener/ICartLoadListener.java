package com.phucdn.learnaddtocartwithmssqlb1.listener;


import com.phucdn.learnaddtocartwithmssqlb1.model.CartModel;

import java.util.List;

public interface ICartLoadListener {

    void onCartLoadSuccess(List<CartModel> cartModelList);

    void onCartLoadFailed(String message);
}
