package com.example.tablump;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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


        Intent intent = getIntent();
        username = intent.getStringExtra("usuario");

        ////////////
        final TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());
        tablumpDatabaseAdapter.open();
        //tablumpDatabaseAdapter.insertUser("mail","no", "con");
        try {
            //Toast.makeText(getApplicationContext(), tablumpDatabaseAdapter.getUser("no").getEmail(), Toast.LENGTH_LONG).show();
        }
        catch(Exception e) {
            Log.d("E","Usuario no existente");
        }



        posts = tablumpDatabaseAdapter.searchPosts("");
        ///////////



        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //navigation.setSelectedItemId();

        //showActionBar();

        intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            buscar(query);
        }


        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("TODOS"));
        tabs.addTab(tabs.newTab().setText("MIS POSTS"));
        tabs.addTab(tabs.newTab().setText("FAVORITOS"));
        tabs.addTab(tabs.newTab().setText("OTRO FILTRO"));
        tabs.setTabTextColors(Color.WHITE, ContextCompat.getColor(getBaseContext(), R.color.colorSelectedText));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);


        tabs.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        //Toast.makeText(PrincipalActivity.this, tab.getText(), Toast.LENGTH_LONG).show();
                        if(tab.getPosition() == 0){
                            posts = tablumpDatabaseAdapter.searchPosts("");
                            if(posts.length>0){
                                String[] titulos = new String[posts.length];
                                String[] descripciones = new String[posts.length];
                                String[] categorias = new String[posts.length];
                                String[] usuarios = new String[posts.length];

                                for(int i = 0; i<posts.length;i++){
                                    titulos[i] = posts[i].getTitulo();
                                    descripciones[i] = posts[i].getDescripcion();
                                    categorias[i] = posts[i].getCategory();
                                    usuarios[i] = posts[i].getUsuario();
                                }

                                CustomList adapter = new CustomList(PrincipalActivity.this, titulos, descripciones);
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                final String[] finalTitulos = titulos;
//            ImageButton button = (ImageButton) listView.findViewById(R.id.like);
//            button.setFocusable(false);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        Toast.makeText(PrincipalActivity.this, "You Clicked at " + finalTitulos[+ position], Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getBaseContext(), PostActivity.class);
                                        //TODO ver por qué no va
                                        //intent.putExtra("titulo", finalTitulos[position]);
                                        startActivity(intent);

                                    }
                                });
                            }
                            else{
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(null);
                                //notifyDataSetChanged();
                            }
                        }
                        else if(tab.getPosition() == 1){

                            posts = tablumpDatabaseAdapter.getPostsFromUser(username);
                            if(posts.length>0){
                                String[] titulos = new String[posts.length];
                                String[] descripciones = new String[posts.length];
                                String[] categorias = new String[posts.length];
                                String[] usuarios = new String[posts.length];

                                for(int i = 0; i<posts.length;i++){
                                    titulos[i] = posts[i].getTitulo();
                                    descripciones[i] = posts[i].getDescripcion();
                                    categorias[i] = posts[i].getCategory();
                                    usuarios[i] = posts[i].getUsuario();
                                }

                                CustomList adapter = new CustomList(PrincipalActivity.this, titulos, descripciones);
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                final String[] finalTitulos = titulos;
//            ImageButton button = (ImageButton) listView.findViewById(R.id.like);
//            button.setFocusable(false);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        Toast.makeText(PrincipalActivity.this, "You Clicked at " + finalTitulos[+ position], Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getBaseContext(), PostActivity.class);
                                        //TODO ver por qué no va
                                        //intent.putExtra("titulo", finalTitulos[position]);
                                        startActivity(intent);

                                    }
                                });
                            }
                            else{
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(null);
                                //notifyDataSetChanged();
                            }
                        }

                        else if(tab.getPosition() == 2){

                            //TODO FAVORITOS
                            posts = tablumpDatabaseAdapter.searchPosts("mo");
                            if(posts.length>0){
                                String[] titulos = new String[posts.length];
                                String[] descripciones = new String[posts.length];
                                String[] categorias = new String[posts.length];
                                String[] usuarios = new String[posts.length];

                                for(int i = 0; i<posts.length;i++){
                                    titulos[i] = posts[i].getTitulo();
                                    descripciones[i] = posts[i].getDescripcion();
                                    categorias[i] = posts[i].getCategory();
                                    usuarios[i] = posts[i].getUsuario();
                                }

                                CustomList adapter = new CustomList(PrincipalActivity.this, titulos, descripciones);
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                final String[] finalTitulos = titulos;
//            ImageButton button = (ImageButton) listView.findViewById(R.id.like);
//            button.setFocusable(false);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        Toast.makeText(PrincipalActivity.this, "You Clicked at " + finalTitulos[+ position], Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getBaseContext(), PostActivity.class);
                                        //TODO ver por qué no va
                                        //intent.putExtra("titulo", finalTitulos[position]);
                                        startActivity(intent);

                                    }
                                });
                            }
                            else{
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(null);
                                //notifyDataSetChanged();
                            }
                        }

                        else if(tab.getPosition() == 3){

                            //TODO OTRO FILTRO
                            posts = tablumpDatabaseAdapter.searchPosts("");
                            if(posts.length>0){
                                String[] titulos = new String[posts.length];
                                String[] descripciones = new String[posts.length];
                                String[] categorias = new String[posts.length];
                                String[] usuarios = new String[posts.length];

                                for(int i = 0; i<posts.length;i++){
                                    titulos[i] = posts[i].getTitulo();
                                    descripciones[i] = posts[i].getDescripcion();
                                    categorias[i] = posts[i].getCategory();
                                    usuarios[i] = posts[i].getUsuario();
                                }

                                CustomList adapter = new CustomList(PrincipalActivity.this, titulos, descripciones);
                                ListView listView = (ListView) findViewById(R.id.list);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                final String[] finalTitulos = titulos;
//            ImageButton button = (ImageButton) listView.findViewById(R.id.like);
//            button.setFocusable(false);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        Toast.makeText(PrincipalActivity.this, "You Clicked at " + finalTitulos[+ position], Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getBaseContext(), PostActivity.class);
                                        //TODO ver por qué no va
                                        //intent.putExtra("titulo", finalTitulos[position]);
                                        startActivity(intent);

                                    }
                                });
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

        //TODO los posts se recogerán del SQLite

        if(posts.length>0){
            String[] titulos = new String[posts.length];
            String[] descripciones = new String[posts.length];
            String[] categorias = new String[posts.length];
            String[] usuarios = new String[posts.length];

            for(int i = 0; i<posts.length;i++){
                titulos[i] = posts[i].getTitulo();
                descripciones[i] = posts[i].getDescripcion();
                categorias[i] = posts[i].getCategory();
                usuarios[i] = posts[i].getUsuario();
            }

            CustomList adapter = new CustomList(PrincipalActivity.this, titulos, descripciones);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
            final String[] finalTitulos = titulos;
//            ImageButton button = (ImageButton) listView.findViewById(R.id.like);
//            button.setFocusable(false);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(PrincipalActivity.this, "You Clicked", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getBaseContext(), PostActivity.class);
                    //TODO ver por qué no va
                    //intent.putExtra("titulo", finalTitulos[position]);
                    startActivity(intent);

                }
            });
        }



//        ImageButton btn_sobre = findViewById(R.id.btn_sobre);
//        btn_sobre.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getBaseContext(), NotificacionesActivity.class);
//                startActivity(intent);
//            }
//        });

//        Button verPost = findViewById(R.id.btn_ver_post);
//        verPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), PostActivity.class);
//                startActivity(intent);
//
//            }
//        });

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
                startActivity(intent);
                //finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void buscar(String query) {
        Intent intent = new Intent(getBaseContext(), SearchResultActivity.class);
        intent.putExtra("titulo", query);
        startActivity(intent);
    }

//    private void showActionBar() {
//        LayoutInflater inflator = (LayoutInflater) this
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflator.inflate(R.layout.action_bar_custom, null);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setDisplayShowHomeEnabled (false);
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(true);
//        //actionBar.setTitle("");
//        actionBar.setTitle("Tablump");
//        actionBar.setCustomView(v);
//    }

}
