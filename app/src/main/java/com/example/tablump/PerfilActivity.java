package com.example.tablump;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PerfilActivity extends AppCompatActivity {

    private String username;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(getBaseContext(), PrincipalActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_dashboard:
                    //Intent intent = new Intent(getBaseContext(), CrearPostActivity.class);
                    //startActivity(intent);

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


        Intent intent = getIntent();
        username = intent.getStringExtra("usuario");

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


        }

        return super.onOptionsItemSelected(item);
    }
}
