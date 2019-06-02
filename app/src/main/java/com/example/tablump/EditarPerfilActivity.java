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
                if(tablumpDatabaseAdapter.getUser(user.toString()) == null || user.toString().equals(username)){
                    tablumpDatabaseAdapter.deleteUser(username);
                    tablumpDatabaseAdapter.insertUser(mail.toString(),user.toString(),pass.toString());
                    tablumpDatabaseAdapter.close();
                    Intent intent = new Intent(getBaseContext(), PerfilActivity.class);
                    username = user.toString();
                    intent.putExtra("usuario", username);
                    startActivity(intent);
                }else{
                    tablumpDatabaseAdapter.close();
                    Toast.makeText(getApplicationContext(), "Nombre de usuario ya escogido", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
