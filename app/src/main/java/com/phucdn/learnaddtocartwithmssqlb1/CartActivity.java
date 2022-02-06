
package com.phucdn.learnaddtocartwithmssqlb1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.phucdn.learnaddtocartwithmssqlb1.adapter.MyCartAdapter;
import com.phucdn.learnaddtocartwithmssqlb1.dao.CartDao;
import com.phucdn.learnaddtocartwithmssqlb1.eventBus.MyUpdateCartEvent;
import com.phucdn.learnaddtocartwithmssqlb1.listener.ICartLoadListener;
import com.phucdn.learnaddtocartwithmssqlb1.model.CartModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements ICartLoadListener {

    @BindView(R.id.recycler_cart)
    RecyclerView recyclerCart;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.txtTotal)
    TextView txtTotal;

    private CartDao cartDao = new CartDao();

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
        loadCartFromDb();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        
        initUi();
        loadCartFromDb();
    }

    private void loadCartFromDb() {
        List<CartModel> cartModelList = new ArrayList<>();
        cartModelList = cartDao.findAllCart();
        if (cartModelList != null) {
            cartLoadListener.onCartLoadSuccess(cartModelList);
        } else {
            cartLoadListener.onCartLoadFailed("Cart Empty");
        }
    }

    private void initUi() {
        ButterKnife.bind(this);

        cartLoadListener = this;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCart.setLayoutManager(layoutManager);
        recyclerCart.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {
        double sum = 0;
        for (CartModel cartModel : cartModelList) {
            sum += cartModel.getTotalPrice();
        }
        txtTotal.setText(new StringBuilder("$").append(sum));
        MyCartAdapter adapter = new MyCartAdapter(this, cartModelList);
        recyclerCart.setAdapter(adapter);
    }

    @Override
    public void onCartLoadFailed(String message) {
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG).show();
    }
}