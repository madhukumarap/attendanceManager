package com.example.attendenacemanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Database database;
    private ArrayList<Class> classes;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        initializeUI();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        database = new Database();
        classes = database.getAllClasses();
        if (classes == null) {
            classes = new ArrayList<>();
        }
        listAdapter = new ListAdapter();
    }

    private void initializeUI() {
        ListView listView = findViewById(R.id.listView);
        Button newClassButton = findViewById(R.id.new_class);
        newClassButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClassEditor.class);
            startActivity(intent);
        });
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        classes = database.getAllClasses();
        if (classes == null) {
            classes = new ArrayList<>();
        }
        listAdapter.notifyDataSetChanged();
    }

    public class ListAdapter extends BaseAdapter {
        public ListAdapter() {

        }
        @Override
        public int getCount() {
            return classes.size();
        }

        @Override
        public Object getItem(int i) {
            return classes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @SuppressLint("MissingInflatedId")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v;
            if (view == null) {
                LayoutInflater inflater = getLayoutInflater();
                v = inflater.inflate(R.layout.list_item, viewGroup, false);
                ViewHolder holder = new ViewHolder();
                holder.text = v.findViewById(R.id.text1);
                v.setTag(holder);
            } else {
                v = view;
            }

            ViewHolder holder = (ViewHolder) v.getTag();
            holder.text.setText(classes.get(i).getClassName());
            holder.text.setOnClickListener(v1 -> {
                Intent intent = new Intent(MainActivity.this, ClassEditor.class);
                intent.putExtra("Class ID", classes.get(i).getID());
                intent.putExtra("Class Name", classes.get(i).getClassName());
                startActivity(intent);
            });
            return v;
        }

        class ViewHolder {
            TextView text;
        }
    }
}
