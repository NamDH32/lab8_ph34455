package com.example.lab8_bai3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_update extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        EditText edtTen = findViewById(R.id.edt_tenSach);
        EditText edtnam = findViewById(R.id.edt_nam);
        EditText edttacGia = findViewById(R.id.edt_tacGia);

        SachModel sachModel = (SachModel) getIntent().getSerializableExtra("Sach");

        edtTen.setText(sachModel.getTenSach());
        edtnam.setText(sachModel.getYear()+"");
        edttacGia.setText(sachModel.getTacGia());

        Button btnSave = findViewById(R.id.btn_submit);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTen.getText().toString();
                String nam = edtnam.getText().toString();
                String tacGia = edttacGia.getText().toString();

                int yearXB = Integer.parseInt(nam);

                SachModel sachModel1 = new SachModel(tenSach,yearXB,tacGia);

                Intent data = new Intent();
                data.putExtra("sach", sachModel1);

            }
        });
    }
}