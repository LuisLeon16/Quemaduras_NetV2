package com.example.quemaduras_ia.onboarding.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class OnboardingSaveState {

    //Variables
    private Context context;
    private String saveName;
    private SharedPreferences sp;

    //Constructor
    public OnboardingSaveState(Context context, String saveName) {
        this.context = context;
        this.saveName = saveName;
        sp = context.getSharedPreferences(saveName,context.MODE_PRIVATE);
    }

    //Guarda el valor entero "key"
    public void setState(int key){
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("key",key);
        //Guarda los cambios de manera asíncrona (no ocurre en el mismo momento)
        editor.apply();
    }

    //Recupera el valor entero almacenado bajo la clave "key"
    public int getState(){
        return sp.getInt("key",0);
    }
}
