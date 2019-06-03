package com.example.tablump;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {

    private TablumpDatabaseAdapter tablumpDatabaseAdapter;


    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Se reinicia la base de datos y se meten los posts y usuarios iniciales
        getBaseContext().deleteDatabase("database.db");
        tablumpDatabaseAdapter=new TablumpDatabaseAdapter(getApplicationContext());
        tablumpDatabaseAdapter.open();
        tablumpDatabaseAdapter.insertUser("mail","us", "con");
        tablumpDatabaseAdapter.insertPost("Fiesta de las paellas", "Este viernes se celebra la fiesta de las paellas","anuncios", "us");
        tablumpDatabaseAdapter.insertPost("Mochila perdida", "Se ha encontrado una mochila sin mochilero que la cargue en el bloque 3","objetos perdidos", "Paco");
        tablumpDatabaseAdapter.insertPost("Fin de las clases", "El último día de clases será el 27 de mayo","anuncios", "Alba");
        tablumpDatabaseAdapter.insertPost("¿Dónde está el aula virtual?", "Acabo de llegar a la escuela, ¿dónde puedo encontrar el aula virtual?","dudas", "Roberto");
        tablumpDatabaseAdapter.insertPost("Jornadas nocturnas", "Mañana comienzan las jornadas nocturnas","anuncios", "Alba");
        tablumpDatabaseAdapter.insertPost("He perdido un reloj", "He perdido un reloj de pulsera y ahora no sé qué hora es, ¿alguien lo ha visto?","objetos perdidos", "Gonzalo");
        tablumpDatabaseAdapter.insertPost("¿Cuándo terminan las clases?", "¿Terminan esta semana o la que viene?","dudas", "Paco");

        /*tablumpDatabaseAdapter.insertNotification("like", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("comment", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("like", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("like", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("like", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("comment", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("like", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("like", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("comment", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("comment", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("comment", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("like", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("comment", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("like", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");
        tablumpDatabaseAdapter.insertNotification("comment", "Este viernes se celebra la fiesta de las paellas", "no", "Paco");*/

        tablumpDatabaseAdapter.insertLike("Mochila perdida", "us");


        try {
            //Toast.makeText(getApplicationContext(), tablumpDatabaseAdapter.getUser("no").getEmail(), Toast.LENGTH_LONG).show();
        }
        catch(Exception e) {
            Log.d("E","Usuario no existente");
        }

        tablumpDatabaseAdapter.close();

        Button signin = findViewById(R.id.sign_in_button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //login()
                TextView user = findViewById(R.id.usuario);
                TextView password = findViewById(R.id.password);

                try {
                    tablumpDatabaseAdapter.open();
                    if (tablumpDatabaseAdapter.getUser(user.getText().toString()).getPassword().equals(password.getText().toString())) {
                        tablumpDatabaseAdapter.close();
                        username = user.getText().toString();
                        Intent intent = new Intent(getBaseContext(), PrincipalActivity.class);
                        intent.putExtra("usuario", username);
                        startActivity(intent);
                    } else {
                        tablumpDatabaseAdapter.close();
                        Toast.makeText(getApplicationContext(), "Error al hacer login", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Usuario no existente", Toast.LENGTH_LONG).show();
                }


            }
        });

        Button signup = findViewById(R.id.sign_up_button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

