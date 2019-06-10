package com.example.tablump;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NotificacionesActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private String username;

    private Notification [] notifications;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    item.setCheckable(true);
                    Intent intent = new Intent(getBaseContext(), PrincipalActivity.class);
                    intent.putExtra("usuario", username);
                    startActivity(intent);
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    intent = new Intent(getBaseContext(), CrearPostActivity.class);
                    startActivity(intent);
                    //mTextMessage.setText(R.string.title_new_post);
                    return true;
                case R.id.navigation_notifications:
                    intent = new Intent(getBaseContext(), PerfilActivity.class);
                    startActivity(intent);
                    //mTextMessage.setText(R.string.title_profile);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        Intent intent = getIntent();
        username = intent.getStringExtra("usuario");

        TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());
        tablumpDatabaseAdapter.open();

        notifications = tablumpDatabaseAdapter.getNotificationsFromUser(username);


        if(notifications != null && notifications.length>0){

            String[] tipos = new String[notifications.length];
            String[] titulos = new String[notifications.length];
            String[] usuariosRecibe = new String[notifications.length];
            String[] usuariosRealiza = new String[notifications.length];

            for(int i = 0; i<notifications.length;i++){
                tipos[i] = notifications[i].getTipo();
                titulos[i] = notifications[i].getTitulo();
                usuariosRecibe[i] = notifications[i].getUsuarioRecibe();
                usuariosRealiza[i] = notifications[i].getUsuarioRealiza();
            }

            tipos = invertir(tipos);
            titulos = invertir(titulos);
            usuariosRecibe = invertir(usuariosRecibe);
            usuariosRealiza = invertir(usuariosRealiza);


            CustomListNotificaciones adapter = new CustomListNotificaciones(NotificacionesActivity.this, tipos, titulos, usuariosRecibe, usuariosRealiza);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
            final String[] finalTitulos = titulos;
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setCheckable(false);
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

    String [] invertir(String[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            String temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }
}
