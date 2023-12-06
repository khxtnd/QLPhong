package com.qlphong.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qlphong.R;
import com.qlphong.adapter.TaiSanSelectAdapter;
import com.qlphong.adapter.TaiSanTrongPhongAdapter;
import com.qlphong.dal.SQLiteDAO;
import com.qlphong.model.TaiSan;

import java.util.ArrayList;
import java.util.List;

public class AddTaiSanToPhongActivity extends AppCompatActivity implements TaiSanSelectAdapter.CheckBoxListener {
    private RecyclerView recyclerView;
    private ImageView ivBack;

    private TaiSanSelectAdapter adapter;

    private ArrayList<Integer> listMa = new ArrayList<>();

    private Button btSave;
    private SQLiteDAO db;

    private int maPhong=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tai_san_to_phong);
        ivBack = findViewById(R.id.iv_back);
        recyclerView = findViewById(R.id.recycleView);
        btSave = findViewById(R.id.bt_save);

        db = new SQLiteDAO(this);
        List<TaiSan> list = db.getTaiSanKhongTrongPhong();

        adapter = new TaiSanSelectAdapter(this);
        adapter.setList(list);
        adapter.setCheckBoxListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        Intent intent=getIntent();
        maPhong=intent.getIntExtra("maPhong", -1);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listMa.isEmpty()){
                    for(int i : listMa){
                        db.updateTaiSanTrongPhong(i, maPhong);
                    }
                    finish();
                }else {
                    Toast.makeText(getBaseContext(), "Chọn ít nhất 1 tài sản!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onItemClick(int maTaiSan) {
        if (listMa.contains(maTaiSan)) {
            listMa.remove(Integer.valueOf(maTaiSan));
        } else {
            listMa.add(maTaiSan);
        }
    }
}