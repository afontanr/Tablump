package com.example.tablump;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class EditarPostActivity extends AppCompatActivity {

    Spinner spinner1;
    private SharedPreferences sp;
    String title;
    String username;
    private String preDesc, preCat;
    private String newTit, newDesc, newCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_post);

        Intent intent1 = getIntent();
        title = intent1.getStringExtra("title");

        Button accept = findViewById(R.id.acceptButton);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                TextView titulo = findViewById(R.id.txtTit);
                TextView descripcion = findViewById(R.id.txtDesc);
                spinner1 = findViewById(R.id.spinner);
                newTit = titulo.getText().toString();
                newDesc = descripcion.getText().toString();
                newCat = spinner1.getSelectedItem().toString();
                TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());
                tablumpDatabaseAdapter.open();
                sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                title = intent1.getStringExtra("title");
                username = sp.getString("username","");
                preDesc = tablumpDatabaseAdapter.getPost(title).getDescripcion();
                preCat = tablumpDatabaseAdapter.getPost(title).getCategory();
                if(newTit.equals("") && newDesc.equals("") && preCat.equals(newCat)){
                    tablumpDatabaseAdapter.close();
                    Toast.makeText(getApplicationContext(), "No se ha modificado nada", Toast.LENGTH_LONG).show();
                }

                else if(tablumpDatabaseAdapter.getPost(titulo.getText().toString()) == null || titulo.getText().toString().equals(title)){
                    if(newTit.equals("")){
                        newTit = title;
                    }
                    if(newDesc.equals("")){
                        newDesc = preDesc;
                    }
                    if(newTit.equals("")){
                        newCat = preCat;
                    }
                    Comment[] comments = tablumpDatabaseAdapter.getCommentFromTitle(title);
                    if(comments != null){
                        for(int i = 0;i<comments.length;i++){
                            tablumpDatabaseAdapter.insertComment(titulo.getText().toString(),comments[i].getUser().toString(),comments[i].getContent().toString());
                        }
                    }
                    tablumpDatabaseAdapter.updatePost(title,newTit,newDesc,newCat,username);
                    tablumpDatabaseAdapter.close();
                    Intent intent = new Intent(getBaseContext(), PostActivity.class);
                    intent.putExtra("titulo", newTit);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra("titulo", title);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
