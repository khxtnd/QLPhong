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
import com.qlphong.model.Phong;

import java.util.Objects;

public class AddOrUpdatePhongActivity extends AppCompatActivity {
    private ImageView ivBack;
    private Button btSave;
    private EditText etName, etDesc;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_update_phong);
        ivBack = findViewById(R.id.iv_back);
        btSave = findViewById(R.id.bt_save);
        etName = findViewById(R.id.et_name);
        etDesc = findViewById(R.id.et_desc);
        tvTitle = findViewById(R.id.tv_title);

        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        if (Objects.equals(mode, "update")) {
            tvTitle.setText("Cập nhật phòng");
            Phong item = (Phong) intent.getSerializableExtra("item");
            if (item != null) {
                etName.setText(item.getTen());
                etDesc.setText(item.getMota());
                btSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = etName.getText().toString();
                        String mota = etDesc.getText().toString();
                        if (!ten.isEmpty() && !mota.isEmpty()) {
                            Phong i = new Phong(item.getMa(), ten, mota);
                            SQLiteDAO db = new SQLiteDAO(getApplicationContext());
                            db.updatePhong(i);
                            Toast.makeText(getBaseContext(), "Đã lưu thành công!", Toast.LENGTH_SHORT).show();
                            finish();
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
                    String mota = etDesc.getText().toString();
                    if (!ten.isEmpty() && !mota.isEmpty()) {
                        Phong i = new Phong(ten, mota);
                        SQLiteDAO db = new SQLiteDAO(getApplicationContext());
                        db.addPhong(i);
                        Toast.makeText(getBaseContext(), "Đã lưu thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Nhập lại!", Toast.LENGTH_SHORT).show();
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
}