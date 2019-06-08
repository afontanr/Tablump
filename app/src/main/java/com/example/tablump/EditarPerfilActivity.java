package com.example.tablump;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class EditarPerfilActivity extends AppCompatActivity {

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

                if(user.getText().toString().equals("") || pass.getText().toString().equals("") || mail.getText().toString().equals("")){
                    tablumpDatabaseAdapter.close();
                    Toast.makeText(getApplicationContext(), "No se han introducido todos los campos", Toast.LENGTH_LONG).show();
                }

                else if(tablumpDatabaseAdapter.getUser(user.getText().toString()) == null || user.getText().toString().equals(username)){
                    Like [] likes = tablumpDatabaseAdapter.getLikesFromUser(username);
                    Post [] posts = tablumpDatabaseAdapter.getPostsFromUser(username);
                    Notification [] notificaciones = tablumpDatabaseAdapter.getNotificationsFromUser(username);
                    for(int i = 0; i < likes.length;i++){
                        tablumpDatabaseAdapter.updateLike(likes[i].getTitulo(),user.toString());
                    }
                    for(int i = 0; i < posts.length;i++){
                        tablumpDatabaseAdapter.updatePost(posts[i].getTitulo(),posts[i].getDescripcion(),posts[i].getCategory(),user.toString());
                    }
                    for(int i = 0; i < notificaciones.length;i++){
                        tablumpDatabaseAdapter.updateNotification(notificaciones[i].getTipo(),notificaciones[i].getTitulo(),user.toString(),notificaciones[i].getUsuarioRealiza());
                    }
                    tablumpDatabaseAdapter.updateUser(user.toString(),pass.toString(),mail.toString());
                    tablumpDatabaseAdapter.close();
                    Intent intent = new Intent(getBaseContext(), PerfilActivity.class);

                    intent.putExtra("usuario", user.getText().toString());
                    startActivity(intent);
                }else{
                    tablumpDatabaseAdapter.close();
                    Toast.makeText(getApplicationContext(), "Nombre de usuario ya escogido", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
