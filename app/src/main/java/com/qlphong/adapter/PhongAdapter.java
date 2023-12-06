package com.qlphong.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlphong.R;
import com.qlphong.dal.SQLiteDAO;
import com.qlphong.model.Phong;
import com.qlphong.view.AddOrUpdatePhongActivity;

import java.util.ArrayList;
import java.util.List;

public class PhongAdapter extends RecyclerView.Adapter<PhongAdapter.PhongViewHolder> {
    private List<Phong> list;
    private final Context context;

    public interface PhongListener {
        void onItemClick(View view, int position);
    }

    private PhongListener itemListener;

    public void setItemPhongListener(PhongListener itemListener) {
        this.itemListener = itemListener;
    }

    public PhongAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();

    }

    public void setList(List<Phong> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Phong getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public PhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phong, parent, false);
        return new PhongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Phong item = list.get(position);
        holder.tvName.setText(item.getTen());
        holder.tvDesc.setText(item.getMota());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc chắn muốn xóa phòng không?")
                        .setIcon(R.drawable.ic_delete_40)
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SQLiteDAO db = new SQLiteDAO(context);
                                db.deletePhong(item.getMa());

                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                                Toast.makeText(context, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
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


        holder.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddOrUpdatePhongActivity.class);
                intent.putExtra("mode", "update");
                intent.putExtra("item", item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvName;
        private final TextView tvDesc;
        private final ImageView ivDelete;
        private final Button btUpdate;

        public PhongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            btUpdate = itemView.findViewById(R.id.bt_update);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemListener != null) {
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
