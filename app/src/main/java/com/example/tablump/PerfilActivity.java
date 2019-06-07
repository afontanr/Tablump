package com.example.tablump;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private Post [] posts;

    private String username;

    private String email;

    private SharedPreferences sp;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(getBaseContext(), PrincipalActivity.class);
                    intent.putExtra("usuario", username);
                    startActivity(intent);

                    return true;
                case R.id.navigation_dashboard:
                    intent = new Intent(getBaseContext(), CrearPostActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_logout:
                    Intent intent2 = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent2);
                    finish();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);



        Intent intent = getIntent();
        username = sp.getString("username","");

        final TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());
        tablumpDatabaseAdapter.open();

        email = tablumpDatabaseAdapter.getUser(username).getEmail();

        TextView u = findViewById(R.id.id_post_title);
        TextView e = findViewById(R.id.id__perfil_email);

        u.setText(username);
        e.setText(email);

        posts = tablumpDatabaseAdapter.getPostsFromUser(username);

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

            CustomList adapter = new CustomList(PerfilActivity.this, titulos, descripciones, isLiked, username);
            ListView listView = (ListView) findViewById(R.id.id_perfil_posts);
            listView.setAdapter(adapter);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setCheckable(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_perfil, menu);
        MenuItem searchItem = menu.findItem(R.id.btn_edit_profile);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.btn_edit_profile:
                Intent intent = new Intent(this, EditarPerfilActivity.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        intent.putExtra("usuario", username);
        startActivity(intent);
    }
}
