package com.example.tablump;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SearchResultActivity extends AppCompatActivity {

    private String busqueda = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        busqueda = intent.getStringExtra("titulo");
        Toast.makeText(SearchResultActivity.this, "Busca esto: " + busqueda, Toast.LENGTH_LONG).show();
    }
}
