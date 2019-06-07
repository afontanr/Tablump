package com.example.tablump;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {

    private String title;
    private String username;
    private String postOwner;
    private Post post;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        final TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());


        sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        username = sp.getString("username","");

        Intent intent = getIntent();
        title = intent.getStringExtra("titulo");

        post = tablumpDatabaseAdapter.getPost(title);
        postOwner=post.getUsuario();

        TextView title = findViewById(R.id.id_post_title);
        TextView username = findViewById(R.id.id_post_username);
        TextView contenido = findViewById(R.id.id_post_contenido);
        TextView tema = findViewById(R.id.id_post_tema);

        title.setText(post.getTitulo());
        username.setText(post.getUsuario());
        contenido.setText(post.getDescripcion());
        tema.setText(post.getCategory());

        FloatingActionButton addComment = findViewById(R.id.id_add_comment);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(this, ComentarPostActivity.class);
                // startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(username.equals(postOwner)){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.dashboard_perfil, menu);
            MenuItem searchItem = menu.findItem(R.id.btn_edit_profile);

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.btn_edit_profile:
                Intent intent = new Intent(this, EditarPostActivity.class);
                intent.putExtra("title", title);
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
