package com.example.tablump;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SearchResultActivity extends AppCompatActivity {

    private Post [] posts;

    private String busqueda = "";
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        busqueda = intent.getStringExtra("titulo");
        username = intent.getStringExtra("usuario");

        ////////////
        TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());
        tablumpDatabaseAdapter.open();

        posts = tablumpDatabaseAdapter.searchPosts(busqueda);

        if(posts != null && posts.length>0){
            String[] titulos = new String[posts.length];
            String[] descripciones = new String[posts.length];
            String[] categorias = new String[posts.length];
            String[] usuarios = new String[posts.length];
            Boolean[] isLiked = new Boolean[posts.length];

            for(int i = 0; i<posts.length;i++){
                titulos[i] = posts[i].getTitulo();
                descripciones[i] = posts[i].getDescripcion();
                categorias[i] = posts[i].getCategory();
                usuarios[i] = posts[i].getUsuario();

                isLiked[i]= tablumpDatabaseAdapter.getLikePostUser(posts[i].getTitulo(),username);
            }

            CustomList adapter = new CustomList(SearchResultActivity.this, titulos, descripciones, isLiked, username);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra("usuario", username);
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
