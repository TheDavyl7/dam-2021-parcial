package cat.udl.tidic.amd.a7mig;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import Validators.AccountValidator;
import cat.udl.tidic.amd.a7mig.models.Jugador;
import cat.udl.tidic.amd.a7mig.models.Partida;


public class GameBeginDialog extends DialogFragment {

    private static final String TAG = "GameBeginDialog";
    private LinearLayout gameSettingLayout;
    private EditText players;
    private int jugadores;
    private View rootView;
    private GameActivity activity;
    public List<Jugador> jugadors;

    public static GameBeginDialog newInstance(GameActivity activity) {
        GameBeginDialog dialog = new GameBeginDialog();
        dialog.activity = activity;
        return dialog;
    }

    private boolean isNameValid = false;
    private boolean isApostaValid = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle(R.string.game_dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.start, null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(dialog -> {
            onDialogShow(alertDialog);
        });
        return alertDialog;
    }

    @SuppressLint("InflateParams")
    private void initViews() {

        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.game_begin_dialog, null, false);

        gameSettingLayout = rootView.findViewById(R.id.gameEndLayout);
        players = rootView.findViewById(R.id.numeroJugadorsET);


        addTextWatchers();
    }

    private void onDialogShow(AlertDialog dialog) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {
            onDoneClicked();
        });
    }
    
    private void onDoneClicked() {
        List<String> noms = new ArrayList<>();
        List<Integer> apostes = new ArrayList<>();
        int contador = 0;
        int i;
        for (i = 0; i < jugadores; i++) {
            EditText editText = gameSettingLayout.findViewById(20000+i);
            String value = editText.getText().toString();
            String nom = value.split(";")[0];
            String aposta = value.split(";")[1];
            comprobacion(value);

            if(isNameValid && isApostaValid){
                noms.add(i,nom);
                apostes.add(i,Integer.parseInt(aposta));
                Log.d(TAG, "noms:"+ noms.toString());
                Log.d(TAG, "apostes:"+ apostes.toString());
                contador++;
            }
            else{
                editText.setError("Error, algo no esta bien escrito");
            }

        }
        if(contador == i) {
            List<Jugador> jugadors = new ArrayList<>();
            Jugador jugador = new Jugador(noms.get(0).toString(), Integer.parseInt(apostes.get(0).toString()));
            jugadors.add(jugador);
            activity.comienzoJuego(noms, apostes, jugador, jugadors);
            dismiss();
        }
    }

    private void comprobacion(String value){
        String[] partes = value.split(";");
        String nombre = partes[0];
        String dinero = partes[1];
        isNameValid = AccountValidator.check_nom(nombre.toString());
        isApostaValid = AccountValidator.check_aposta(dinero);
    }



    private void addTextWatchers() {
        players.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    jugadores = Integer.parseInt(s.toString());
                    for (int i = 0; i < jugadores; i++) {

                        EditText nomET = new EditText(rootView.getContext());
                        nomET.setHint(R.string.player_hint);
                        nomET.setId(20000+i);
                        gameSettingLayout.addView(nomET);
                    }
                }
                catch(Exception e) {
                    gameSettingLayout.removeAllViews();
                    gameSettingLayout.addView(players);
                }
            }
        });



}
}
