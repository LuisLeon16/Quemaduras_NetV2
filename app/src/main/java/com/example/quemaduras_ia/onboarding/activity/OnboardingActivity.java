package com.example.quemaduras_ia.onboarding.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quemaduras_ia.MainActivity;
import com.example.quemaduras_ia.R;
import com.example.quemaduras_ia.onboarding.utils.OnboardingSaveState;
import com.example.quemaduras_ia.onboarding.adapter.OnboardingAdapter;

public class OnboardingActivity extends AppCompatActivity {

    //Variables
    private androidx.viewpager.widget.ViewPager viewPager;
    private CardView next;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private OnboardingSaveState onboardingSaveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Maneja la excepción si el hilo es interrumpido
            e.printStackTrace();
        }

        //Se establece un tema
        setTheme(R.style.Theme_Quemaduras_IA);
        //Pantalla completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPager);
        next = findViewById(R.id.nextCard);
        dotsLayout = findViewById(R.id.dotsLayout);
        onboardingSaveState = new OnboardingSaveState(this,"ob");

        //Comprueba el estado guardado
        if (onboardingSaveState.getState() == 1) {
            startActivity(new Intent(OnboardingActivity.this, MainActivity.class));
            finish();
        }

        //Se Configura el ViewPager con un adaptador
        OnboardingAdapter adapter = new OnboardingAdapter(this);
        viewPager.setAdapter(adapter);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1, true);
            }
        });
        viewPager.addOnPageChangeListener(new androidx.viewpager.widget.ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotsFunction(position);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (position < 2) {
                            viewPager.setCurrentItem(position + 1, true);
                        }else{
                            onboardingSaveState.setState(1);
                            startActivity(new Intent(OnboardingActivity.this, WelcomeActivity.class));
                            finish();
                        }
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Inicializar los puntos indicadores (barra de estado)
        dotsFunction(0);

    }

    private void dotsFunction(int pos) {
        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i = 0 ; i< dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("-"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i].setTextColor(getColor(R.color.white));
            }
            dots[i].setTextSize(40);

            dotsLayout.addView(dots[i]);

        }

        if (dots.length > 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[pos].setTextColor(getColor(R.color.naranja_pastel));
            }
            dots[pos].setTextSize(40);
        }
    }
}