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
        //tablumpDatabaseAdapter.insertUser("mail","no", "con");
        try {
            //Toast.makeText(getApplicationContext(), tablumpDatabaseAdapter.getUser("no").getEmail(), Toast.LENGTH_LONG).show();
        }
        catch(Exception e) {
            Log.d("E","Usuario no existente");
        }


        posts = tablumpDatabaseAdapter.searchPosts(busqueda);
        //Toast.makeText(SearchResultActivity.this, "Busca esto: " + busqueda, Toast.LENGTH_LONG).show();

        if(posts.length>0){
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
                //TODO ESTO VENDRÁ DE LA DDBB
                isLiked[i]= false;
            }

            CustomList adapter = new CustomList(SearchResultActivity.this, titulos, descripciones, isLiked, username);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
            final String[] finalTitulos = titulos;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(SearchResultActivity.this, "You Clicked at " + finalTitulos[+ position], Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getBaseContext(), PostActivity.class);
                    //TODO ver por qué no va
                    //intent.putExtra("titulo", finalTitulos[position]);
                    startActivity(intent);

                }
            });
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
