package com.example.tablump;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListNotificaciones extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] tipo;
    private final String[] titulo;
    private final String[] usuarioRecibe;
    private final String[] usuarioRealiza;

    public CustomListNotificaciones(Activity context,
                                    String[] tipo, String[] titulo, String[] usuarioRecibe, String[] usuarioRealiza) {
        super(context, R.layout.list_single, titulo);
        this.context = context;
        this.tipo = tipo;
        this.titulo = titulo;
        this.usuarioRecibe= usuarioRecibe;
        this.usuarioRealiza = usuarioRealiza;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single_notificaciones, null, true);

        TextView txtTitulo = (TextView) rowView.findViewById(R.id.txt_titulo);

        if (tipo[position].equals("like")){
            txtTitulo.setText("El usuario " + usuarioRealiza[position] + " ha dado like a tu post: '" + titulo[position] + "'");
        }
        else{
            txtTitulo.setText("El usuario " + usuarioRealiza[position] + " ha comentado en tu post: '" + titulo[position] + "'");
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PostActivity.class);

                intent.putExtra("titulo", titulo[position]);
                context.startActivity(intent);
            }
        });
        return rowView;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}