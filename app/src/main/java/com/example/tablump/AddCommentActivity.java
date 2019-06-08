package com.example.tablump;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class AddCommentActivity extends AppCompatActivity {

    private String title;
    private String username;
    private String userPost;
    private TablumpDatabaseAdapter tablumpDatabaseAdapter;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        tablumpDatabaseAdapter=new TablumpDatabaseAdapter(getApplicationContext());
        username = sp.getString("username","");

        Intent intent = getIntent();

        title = intent.getStringExtra("title");


        Button comentar = findViewById(R.id.button_comentar);
        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView comentarioView = findViewById(R.id.comentario);
                String comentario = comentarioView.getText().toString();
                try {

                    tablumpDatabaseAdapter.open();
                    userPost = tablumpDatabaseAdapter.getPost(title).getUsuario();
                    tablumpDatabaseAdapter.insertComment(title,username,comentario);
                    tablumpDatabaseAdapter.insertNotification("comment",title,userPost,username);
                    tablumpDatabaseAdapter.close();

                    Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                    intent.putExtra("titulo", title);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);


                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), userPost)
                            .setSmallIcon(R.drawable.sobre)
                            .setContentTitle(username+" ha comentado en tu post:")
                            .setContentText(title)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            // Set the intent that will fire when the user taps the notification
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

                    int min = 1;
                    int max = 10000;

                    Random r = new Random();
                    int i1 = r.nextInt(max - min + 1) + min;
                    notificationManagerCompat.notify(i1, builder.build());



                    onBackPressed();

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Usuario no " +
                            "existente", Toast.LENGTH_LONG).show();
                }


            }
        });

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




}
