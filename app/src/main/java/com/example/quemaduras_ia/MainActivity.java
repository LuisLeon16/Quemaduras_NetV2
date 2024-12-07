package com.example.quemaduras_ia;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //Variables
    private LinearLayout layout1, layout2;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout1 = findViewById(R.id.layout_home);
        layout2 = findViewById(R.id.layout_screen);
        toolbarTitle = findViewById(R.id.toolbar_title);

        //NavController asociado con el contenedor de fragmentos
        NavController navController = Navigation.findNavController(this, R.id.contenedorFragments);
        //Vista de navegación inferior
        BottomNavigationView bottom_nav_view = findViewById(R.id.bottom_navigation);
        //Configura el BottomNavigationView para trabajar con el NavController
        NavigationUI.setupWithNavController(bottom_nav_view, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.contactosFragment) {
                toolbarTitle.setText("Contactos de \nEmergencia");
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.informacionFragment || destination.getId() == R.id.detalleInformacionFragment) {
                bottom_nav_view.getMenu().findItem(R.id.informacionFragment).setChecked(true);

                toolbarTitle.setText("Artículos \nInformativos");
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.analisisFragment) {
                bottom_nav_view.getMenu().findItem(R.id.inicioFragment).setChecked(true);

                toolbarTitle.setText("Análisis");
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
            } else {
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
            }
        });
    }
}
