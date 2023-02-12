package com.example.moneyparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Document doc;
    private Runnable runnable;
    private Thread secThread;
    private ListView listView;
    private CustomArrayAdapter customArrayAdapter;
    private List<ListItemClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        customArrayAdapter = new CustomArrayAdapter(this, R.layout.list_item_1, arrayList, getLayoutInflater());
        listView.setAdapter(customArrayAdapter);
        runnable = new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
        ListItemClass items = new ListItemClass();
        items.setData_1("Dollar");
        items.setData_2("72");
        items.setData_3("77");
        arrayList.add(items);
        items = new ListItemClass();
        items.setData_1("Euro");
        items.setData_2("79");
        items.setData_3("84");
        arrayList.add(items);
        customArrayAdapter.notifyDataSetChanged();
    }

    private void getWeb() {
        try {
            doc = Jsoup.connect("https://www.banki.ru/products/currency/").get();
            Elements tables = doc.getElementsByTag("tbody");
            Element moneyTable = tables.get(0);
            Elements moneyRang = moneyTable.children();
            Log.d("MyLog", "Tbody Size " + moneyRang.get(0).text());
            Log.d("MyLog", "TextSite: " + doc.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}