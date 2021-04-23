package cat.udl.tidic.amd.a7mig;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cat.udl.tidic.amd.a7mig.models.Carta;
import cat.udl.tidic.amd.a7mig.models.Jugador;
import cat.udl.tidic.amd.a7mig.models.Partida;


public class GameActivity extends AppCompatActivity {

    private static final String TAG = "size";
    private TextView nombreView;
    private TextView apuestaView;
    private TextView puntosView;

    private Button plantarseButton;
    private Button seguirButton;

    private ImageView cartaFoto;

    private static final String GAME_BEGIN_DIALOG_TAG = "game_dialog_tag";
    private static final String GAME_END_DIALOG_TAG = "game_end_dialog_tag";

    private double puntuacion = 0;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        nombreView = findViewById(R.id.textView);
        apuestaView = findViewById(R.id.textView2);
        puntosView = findViewById(R.id.textView3);

        plantarseButton = findViewById(R.id.button2);
        seguirButton = findViewById(R.id.seguirButton);

        cartaFoto = findViewById(R.id.UltimaCarta);
    }

    public void initView(){
        promptForPlayer();
    }

    private void promptForPlayer() {
        GameBeginDialog dialog = GameBeginDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), GAME_BEGIN_DIALOG_TAG);
    }

    private void finalPartida(List p){
        GameEndDialog dialog = GameEndDialog.newInstance(this,
                p);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), GAME_END_DIALOG_TAG);
    }

    public void comienzoJuego(List nombres, List apuestas, Jugador player, List players){

        Partida partida = new Partida();
        Carta carta = partida.cogerCarta();
        cartaFoto.setImageResource(carta.getResource());

        nombreView.setText(nombres.get(index).toString());
        apuestaView.setText(apuestas.get(index).toString());

        puntuacion += carta.getValue();
        player.setPuntuacion(puntuacion);
        puntosView.setText("" + puntuacion);

        if(puntuacion > 7.5){
            plantarseButton.setEnabled(false);
            seguirButton.setEnabled(false);
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    if(index<nombres.size()-1) {
                        Log.d(TAG, "Name: " + nombres.size());
                        index++;
                        puntuacion = 0;
                        Jugador jugador = new Jugador(nombres.get(index).toString(), Integer.parseInt(apuestas.get(index).toString()));
                        players.add(jugador);
                        plantarseButton.setEnabled(true);
                        seguirButton.setEnabled(true);
                        comienzoJuego(nombres, apuestas, jugador, players);
                    }
                    else {
                        plantarseButton.setEnabled(true);
                        seguirButton.setEnabled(true);
                        puntuacion = 0;
                        finalPartida(players);
                    }
                }
            }, 2000);

        }

        plantarseButton.setOnClickListener(v -> {
            if(index<nombres.size()-1) {
                index++;
                puntuacion = 0;
                Jugador jugador = new Jugador(nombres.get(index).toString(), Integer.parseInt(apuestas.get(index).toString()));
                players.add(jugador);
                comienzoJuego(nombres, apuestas, jugador, players);
            }
            else {
                puntuacion = 0;
                finalPartida(players);
            }
        });

        seguirButton.setOnClickListener(v -> {
            comienzoJuego(nombres,apuestas,player,players);
        });

    }

}