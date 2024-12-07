package com.example.quemaduras_ia.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quemaduras_ia.R;

public class DetalleInformacionFragment extends Fragment {

    // Variables
    private static final String ARG_INFO_ID = "infoId";
    private ImageView detailImage;
    private TextView detailTitle, detailDescription;

    public static DetalleInformacionFragment newInstance(String infoId) {
        DetalleInformacionFragment fragment = new DetalleInformacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INFO_ID, infoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_informacion, container, false);

        detailImage = view.findViewById(R.id.detail_image);
        detailTitle = view.findViewById(R.id.detail_title);
        detailDescription = view.findViewById(R.id.detail_description);

        // Para verificar Argumentos
        if (getArguments() != null) {
            // Recupera el valor de "infoId" de los argumentos
            String infoId = getArguments().getString(ARG_INFO_ID);

            if ("info1".equals(infoId)) {
                loadImageWithGlide(R.drawable.info1, detailImage);
                detailTitle.setText(R.string.txt_title_info1);
                detailDescription.setText(R.string.txt_description1_detalleinfo);
            } else if ("info2".equals(infoId)) {
                loadImageWithGlide(R.drawable.info2, detailImage);
                detailTitle.setText(R.string.txt_title_info2);
                detailDescription.setText(R.string.txt_description2_detalleinfo);
            } else if ("info3".equals(infoId)) {
                loadImageWithGlide(R.drawable.info3, detailImage);
                detailTitle.setText(R.string.txt_title_info3);
                detailDescription.setText(R.string.txt_description3_detalleinfo);
            } else if ("info4".equals(infoId)) {
                loadImageWithGlide(R.drawable.info4, detailImage);
                detailTitle.setText(R.string.txt_title_info4);
                detailDescription.setText(R.string.txt_description4_detalleinfo);
            }
        }

        return view;
    }

    // Método para cargar imágenes con Glide
    private void loadImageWithGlide(int resourceId, ImageView imageView) {
        Glide.with(this)
                .load(resourceId)          // Carga el recurso drawable
                .override(600, 400)        // Redimensiona a 600x400 píxeles
                .centerCrop()              // Escala para llenar el ImageView
                .into(imageView);          // Establece la imagen en el ImageView
    }
}