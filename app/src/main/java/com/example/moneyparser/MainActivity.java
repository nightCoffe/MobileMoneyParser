package com.example.moneyparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Document doc;
    private Runnable runnable;
    private Thread secThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        runnable = new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
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