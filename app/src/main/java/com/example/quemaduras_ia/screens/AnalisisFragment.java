package com.example.quemaduras_ia.screens;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quemaduras_ia.R;

import java.io.IOException;

public class AnalisisFragment extends Fragment {
    private TextView prediccionTextView, additionalTextView, textViewLinks;
    private ImageView imagenImageView, icoImageView, icoVerImagen;
    private CardView cardImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analisis, container, false);

        prediccionTextView = view.findViewById(R.id.gradeTextView);
        icoImageView = view.findViewById(R.id.medidorGrado);
        icoVerImagen = view.findViewById(R.id.icon_ver_imagen);
        additionalTextView  = view.findViewById(R.id.additionalTextView);
        cardImg = view.findViewById(R.id.card_acordeon);
        imagenImageView = view.findViewById(R.id.userImage);
        textViewLinks = view.findViewById(R.id.textViewLinks);

        if (getArguments() != null) {
            String prediccion = getArguments().getString("prediccion");
            Uri imagenUri = getArguments().getParcelable("imagen");

            if (prediccion != null) {
                prediccionTextView.setText("Quemadura de " + prediccion);
                actualizarRecomendaciones(prediccion);
            }

            if (imagenUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagenUri);
                    imagenImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            cardImg.setOnClickListener(v -> alternarVisibilidad(imagenImageView));

            String enlaces = "• <a href='" + getString(R.string.enlace_oms) + "'>Organización Mundial de la Salud (OMS)</a><br>" +
                    "• <a href='" + getString(R.string.enlace_paraguay) + "'>Ministerio de Salud Pública y Bienestar Social de Paraguay</a><br>" +
                    "• <a href='" + getString(R.string.enlace_mayo) + "'>Mayo Clinic</a>";

            // Compatibilidad con diferentes versiones de Android
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textViewLinks.setText(Html.fromHtml(enlaces, Html.FROM_HTML_MODE_LEGACY));
            } else {
                textViewLinks.setText(Html.fromHtml(enlaces));
            }

            // Permitir que los enlaces se abran en un navegador
            textViewLinks.setMovementMethod(LinkMovementMethod.getInstance());
        }

        return view;
    }

    private void actualizarRecomendaciones(String prediccion) {
        switch (prediccion) {
            case "primer grado":
                additionalTextView.setText(getString(R.string.txt_recomendaciones_primer_grado));
                icoImageView.setImageResource(R.drawable.medidor_primer_grado);
                break;
            case "segundo grado":
                additionalTextView.setText(getString(R.string.txt_recomendaciones_segundo_grado));
                icoImageView.setImageResource(R.drawable.medidor_segundo_grado);
                break;
            case "tercer grado":
                additionalTextView.setText(getString(R.string.txt_recomendaciones_tercer_grado));
                icoImageView.setImageResource(R.drawable.medidor_tercer_grado);
                break;
            default:
                additionalTextView.setText(getString(R.string.txt_recomendaciones_indeterminadas));
                icoImageView.setImageResource(R.drawable.medidor_primer_grado);
        }
    }

    private void alternarVisibilidad(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }

        actualizarIconoVerImagen();
    }

    private void actualizarIconoVerImagen() {
        if (imagenImageView.getVisibility() == View.VISIBLE) {
            icoVerImagen.setImageResource(R.drawable.icon_ver_imagen_activo);
        } else {
            icoVerImagen.setImageResource(R.drawable.icon_ver_imagen_inactivo);
        }
    }
}
