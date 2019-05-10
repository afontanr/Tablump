package com.example.tablump;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;

    private final String[] titulo;
    private final String[] descripcion;
    public CustomList(Activity context,
                      String[] titulo, String[] descripcion) {
        super(context, R.layout.list_single, titulo);
        this.context = context;
        this.titulo = titulo;
        this.descripcion = descripcion;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        TextView txtTitulo = (TextView) rowView.findViewById(R.id.txt_titulo);
        TextView txtDescripcion = (TextView) rowView.findViewById(R.id.txt_descripcion);

        txtTitulo.setText(titulo[position]);
        txtDescripcion.setText(descripcion[position]);
        return rowView;
    }
}