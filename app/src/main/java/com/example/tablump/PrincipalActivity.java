package com.example.tablump;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PrincipalActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private Post [] posts;

    private String username;

    private SharedPreferences sp;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(getBaseContext(), CrearPostActivity.class);
                    startActivity(intent);
                    //mTextMessage.setText(R.string.title_new_post);
                    return true;
                case R.id.navigation_notifications:
                    intent = new Intent(getBaseContext(), PerfilActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        sp = getSharedPreferences("preferences",Context.MODE_PRIVATE);

        Intent intent = getIntent();
        username = intent.getStringExtra("usuario");

        SharedPreferences.Editor editor = sp.edit();
        if (sp.getString("username","").equals("")) {
            editor.putString("username", username);
            editor.commit();
        }

        final TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());
        tablumpDatabaseAdapter.open();

        posts = tablumpDatabaseAdapter.searchPosts("");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent2 = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent2.getAction())) {
            String query = intent2.getStringExtra(SearchManager.QUERY);
            //Toast.makeText(PrincipalActivity.this, us, Toast.LENGTH_LONG).show();
            buscar(query,sp.getString("username",""));
        }

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("TODOS"));
        tabs.addTab(tabs.newTab().setText("MIS POSTS"));
        tabs.addTab(tabs.newTab().setText("FAVORITOS"));
        tabs.addTab(tabs.newTab().setText("ANUNCIOS"));
        tabs.setTabTextColors(Color.WHITE, ContextCompat.getColor(getBaseContext(), R.color.colorSelectedText));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabs.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        //TODOS
                        if(tab.getPosition() == 0){
                            posts = tablumpDatabaseAdapter.searchPosts("");
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

                                CustomList adapter = new CustomList(PrincipalActivity.this, titulos, descripciones, isLiked, username);
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                            else{
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(null);
                                //notifyDataSetChanged();
                            }
                        }
                        //MIS POSTS
                        else if(tab.getPosition() == 1){

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

                                CustomList adapter = new CustomList(PrincipalActivity.this, titulos, descripciones, isLiked, username);
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                            else{
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(null);
                                //notifyDataSetChanged();
                            }
                        }
                        //FAVORITOS
                        else if(tab.getPosition() == 2){

                            //TODO FAVORITOS
                            Like [] likes = tablumpDatabaseAdapter.getLikesFromUser(username);
                            if (likes != null){
                                posts = new Post[likes.length];
                                for(int i = 0;i<likes.length;i++){
                                    posts[i] = tablumpDatabaseAdapter.getPost(likes[i].getTitulo());
                                }
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

                                    CustomList adapter = new CustomList(PrincipalActivity.this, titulos, descripciones, isLiked, username);
                                    ListView listView = (ListView) findViewById(R.id.list);
                                    listView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }
                            }

                            else{
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(null);
                                //notifyDataSetChanged();
                            }
                        }
                        //CATEGORÍA: ANUNCIOS
                        else if(tab.getPosition() == 3){

                            posts = tablumpDatabaseAdapter.getPostsFromCategory("anuncios");
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

                                CustomList adapter = new CustomList(PrincipalActivity.this, titulos, descripciones, isLiked, username);
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                            else{
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(null);
                                //notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        // ...
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        // ...
                    }
                }
        );


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

            CustomList adapter = new CustomList(PrincipalActivity.this, titulos, descripciones, isLiked, username);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);


            NotificationManager notificationManager = (NotificationManager)
                    this.getSystemService(Context.NOTIFICATION_SERVICE);
            //Toast.makeText(this, sp.getString("username",""), Toast.LENGTH_LONG).show();
            final String channelId = sp.getString("username","");
            final CharSequence channelName = "name1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notificationChannel = new NotificationChannel(channelId, channelName,
                        importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400,
                        500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        @SuppressLint("WrongConstant") SearchManager searchManager = (SearchManager) PrincipalActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(PrincipalActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:

                return true;

            case R.id.btn_sobre:
                Intent intent = new Intent(this, NotificacionesActivity.class);
                intent.putExtra("usuario", username);
                startActivity(intent);
                //finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void buscar(String query, String user) {
        Intent intent = new Intent(getBaseContext(), SearchResultActivity.class);
        intent.putExtra("titulo", query);
        intent.putExtra("usuario", user);
        //Toast.makeText(PrincipalActivity.this, user, Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

}
