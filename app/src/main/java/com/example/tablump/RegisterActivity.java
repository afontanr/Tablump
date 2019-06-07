package com.example.tablump;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button accept = findViewById(R.id.registrar_boton);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView user = findViewById(R.id.registro_usuario);
                TextView pass = findViewById(R.id.registro_contraseña);
                TextView mail = findViewById(R.id.registro_correo);

                Toast.makeText(getApplicationContext(), mail.getText().toString(), Toast.LENGTH_LONG).show();

                TablumpDatabaseAdapter tablumpDatabaseAdapter = new TablumpDatabaseAdapter(getApplicationContext());
                tablumpDatabaseAdapter.open();
                if( !user.getText().toString().equals("") && !pass.getText().toString().equals("") && !mail.getText().toString().equals("") && tablumpDatabaseAdapter.getUser(user.getText().toString()) == null){
                    tablumpDatabaseAdapter.insertUser(mail.getText().toString(),user.getText().toString(),pass.getText().toString());
                    tablumpDatabaseAdapter.close();
                    Intent intent = new Intent(getBaseContext(), PrincipalActivity.class);
                    intent.putExtra("usuario", user.getText().toString());
                    startActivity(intent);
                }else{
                    tablumpDatabaseAdapter.close();
                    Toast.makeText(getApplicationContext(), "Usuario Existente", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
