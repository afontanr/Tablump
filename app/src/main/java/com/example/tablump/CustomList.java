package com.example.tablump;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;

    private final String[] titulo;
    private final String[] descripcion;
    private final Boolean[] isLiked;
    private final String username;
    private final TablumpDatabaseAdapter tablumpDatabaseAdapter;

    public CustomList(Activity context,
                      String[] titulo, String[] descripcion, Boolean[] isLiked, String username) {
        super(context, R.layout.list_single, titulo);
        this.context = context;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.isLiked = isLiked;

        this.username = username;
        this.tablumpDatabaseAdapter = new TablumpDatabaseAdapter(context);
        tablumpDatabaseAdapter.open();

    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        TextView txtTitulo = (TextView) rowView.findViewById(R.id.txt_titulo);
        TextView txtDescripcion = (TextView) rowView.findViewById(R.id.txt_descripcion);

        txtTitulo.setText(titulo[position]);
        txtDescripcion.setText(descripcion[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Pulsado en el post " + titulo[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), PostActivity.class);

                intent.putExtra("titulo", titulo[position]);
                context.startActivity(intent);
            }
        });

        final ImageButton likeButton = (ImageButton) rowView.findViewById(R.id.like);

        if (isLiked[position]){
            likeButton.setImageResource(context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + "like_lleno", null, null));
        }
        else{
            likeButton.setImageResource(context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + "like_vacio", null, null));
        }

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLiked[position]){
                    //Toast.makeText(getContext(), "Pulsado en el like", Toast.LENGTH_SHORT).show();

                    tablumpDatabaseAdapter.deleteLike(titulo[position], username);
                    likeButton.setImageResource(context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + "like_vacio", null, null));
                    isLiked[position] = false;
                }
                else{

                    //Se notifica al usuario
                    tablumpDatabaseAdapter.insertNotification("like",titulo[position],tablumpDatabaseAdapter.getPost(titulo[position]).getUsuario(),username);

                    ////////////////////////////////////////////////////////////////////////////


                    // Notificación de like
                    String title = titulo[position];
                    Intent intent = new Intent(context, PostActivity.class);
                    intent.putExtra("titulo", title);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, tablumpDatabaseAdapter.getPost(titulo[position]).getUsuario())
                            .setSmallIcon(R.drawable.sobre)
                            .setContentTitle("Un usuario ha dado like a tu post:")
                            .setContentText(title)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            // Set the intent that will fire when the user taps the notification
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

                    // notificationId is a unique int for each notification that you must define
                    int min = 1;
                    int max = 10000;

                    Random r = new Random();
                    int i1 = r.nextInt(max - min + 1) + min;
                    notificationManagerCompat.notify(1, builder.build());

                    ////////////////////////////////////////////////////////////////////////////

                    tablumpDatabaseAdapter.insertLike(titulo[position], username);
                    likeButton.setImageResource(context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + "like_lleno", null, null));
                    isLiked[position] = true;
                }

            }
        });


        return rowView;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }
}