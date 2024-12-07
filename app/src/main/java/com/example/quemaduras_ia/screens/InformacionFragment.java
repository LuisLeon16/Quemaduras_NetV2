package com.example.quemaduras_ia.screens;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.quemaduras_ia.R;

public class InformacionFragment extends Fragment {
    // Variables
    private ImageView img1, img2, img3, img4;
    private CardView card1, card2, card3, card4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informacion, container, false);

        img1 = view.findViewById(R.id.img_info1);
        img2 = view.findViewById(R.id.img_info2);
        img3 = view.findViewById(R.id.img_info3);
        img4 = view.findViewById(R.id.img_info4);

        card1 = view.findViewById(R.id.card1_info);
        card2 = view.findViewById(R.id.card2_info);
        card3 = view.findViewById(R.id.card3_info);
        card4 = view.findViewById(R.id.card4_info);

        // Cargar imágenes con Glide
        loadImageWithGlide(R.drawable.info1, img1);
        loadImageWithGlide(R.drawable.info2, img2);
        loadImageWithGlide(R.drawable.info3, img3);
        loadImageWithGlide(R.drawable.info4, img4);

        // Añade oyentes de clic para cada CardView
        card1.setOnClickListener(v -> navigateToDetail("info1"));
        card2.setOnClickListener(v -> navigateToDetail("info2"));
        card3.setOnClickListener(v -> navigateToDetail("info3"));
        card4.setOnClickListener(v -> navigateToDetail("info4"));

        return view;
    }

    private void navigateToDetail(String infoId) {
        // Obtiene el NavController para manejar la navegación
        NavController navController = Navigation.findNavController(requireActivity(), R.id.contenedorFragments);
        // Se crea un Bundle
        Bundle args = new Bundle();
        // Añade el ID de información
        args.putString("infoId", infoId);
        // Navegar al fragmento de detalles, pasando los argumentos
        navController.navigate(R.id.action_informacionFragment_to_detalleInformacionFragment, args);
    }

    private void loadImageWithGlide(int resourceId, ImageView imageView) {
        Glide.with(this)
                .load(resourceId)          // ID del recurso drawable
                .override(600, 400)        // Redimensiona la imagen
                .centerCrop()              // Escala la imagen para llenar el ImageView
                .into(imageView);          // Establece la imagen en el ImageView
    }
}
