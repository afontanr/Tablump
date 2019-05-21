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
import android.widget.TextView;

public class NotificacionesActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    item.setCheckable(true);
                    Intent intent = new Intent(getBaseContext(), PrincipalActivity.class);
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


        ////////////////////////////////////////////////////////////////////////////
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        final String channelId = "id1";
        final CharSequence channelName = "name1";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId, channelName,
                    importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400,
                    500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }


        // Notificación de like
        String title = "UN POST CONCRETO";
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra("titulo", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id1")
                .setSmallIcon(R.drawable.sobre)
                .setContentTitle("Un usuario ha dado like a tu post")
                .setContentText(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManagerCompat.notify(1, builder.build());

        // Notificación de mensaje
        title = "UN POST CONCRETO";
        intent = new Intent(this, PostActivity.class);
        intent.putExtra("titulo", title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder = new NotificationCompat.Builder(this, "id1")
                .setSmallIcon(R.drawable.sobre)
                .setContentTitle("Un usuario ha comentado en tu post")
                .setContentText(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManagerCompat.notify(2, builder.build());

        ////////////////////////////////////////////////////////////////////////////


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setCheckable(false);
    }

}
