package com.example.tablump;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CustomListComentarios extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] user;
    private final String[] comment;
    private final TablumpDatabaseAdapter tablumpDatabaseAdapter;


    public CustomListComentarios(Activity context, String[] user, String[] comment) {
        super(context, R.layout.list_comment, user);
        this.context = context;
        this.user = user;
        this.comment= comment;
        this.tablumpDatabaseAdapter = new TablumpDatabaseAdapter(context);
        tablumpDatabaseAdapter.open();

    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_comment, null, true);

        TextView txtUsuario = (TextView) rowView.findViewById(R.id.txt_titulo);
        TextView txtComment = (TextView) rowView.findViewById(R.id.txt_descripcion);

        txtUsuario.setText(user[position]);
        txtComment.setText(comment[position]);

        return rowView;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }


}
