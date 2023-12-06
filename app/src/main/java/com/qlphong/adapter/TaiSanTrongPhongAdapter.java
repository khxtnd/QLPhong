package com.qlphong.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlphong.R;
import com.qlphong.dal.SQLiteDAO;
import com.qlphong.model.TaiSan;

import java.util.ArrayList;
import java.util.List;

public class TaiSanTrongPhongAdapter extends RecyclerView.Adapter<TaiSanTrongPhongAdapter.TaiSanViewHolder> {
    private List<TaiSan> list;
    private final Context context;

    public TaiSanTrongPhongAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<TaiSan> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TaiSanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tai_san, parent, false);
        return new TaiSanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaiSanViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TaiSan item = list.get(position);
        holder.tvName.setText(item.getTen());
        holder.tvCategory.setText(item.getLoai());
        holder.tvLocation.setText(item.getVitri());
        holder.tvPrice.setText(item.getGiatri() + "");

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc chắn muốn xóa tài sản này khỏi phòng không?")
                        .setIcon(R.drawable.ic_delete_40)
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SQLiteDAO db = new SQLiteDAO(context);
                                db.updateTaiSanTrongPhong(item.getMa(), -1);
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                                Toast.makeText(context, "Đã xóa tài sản khỏi phòng!!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TaiSanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvCategory;
        private final TextView tvLocation;
        private final TextView tvPrice;
        private final ImageView ivDelete;

        public TaiSanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCategory = itemView.findViewById(R.id.tv_category);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

}
