package com.digitalistic.co.restaurant_userapp.LocalDB.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalistic.co.restaurant_userapp.LocalDB.utils.model.OrderCart;
import com.digitalistic.co.restaurant_userapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private CartClickedListeners cartClickedListeners;
    private List<OrderCart> orderCartList;

    public CartAdapter(CartClickedListeners cartClickedListeners){
        this.cartClickedListeners = cartClickedListeners;
    }

    public void setOrderCartList(List<OrderCart> orderCartList) {
        this.orderCartList = orderCartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcartlist, parent, false);
        return new CartViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        OrderCart orderCart = orderCartList.get(position);
        holder.itemNameTv.setText(orderCart.getItem_name());
        holder.itemQuantity.setText(orderCart.getQuantity() + "");
        holder.itemPriceTv.setText(orderCart.getTotalItemPrice() + "");

        holder.deleteOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onDeleteClicked(orderCart);
            }
        });


        holder.addQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onPlusClicked(orderCart);
            }
        });

        holder.minusQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onMinusClicked(orderCart);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orderCartList == null) {
            return 0;
        } else {
            return orderCartList.size();
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView itemNameTv, itemPriceTv, itemQuantity;
        private ImageView deleteOrderBtn, orderImageView;
        ImageButton addQuantityBtn, minusQuantityBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTv = itemView.findViewById(R.id.eachCartItemName);
            itemPriceTv = itemView.findViewById(R.id.eachCartItemPriceTv);
            deleteOrderBtn = itemView.findViewById(R.id.eachCartItemDeleteBtn);
            orderImageView = itemView.findViewById(R.id.eachCartItemIV);
            itemQuantity = itemView.findViewById(R.id.eachCartItemQuantityTV);
            addQuantityBtn = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            minusQuantityBtn = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);
        }
    }
    public interface CartClickedListeners {
        void onDeleteClicked(OrderCart orderCart);

        void onPlusClicked(OrderCart orderCart);

        void onMinusClicked(OrderCart orderCart);
    }
}
