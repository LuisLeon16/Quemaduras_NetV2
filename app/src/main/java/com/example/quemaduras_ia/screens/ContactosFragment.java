package com.example.quemaduras_ia.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quemaduras_ia.R;

public class ContactosFragment extends Fragment {

    //Variables
    TextView phoneTextView;

    public ContactosFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_contactos, container, false);

        phoneTextView = vista.findViewById(R.id.txt_phone_contactos_4);
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se crea un Intent que abre la aplicación de teléfono
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:132"));
                //Inicia la actividad de marcación
                startActivity(intent);
            }
        });

        return vista;
    }
}