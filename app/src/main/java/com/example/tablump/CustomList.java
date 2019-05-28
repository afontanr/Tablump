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

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;

    private final String[] titulo;
    private final String[] descripcion;
    private final Boolean[] isLiked;

    public CustomList(Activity context,
                      String[] titulo, String[] descripcion, Boolean[] isLiked) {
        super(context, R.layout.list_single, titulo);
        this.context = context;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.isLiked = isLiked;

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
                    likeButton.setImageResource(context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + "like_vacio", null, null));
                    isLiked[position] = false;
                }
                else{
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