package com.example.lab8_bai3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<SachModel> arrsach = new ArrayList<>();
    sachAdapter sachAdapter;
    int indexSachEdix = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityResultLauncher<Intent> getData = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    SachModel sachModel = (SachModel) result.getData().getSerializableExtra("Sach");
                    arrsach.set(indexSachEdix,sachModel);
                    sachAdapter.notifyDataSetChanged();
                }
            }
        });

        ListView lv_sach = findViewById(R.id.lv_quan_ly_sach);
        arrsach.add(new SachModel("Doermon", 1998, "Hoài Nam"));
        arrsach.add(new SachModel("Conan", 1980, "Hoài Thu"));
        arrsach.add(new SachModel("Naruto", 1977, "Hoài Linh"));
        sachAdapter = new sachAdapter(MainActivity.this, arrsach);
        lv_sach.setAdapter(sachAdapter);
        lv_sach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), activity_update.class);
                intent.putExtra("Sach", arrsach.get(position));
            }
        });
    }

    private class sachAdapter extends BaseAdapter {
        Activity activity;
        ArrayList<SachModel> list;

        public sachAdapter(Activity activity, ArrayList<SachModel> list) {
            this.activity = activity;
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_sach, parent, false);
            SachModel sachModel = list.get(position);

            TextView txt_ten = convertView.findViewById(R.id.txt_ten);
            TextView txt_nam = convertView.findViewById(R.id.txt_nam);
            TextView txt_tacgia = convertView.findViewById(R.id.txt_tacgia);

            txt_ten.setText(sachModel.getTenSach());
            txt_nam.setText(sachModel.getYear() + "");
            txt_tacgia.setText(sachModel.getTacGia());

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //thông báo để hỏi có muốn xóa k
                    new AlertDialog.Builder(activity).setMessage("Bạn có chắc chán muốn xóa").setPositiveButton
                            ("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list.remove(position);

                            notifyDataSetChanged();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

                    return false;
                }
            });

            return convertView;
        }
    }
}