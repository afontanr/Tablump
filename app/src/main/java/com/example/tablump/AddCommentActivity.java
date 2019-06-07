package com.example.tablump;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddCommentActivity extends AppCompatActivity {

    private String title;
    private String username;
    private TablumpDatabaseAdapter tablumpDatabaseAdapter;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        tablumpDatabaseAdapter=new TablumpDatabaseAdapter(getApplicationContext());
        username = sp.getString("username","");

        Intent intent = getIntent();
        title = intent.getStringExtra("title");


        Button comentar = findViewById(R.id.button_comentar);
        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView comentarioView = findViewById(R.id.comentario);
                String comentario = comentarioView.getText().toString();
                try {
                    tablumpDatabaseAdapter.open();
                    tablumpDatabaseAdapter.insertComment(title,username,comentario);
                    tablumpDatabaseAdapter.close();
                    onBackPressed();

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Usuario no " +
                            "existente", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra("usuario", username);
        startActivity(intent);
    }




}
