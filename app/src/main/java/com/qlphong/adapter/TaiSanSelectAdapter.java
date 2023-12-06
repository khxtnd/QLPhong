package com.qlphong.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlphong.R;
import com.qlphong.model.TaiSan;

import java.util.ArrayList;
import java.util.List;

public class TaiSanSelectAdapter extends RecyclerView.Adapter<TaiSanSelectAdapter.TaiSanSelectViewHolder> {
    private List<TaiSan> list;
    private final Context context;

    public interface CheckBoxListener {
        void onItemClick(int maTaiSan);
    }

    private CheckBoxListener checkBoxListener;

    public void setCheckBoxListener(CheckBoxListener checkBoxListener) {
        this.checkBoxListener = checkBoxListener;
    }

    public TaiSanSelectAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<TaiSan> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TaiSanSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_tai_san, parent, false);
        return new TaiSanSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaiSanSelectViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TaiSan item = list.get(position);
        holder.tvName.setText(item.getTen());
        holder.tvCategory.setText(item.getLoai());
        holder.tvLocation.setText(item.getVitri());
        holder.tvPrice.setText(item.getGiatri() + "");

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxListener.onItemClick(item.getMa());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TaiSanSelectViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvCategory;
        private final TextView tvLocation;
        private final TextView tvPrice;
        private final CheckBox checkBox;

        public TaiSanSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCategory = itemView.findViewById(R.id.tv_category);
            checkBox = itemView.findViewById(R.id.checkbox);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
