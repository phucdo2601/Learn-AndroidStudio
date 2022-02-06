package com.phucdn.learnaddtocartwithmssqlb1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phucdn.learnaddtocartwithmssqlb1.R;
import com.phucdn.learnaddtocartwithmssqlb1.dao.CartDao;
import com.phucdn.learnaddtocartwithmssqlb1.eventBus.MyUpdateCartEvent;
import com.phucdn.learnaddtocartwithmssqlb1.listener.ICartLoadListener;
import com.phucdn.learnaddtocartwithmssqlb1.listener.IRecyclerViewClickListener;
import com.phucdn.learnaddtocartwithmssqlb1.model.CartModel;
import com.phucdn.learnaddtocartwithmssqlb1.model.ItemModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private List<ItemModel> itemModelList;
    private ICartLoadListener iCartLoadListener;
    private RecyclerView rvStaffs;

    private CartDao cartDao = new CartDao();

    public ItemAdapter(Context context, List<ItemModel> itemModelList, ICartLoadListener iCartLoadListener) {
        this.context = context;
        this.itemModelList = itemModelList;
        this.iCartLoadListener = iCartLoadListener;
    }

    public ItemAdapter(Context context, List<ItemModel> itemModelList, RecyclerView rvStaffs) {
        this.context = context;
        this.itemModelList = itemModelList;
        this.rvStaffs = rvStaffs;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).
                inflate(R.layout.layout_item, parent, false));

//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.layout_item, parent, false);
//        view.setOnClickListener(onClickListener);
//        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context)
                .load(itemModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("$").append(itemModelList.get(position).getPrice()));
        holder.txtId.setText(new StringBuilder().append(itemModelList.get(position).getItemId()));
        holder.txtName.setText(new StringBuilder().append(itemModelList.get(position).getItemName()));

        holder.setListener((view, adapterPositon) -> {
            addToCart(itemModelList.get(position));
        });
    }

    private void addToCart(ItemModel itemModel) {
        boolean isCreateCart = false;

        CartModel findCartByItemId = cartDao.findCartByItemId(itemModel.getItemId());
        if (findCartByItemId.getItemId() != 0) {
            System.out.println(findCartByItemId.getCartId());
            int quanUp = findCartByItemId.getQuantity() + 1;
            findCartByItemId.setQuantity(quanUp);
            System.out.println(quanUp);
            isCreateCart = cartDao.updateCartByCartId(findCartByItemId);
            Toast.makeText(context, "Update item on cart is success", Toast.LENGTH_LONG).show();
            EventBus.getDefault().postSticky(new MyUpdateCartEvent());
        } else if (findCartByItemId.getItemId() == 0){
            System.out.println(findCartByItemId.getCartId());
            isCreateCart = cartDao.addCart(itemModel);
            Toast.makeText(context, "Add item on cart is success", Toast.LENGTH_LONG).show();
            EventBus.getDefault().postSticky(new MyUpdateCartEvent());
        }


    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView txtName;
        private TextView txtPrice;
        private TextView txtId;

        IRecyclerViewClickListener listener;

        public void setListener(IRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        private Unbinder unbinder;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            txtId = itemView.findViewById(R.id.txtId);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRecyclerClick(v, getAdapterPosition());
        }
    }

}
