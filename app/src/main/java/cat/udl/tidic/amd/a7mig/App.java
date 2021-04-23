package cat.udl.tidic.amd.a7mig;
import android.app.Application;
import android.content.SharedPreferences;

import cat.udl.tidic.amd.a7mig.preferences.PreferenceProvider;

public class App extends Application {
        String TAG = "App";
        @Override
        public void onCreate() {
            super.onCreate();
            PreferenceProvider.init(this);
            comprobarIniciado();

        }

        public static float iniciado(){
            float banca = PreferenceProvider.providePreferences().getFloat("banca", -1);
            return banca;
        }

        public void comprobarIniciado(){
            if(iniciado() == -1){
                SharedPreferences.Editor editor = PreferenceProvider.providePreferences().edit().putFloat("banca", 30000);
                editor.commit();
            }
        }
}
