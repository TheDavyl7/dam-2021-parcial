package cat.udl.tidic.amd.a7mig.models;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import cat.udl.tidic.amd.a7mig.preferences.PreferenceProvider;


public class Jugador {

        private final String nombre;
        private final int apuesta;
        private double puntuacion;

    public Jugador(String nombre, Integer apuesta) {
        this.nombre = nombre;
        this.apuesta = apuesta;
        this.puntuacion = 0.0;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getApuesta() {
        return apuesta;
    }

    @NonNull
    @Override
    public String toString(){
        if (this.puntuacion == 7.5){
            float dinero = this.getApuesta()*2;
            Log.d("jugador: ","money "+dinero);
            float banca = PreferenceProvider.providePreferences().getFloat("banca", -1);
            banca = banca - dinero;
            SharedPreferences.Editor editor = PreferenceProvider.providePreferences().edit().putFloat("banca", banca);
            editor.commit();
            return this.nombre + " ha guanyat " + this.getApuesta()*2 + " euros amb una puntuació de " + this.getPuntuacion();
        } else if (this.puntuacion < 7.5){
            float dinero = (float) (this.getApuesta()*0.1);
            Log.d("jugador: ","money "+dinero);
            float banca = PreferenceProvider.providePreferences().getFloat("banca", -1);
            banca = banca + dinero;
            SharedPreferences.Editor editor = PreferenceProvider.providePreferences().edit().putFloat("banca", banca);
            editor.commit();
            return this.nombre + " ha perdut " + this.getApuesta()*0.1 + " euros amb una puntuació de " + this.getPuntuacion();
        } else {
            float dinero = (float) (this.getApuesta());
            Log.d("jugador: ","money "+dinero);
            float banca = PreferenceProvider.providePreferences().getFloat("banca", -1);
            Log.d("banca: ","money "+banca);
            banca = banca + dinero;
            Log.d("banca final: ","money "+banca);
            SharedPreferences.Editor editor = PreferenceProvider.providePreferences().edit().putFloat("banca", banca);
            editor.commit();
            return this.nombre + " ha perdut " + this.getApuesta() + " euros amb una puntuació de " + this.getPuntuacion();
        }
    }

}
