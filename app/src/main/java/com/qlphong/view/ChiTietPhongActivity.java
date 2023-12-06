package com.qlphong.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qlphong.R;
import com.qlphong.adapter.TaiSanTrongPhongAdapter;
import com.qlphong.dal.SQLiteDAO;
import com.qlphong.model.Phong;
import com.qlphong.model.TaiSan;

import java.util.List;

public class ChiTietPhongActivity extends AppCompatActivity {
    private ImageView ivBack;
    private RecyclerView recyclerView;
    private TaiSanTrongPhongAdapter adapter;
    private FloatingActionButton btAdd;
    private Phong phong;
    private TextView tvName, tvDesc;
    private SQLiteDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phong);
        ivBack = findViewById(R.id.iv_back);
        recyclerView = findViewById(R.id.recycleView);
        tvName = findViewById(R.id.tv_name);
        tvDesc = findViewById(R.id.tv_desc);
        btAdd = findViewById(R.id.bt_add);

        Intent intent = getIntent();
        phong = (Phong) intent.getSerializableExtra("item");
        tvName.setText(phong.getTen());
        tvDesc.setText(phong.getMota());

        db = new SQLiteDAO(this);
        adapter = new TaiSanTrongPhongAdapter(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTaiSanToPhongActivity.class);
                intent.putExtra("maPhong", phong.getMa());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        List<TaiSan> list = db.getTaiSanTrongPhong(phong.getMa());
        adapter.setList(list);
    }
}