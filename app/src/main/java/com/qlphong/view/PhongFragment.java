package com.qlphong.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qlphong.R;
import com.qlphong.adapter.PhongAdapter;
import com.qlphong.dal.SQLiteDAO;
import com.qlphong.model.Phong;

import java.util.List;

public class PhongFragment extends Fragment implements PhongAdapter.PhongListener {
    private RecyclerView recyclerView;
    private FloatingActionButton btAdd;
    private PhongAdapter adapter;
    private SQLiteDAO db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phong, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        btAdd = view.findViewById(R.id.bt_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddOrUpdatePhongActivity.class);
                intent.putExtra("mode", "add");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Phong item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ChiTietPhongActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new PhongAdapter(getContext());
        db = new SQLiteDAO(getContext());
        List<Phong> list = db.getAllPhong();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemPhongListener(this);
    }
}