package com.example.tablump;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class CrearPostActivity extends AppCompatActivity {

    private Spinner spinner1;
    private String username;
    private String title;
    private String description;
    private String category;
    private SharedPreferences sp;
    private TablumpDatabaseAdapter tablumpDatabaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_post);
        tablumpDatabaseAdapter=new TablumpDatabaseAdapter(getApplicationContext());

        sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        username = sp.getString("username","");

        Button accept = findViewById(R.id.acceptButton);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView titleView = findViewById(R.id.txtTit);
                TextView descriptionView = findViewById(R.id.txtDesc);
                spinner1 = findViewById(R.id.spinner);

                title = titleView.getText().toString();
                description = descriptionView.getText().toString();
                category = spinner1.getSelectedItem().toString();

                tablumpDatabaseAdapter.open();
                tablumpDatabaseAdapter.insertPost(title,description,category,username);
                tablumpDatabaseAdapter.close();

                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra("usuario", username);
        startActivity(intent);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
}
