package com.phucdn.learnaddtocartwithmssqlb1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.nex3z.notificationbadge.NotificationBadge;
import com.phucdn.learnaddtocartwithmssqlb1.adapter.ItemAdapter;
import com.phucdn.learnaddtocartwithmssqlb1.dao.CartDao;
import com.phucdn.learnaddtocartwithmssqlb1.dao.ItemDao;
import com.phucdn.learnaddtocartwithmssqlb1.eventBus.MyUpdateCartEvent;
import com.phucdn.learnaddtocartwithmssqlb1.listener.ICartLoadListener;
import com.phucdn.learnaddtocartwithmssqlb1.listener.IItemLoadListener;
import com.phucdn.learnaddtocartwithmssqlb1.model.CartModel;
import com.phucdn.learnaddtocartwithmssqlb1.model.ItemModel;
import com.phucdn.learnaddtocartwithmssqlb1.utils.SpaceItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IItemLoadListener, ICartLoadListener {

    private RecyclerView recyclerItem;
    private RelativeLayout mainLayout;
    private NotificationBadge badge;
    private FrameLayout btnCart;

    private IItemLoadListener itemLoadListener;
    private ICartLoadListener cartLoadListener;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if (EventBus.getDefault().hasSubscriberForEvent(MyUpdateCartEvent.class)) {
            EventBus.getDefault().removeStickyEvent(MyUpdateCartEvent.class);

        }
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUpdateCart(MyUpdateCartEvent event) {
        countCartItem();
    }

    private ItemDao itemDao = new ItemDao();
    private ItemAdapter itemAdapter;
    private CartDao cartDao = new CartDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        countCartItem();
    }

    private void initUi(){
        recyclerItem = findViewById(R.id.recycler_drink);
        mainLayout = findViewById(R.id.mainLayout);
        badge = findViewById(R.id.badge);
        btnCart = findViewById(R.id.btnCart);

        ButterKnife.bind(this);
        itemLoadListener =this;
        cartLoadListener = this;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerItem.setLayoutManager(gridLayoutManager);
        recyclerItem.addItemDecoration(new SpaceItemDecoration());
        List<ItemModel> itemModels = itemDao.loadAllItems();
        itemLoadListener.onDrinkLoadSuccess(itemModels);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDrinkLoadSuccess(List<ItemModel> drinkModelList) {
        ItemAdapter adapter = new ItemAdapter(this, drinkModelList, recyclerItem);
        recyclerItem.setAdapter(adapter);
    }

    @Override
    public void onDrinkLoadFailed(String message) {
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {
        int cartSum = 0;
        for (CartModel cartModel : cartModelList) {
            cartSum += cartModel.getQuantity();
        }
        badge.setNumber(cartSum);
    }

    @Override
    public void onCartLoadFailed(String message) {
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countCartItem();
    }

    private void countCartItem() {
        List<CartModel> cartModels = cartDao.findAllCart();
        if (cartModels != null) {
            cartLoadListener.onCartLoadSuccess(cartModels);
        } else {
            cartLoadListener.onCartLoadFailed("Dont load anything!");
        }
    }
}