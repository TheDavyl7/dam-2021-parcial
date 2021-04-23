package cat.udl.tidic.amd.a7mig;
import android.app.Application;

import cat.udl.tidic.amd.a7mig.preferences.PreferenceProvider;

public class App extends Application {
        String TAG = "App";
        @Override
        public void onCreate() {
            super.onCreate();
            PreferenceProvider.init(this);
            if(PreferenceProvider.getBanca() == -1){
                PreferenceProvider.providePreferences.putInt(getString(R.string.banca), 30000);
            }
        }
}
