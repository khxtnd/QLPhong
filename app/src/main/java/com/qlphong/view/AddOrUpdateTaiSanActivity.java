package com.qlphong.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qlphong.R;
import com.qlphong.dal.SQLiteDAO;
import com.qlphong.model.TaiSan;

import java.util.Objects;

public class AddOrUpdateTaiSanActivity extends AppCompatActivity {
    private ImageView ivBack;
    private Button btSave;
    private EditText etName, etCategory, etLocation, etPrice;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_update_tai_san);

        initView();

        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        if (Objects.equals(mode, "update")) {
            tvTitle.setText("Cập nhật tài sản");
            TaiSan item = (TaiSan) intent.getSerializableExtra("item");
            if (item != null) {
                etName.setText(item.getTen());
                etCategory.setText(item.getLoai());
                etLocation.setText(item.getVitri());
                etPrice.setText(item.getGiatri() + "");

                btSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = etName.getText().toString();
                        String loai = etCategory.getText().toString();
                        String vitri = etLocation.getText().toString();
                        String gia = etPrice.getText().toString();

                        if (!ten.isEmpty() && !loai.isEmpty() && !vitri.isEmpty() && !gia.isEmpty()) {
                            int giatri = Integer.parseInt(gia);
                            if (giatri > 0) {
                                TaiSan i = new TaiSan(item.getMa(), ten, loai, vitri, giatri);
                                SQLiteDAO db = new SQLiteDAO(getApplicationContext());
                                db.updateTaiSan(i);
                                Toast.makeText(getBaseContext(), "Đã lưu thành công!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Nhập lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else {
            btSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ten = etName.getText().toString();
                    String loai = etCategory.getText().toString();
                    String vitri = etLocation.getText().toString();
                    String gia = etPrice.getText().toString();

                    if (!ten.isEmpty() && !loai.isEmpty() && !vitri.isEmpty() && !gia.isEmpty()) {
                        int giatri = Integer.parseInt(gia);
                        if (giatri > 0) {
                            TaiSan i = new TaiSan(ten, loai, vitri, giatri);
                            SQLiteDAO db = new SQLiteDAO(getApplicationContext());
                            db.addTaiSan(i);
                            Toast.makeText(getBaseContext(), "Đã lưu thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            });
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        btSave = findViewById(R.id.bt_save);
        etName = findViewById(R.id.et_name);
        etCategory = findViewById(R.id.et_category);
        etLocation = findViewById(R.id.et_location);
        etPrice = findViewById(R.id.et_price);
        tvTitle = findViewById(R.id.tv_title);
    }
}
