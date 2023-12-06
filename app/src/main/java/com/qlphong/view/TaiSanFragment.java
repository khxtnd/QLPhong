package com.qlphong.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qlphong.R;
import com.qlphong.adapter.TaiSanAdapter;
import com.qlphong.dal.SQLiteDAO;
import com.qlphong.model.TaiSan;

import java.util.List;

public class TaiSanFragment extends Fragment implements TaiSanAdapter.TaiSanListener {
    private RecyclerView recyclerView;
    private FloatingActionButton btAdd;
    private TaiSanAdapter adapter;
    private SQLiteDAO db;
    private Button btAll, btOver10M;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_san, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        btAdd = view.findViewById(R.id.bt_add);
        btAll= view.findViewById(R.id.bt_all);
        btOver10M=view.findViewById(R.id.bt_over_10m);

        db = new SQLiteDAO(getContext());
        adapter = new TaiSanAdapter(getContext());

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddOrUpdateTaiSanActivity.class);
                intent.putExtra("mode", "add");
                startActivity(intent);
            }
        });

        btAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAll();
            }
        });

        btOver10M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOver10M();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        TaiSan item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), AddOrUpdateTaiSanActivity.class);
        intent.putExtra("mode", "update");
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAll();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemTaiSanListener(this);
    }

    private void getAll(){
        List<TaiSan> list = db.getAllTaiSan();
        adapter.setList(list);
        btAll.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btAll.setBackgroundResource(R.drawable.bt_background);

        btOver10M.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        btOver10M.setBackgroundResource(R.drawable.et_background);
    }

    private void getOver10M(){
        List<TaiSan> list = db.getTaiSanOver10Million();
        adapter.setList(list);
        btOver10M.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        btOver10M.setBackgroundResource(R.drawable.bt_background);

        btAll.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        btAll.setBackgroundResource(R.drawable.et_background);
    }
}