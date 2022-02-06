package com.phucdn.learnaddtocartwithmssqlb1.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.bumptech.glide.Glide;
import  com.phucdn.learnaddtocartwithmssqlb1.R;
import com.phucdn.learnaddtocartwithmssqlb1.dao.CartDao;
import com.phucdn.learnaddtocartwithmssqlb1.eventBus.MyUpdateCartEvent;
import com.phucdn.learnaddtocartwithmssqlb1.model.CartModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {

    private Context context;
    private List<CartModel> cartModelList;
    private CartDao cartDao = new CartDao();

    public MyCartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCartViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.layout_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartViewHolder holder, int position) {
        Glide.with(context)
                .load(cartModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("$").append(cartModelList.get(position).getPrice()));
        holder.txtName.setText(new StringBuilder().append(cartModelList.get(position).getItemName()));
        holder.txtQuantity.setText(new StringBuilder().append(cartModelList.get(position).getQuantity()));

        //Event
        // Decrease the quantity of item
        holder.btnMinus.setOnClickListener(v -> {
            minusCartItem(holder, cartModelList.get(position));
        });

        //Increase Quatity
        holder.btnPlus.setOnClickListener(v ->{
            plusCartItem(holder, cartModelList.get(position));
        });

        //Delete item in cart
        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle("Delete item")
                    .setMessage("Do you really want to delete item?")
                    .setNegativeButton("CANCEL", (dialog1, which) -> dialog1.dismiss())
                    .setPositiveButton("OK", (dialog2, which) -> {

                        //Temp remove
                        notifyItemRemoved(position);

                        deleteItemFromCart(cartModelList.get(position));
                        dialog2.dismiss();
                    }).create();
            alertDialog.show();
        });
    }

    private void deleteItemFromCart(CartModel cartModel) {
        String cartID = cartModel.getCartId();
        boolean isDelete = cartDao.deleteByCartId(cartID);
        if (isDelete == true) {
            EventBus.getDefault().postSticky(new MyUpdateCartEvent());
        }

    }

    private void plusCartItem(MyCartViewHolder holder, CartModel cartModel) {
        cartModel.setQuantity(cartModel.getQuantity() + 1);
        cartModel.setTotalPrice(cartModel.getQuantity() * cartModel.getPrice());

        //Update quantity
        holder.txtQuantity.setText(new StringBuilder().append(cartModel.getQuantity()));
        boolean isUpdate = cartDao.updateCartByCartId(cartModel);
        if (isUpdate == true) {
            EventBus.getDefault().postSticky(new MyUpdateCartEvent());
        }
    }

    private void minusCartItem(MyCartViewHolder holder, CartModel cartModel) {
        if (cartModel.getQuantity() > 1) {
            cartModel.setQuantity(cartModel.getQuantity() - 1);
            cartModel.setTotalPrice(cartModel.getQuantity() * cartModel.getPrice());

            //update quantiy
            holder.txtQuantity.setText(new StringBuilder().append(cartModel.getQuantity()));
            boolean isUpdate = cartDao.updateCartByCartId(cartModel);
            if (isUpdate == true) {
                EventBus.getDefault().postSticky(new MyUpdateCartEvent());
            }
        }
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MyCartViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.btnMinus)
        ImageView btnMinus;
        @BindView(R.id.btnPlus)
        ImageView btnPlus;
        @BindView(R.id.btnDelete)
        ImageView btnDelete;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.txtQuantity)
        TextView txtQuantity;

        Unbinder unbinder;

        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
