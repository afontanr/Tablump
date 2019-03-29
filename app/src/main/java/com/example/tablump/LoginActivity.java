package com.example.tablump;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button aux = findViewById(R.id.sign_in_button);
        aux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //login()

                Intent intent = new Intent(getBaseContext(), PrincipalActivity.class);
                intent.putExtra("usuario", username);
                startActivity(intent);
            }
        });
    }
}
