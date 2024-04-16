package com.example.simpleandroidapp;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchDataTask extends AsyncTask<Void, Void, ArrayList<TodoItem>> {
    private static final String URL_STRING = "https://mgm.ub.ac.id/todo.php";
    private Listener listener;

    public FetchDataTask(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<TodoItem> doInBackground(Void... voids) {
        ArrayList<TodoItem> todoItems = new ArrayList<>();

        try {
            URL url = new URL(URL_STRING);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            String response = stringBuilder.toString();

            Gson gson = new Gson();
            Type todoListType = new TypeToken<ArrayList<TodoItem>>(){}.getType();
            todoItems = gson.fromJson(response, todoListType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return todoItems;
    }

    @Override
    protected void onPostExecute(ArrayList<TodoItem> todoItems) {
        super.onPostExecute(todoItems);
        if (listener != null) {
            listener.onDataFetched(todoItems);
        }
    }

    public interface Listener {
        void onDataFetched(ArrayList<TodoItem> todoItems);
    }
}
