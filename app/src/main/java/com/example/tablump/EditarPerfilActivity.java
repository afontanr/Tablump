package com.example.tablump;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class EditarPerfilActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private String username;
    private String prePass, preMail;
    private String newUser, newMail, newPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarperfil);

        Button accept = findViewById(R.id.editarperfil_boton);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView user = findViewById(R.id.editarperfil_usuario);
                TextView pass = findViewById(R.id.editarperfil_contraseña);
                TextView mail = findViewById(R.id.editarperfil_correo);
                newUser = user.getText().toString();
                newMail = mail.getText().toString();
                newPass = pass.getText().toString();
                TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());
                tablumpDatabaseAdapter.open();
                sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                username = sp.getString("username","");
                prePass = tablumpDatabaseAdapter.getUser(username).getPassword();
                preMail = tablumpDatabaseAdapter.getUser(username).getEmail();

                if(newUser.equals("") && newPass.equals("") && newMail.equals("")){
                    tablumpDatabaseAdapter.close();
                    Toast.makeText(getApplicationContext(), "No se ha realizado ningun cambio", Toast.LENGTH_LONG).show();
                }

                else if(tablumpDatabaseAdapter.getUser(user.getText().toString()) == null && !user.getText().toString().equals(username)){
                    if(newUser.equals("")){
                        newUser = username;
                    }
                    if(newPass.equals("")){
                        newPass = prePass;
                    }
                    if(newMail.equals("")){
                        newMail = preMail;
                    }
                    Like [] likes = tablumpDatabaseAdapter.getLikesFromUser(username);
                    Post [] posts = tablumpDatabaseAdapter.getPostsFromUser(username);
                    Notification [] notificaciones = tablumpDatabaseAdapter.getNotificationsFromUser(username);
                    tablumpDatabaseAdapter.deleteUser(username);
                    tablumpDatabaseAdapter.insertUser(newMail,newUser,newPass);
                    if(likes!= null) {
                        for (int i = 0; i < likes.length; i++) {
                            tablumpDatabaseAdapter.updateLike(likes[i].getTitulo().toString(), newUser);
                        }
                    }
                    if(posts!= null) {
                        for (int i = 0; i < posts.length; i++) {
                            tablumpDatabaseAdapter.updatePost(posts[i].getTitulo().toString(),posts[i].getTitulo().toString(), posts[i].getDescripcion().toString(), posts[i].getCategory().toString(), newUser);
                        }
                    }
                    if(notificaciones!= null) {
                        for (int i = 0; i < notificaciones.length; i++) {
                            tablumpDatabaseAdapter.updateNotification(notificaciones[i].getTipo().toString(), notificaciones[i].getTitulo().toString(), newUser, notificaciones[i].getUsuarioRealiza().toString());
                        }
                    }
                    tablumpDatabaseAdapter.close();
                    Intent intent = new Intent(getBaseContext(), PerfilActivity.class);

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", newUser);
                    editor.commit();

                    NotificationManager notificationManager = (NotificationManager)
                            getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.deleteNotificationChannel(username);

                    //Sólo para la prueba
                    notificationManager.deleteNotificationChannel("Alba");
                    notificationManager.deleteNotificationChannel("Paco");
                    notificationManager.deleteNotificationChannel("Ramón");
                    notificationManager.deleteNotificationChannel("us");
                    notificationManager.deleteNotificationChannel("Roberto");
                    notificationManager.deleteNotificationChannel("Gonzalo");
                    //////////////////

                    intent.putExtra("username", user.getText().toString());
                    startActivity(intent);
                }else{
                    tablumpDatabaseAdapter.close();
                    Toast.makeText(getApplicationContext(), "Nombre de usuario ya escogido", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
