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
import android.widget.Toast;
import android.util.Log;

public class EditarPostActivity extends AppCompatActivity {

    Spinner spinner1;
    private SharedPreferences sp;
    String title;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_post);

        Button accept = findViewById(R.id.acceptButton);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                TextView titulo = findViewById(R.id.txtTit);
                TextView descripcion = findViewById(R.id.txtDesc);
                spinner1 = findViewById(R.id.spinner);
                TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());
                tablumpDatabaseAdapter.open();
                sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                title = intent1.getStringExtra("title");
                username = sp.getString("username","");
                Log.d("BLA",username);
                if(titulo.getText().toString().equals("") || descripcion.getText().toString().equals("")){
                    tablumpDatabaseAdapter.close();
                    Toast.makeText(getApplicationContext(), "No se han introducido todos los campos", Toast.LENGTH_LONG).show();
                }

                else if(tablumpDatabaseAdapter.getPost(titulo.getText().toString()) == null || titulo.getText().toString().equals(title)){
                    //tablumpDatabaseAdapter.deletePost(title);
                    //tablumpDatabaseAdapter.insertPost(titulo.getText().toString(),descripcion.getText().toString(),spinner1.getSelectedItem().toString(),username);
                    Comment[] comments = tablumpDatabaseAdapter.getCommentFromTitle(title);
                    if(comments != null){
                        for(int i = 0;i<comments.length;i++){
                            tablumpDatabaseAdapter.insertComment(title,comments[i].getUser().toString(),comments[i].getContent().toString());
                        }
                    }
                    tablumpDatabaseAdapter.updatePost(title,titulo.getText().toString(),descripcion.getText().toString(),spinner1.getSelectedItem().toString(),username);
                    tablumpDatabaseAdapter.close();
                    Intent intent = new Intent(getBaseContext(), PerfilActivity.class);
                    intent.putExtra("titulo", title);
                    startActivity(intent);
                }else{
                    tablumpDatabaseAdapter.close();
                    Toast.makeText(getApplicationContext(), "Titulo ya escogido", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
}
