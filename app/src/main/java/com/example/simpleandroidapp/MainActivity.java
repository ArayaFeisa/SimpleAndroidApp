package com.example.simpleandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchDataTask.Listener {

    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        new FetchDataTask(this).execute();
    }

    @Override
    public void onDataFetched(ArrayList<TodoItem> todoItems) {
        adapter = new CustomAdapter(this, todoItems);
        listView.setAdapter(adapter);
    }
}
