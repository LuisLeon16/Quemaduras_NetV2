package com.example.quemaduras_ia.onboarding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.quemaduras_ia.R;

public class OnboardingAdapter extends PagerAdapter {

    //Variables
    private Context context;
    private LayoutInflater layoutInflater;

    //Constructor
    public OnboardingAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    //Arrays para Títulos, Descripciones e Imágenes
    private int titles[] = {
            R.string.txt_title1,
            R.string.txt_title2,
            R.string.txt_title3
    };

    private int descriptions[] = {
            R.string.txt_description1,
            R.string.txt_description2,
            R.string.txt_description3
    };

    private int images[] ={
            R.drawable.ob1,
            R.drawable.ob2,
            R.drawable.ob3
    };

    //Métodos Requeridos por el Adaptador
    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }

    //Creación de las Páginas del Onboarding
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = layoutInflater.inflate(R.layout.activity_onboarding_slider,container,false);

        ImageView image = v.findViewById(R.id.image_onboarding);
        TextView title,description;
        title = v.findViewById(R.id.title_onboarding);
        description = v.findViewById(R.id.description_onboarding);

        image.setImageResource(images[position]);
        title.setText(titles[position]);
        description.setText(descriptions[position]);

        container.addView(v);
        return v;
    }
}
