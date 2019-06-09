package com.example.tablump;

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
                TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());
                tablumpDatabaseAdapter.open();
                sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                username = sp.getString("username","");

                if(user.getText().toString().equals("") || pass.getText().toString().equals("") || mail.getText().toString().equals("")){
                    tablumpDatabaseAdapter.close();
                    Toast.makeText(getApplicationContext(), "No se han introducido todos los campos", Toast.LENGTH_LONG).show();
                }

                else if(tablumpDatabaseAdapter.getUser(user.getText().toString()) == null || user.getText().toString().equals(username)){
                    Like [] likes = tablumpDatabaseAdapter.getLikesFromUser(username);
                    Post [] posts = tablumpDatabaseAdapter.getPostsFromUser(username);
                    Notification [] notificaciones = tablumpDatabaseAdapter.getNotificationsFromUser(username);
                    tablumpDatabaseAdapter.deleteUser(username);
                    tablumpDatabaseAdapter.insertUser(mail.getText().toString(),user.getText().toString(),pass.getText().toString());
                    //tablumpDatabaseAdapter.updateUser(user.getText().toString(),pass.getText().toString(),mail.getText().toString());
                    if(likes!= null) {
                        for (int i = 0; i < likes.length; i++) {
                            tablumpDatabaseAdapter.updateLike(likes[i].getTitulo().toString(), user.getText().toString());
                        }
                    }
                    if(posts!= null) {
                        for (int i = 0; i < posts.length; i++) {
                            tablumpDatabaseAdapter.updatePost(posts[i].getTitulo().toString(),posts[i].getTitulo().toString(), posts[i].getDescripcion().toString(), posts[i].getCategory().toString(), user.getText().toString());
                        }
                    }
                    if(notificaciones!= null) {
                        for (int i = 0; i < notificaciones.length; i++) {
                            tablumpDatabaseAdapter.updateNotification(notificaciones[i].getTipo().toString(), notificaciones[i].getTitulo().toString(), user.getText().toString(), notificaciones[i].getUsuarioRealiza().toString());
                        }
                    }
                    tablumpDatabaseAdapter.close();
                    Intent intent = new Intent(getBaseContext(), PerfilActivity.class);

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", user.getText().toString());
                    editor.commit();

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
