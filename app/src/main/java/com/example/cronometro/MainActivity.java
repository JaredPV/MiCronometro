package com.example.cronometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tv_tiempo;
    private Button btn_inicar_parar, btn_reiniciar_lap;
    private ListView lv_laps;
    private int milisegundos = 0, segundos = 0, minutos = 0, ms;
    private int lap = 1;
    private boolean corriendo=false, start_stop=true;
    private ArrayList<String> al_laps;
    private ArrayAdapter<String> aa_laps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_tiempo = findViewById(R.id.tv_tiempo);
        btn_inicar_parar = findViewById(R.id.btn_iniciar_parar);
        btn_inicar_parar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_start,0,0,0);
        btn_reiniciar_lap = findViewById(R.id.btn_reiniciar_lap);
        btn_reiniciar_lap.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_restart,0,0,0);
        lv_laps = findViewById(R.id.lv_laps);

        al_laps = new ArrayList<>();
        aa_laps = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, al_laps);

        lv_laps.setAdapter(aa_laps);

        iniciarTiempo();
        btn_reiniciar_lap.setEnabled(false);
        btn_inicar_parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(start_stop){
                    corriendo = true;
                    tv_tiempo.setTextColor(getColor(R.color.black));
                    btn_reiniciar_lap.setEnabled(true);
                    btn_inicar_parar.setText("Detener");
                    btn_inicar_parar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stop,0,0,0);
                    btn_inicar_parar.invalidate();
                    btn_reiniciar_lap.setText("Lap");
                    btn_reiniciar_lap.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_flag,0,0,0);
                    btn_reiniciar_lap.invalidate();
                    start_stop=false;
                }else{
                    corriendo = false;
                    tv_tiempo.setTextColor(getColor(R.color.red_700));
                    btn_inicar_parar.setText("Iniciar");
                    btn_inicar_parar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_start,0,0,0);
                    btn_reiniciar_lap.setText("Reiniciar");
                    btn_reiniciar_lap.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_restart,0,0,0);
                    start_stop=true;
                }
            }
        });
        btn_reiniciar_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_reiniciar_lap.getText().toString().equals("Reiniciar")){
                    tv_tiempo.setTextColor(getColor(R.color.gray));
                    btn_reiniciar_lap.setEnabled(false);
                    milisegundos=0;
                    segundos=0;
                    minutos=0;
                    lap = 1;
                    aa_laps.clear();
                }else{

                    String tiempo = String.format(Locale.getDefault(), "%02d:%02d:%02d",minutos,segundos,ms);
                    if (lap==1){
                        aa_laps.add("Lap\t\t\tTiempo");
                    }
                    aa_laps.add(String.valueOf(lap)+"\t\t\t\t\t"+tiempo);
                    lap++;

                }
            }
        });


    }

    public void iniciarTiempo(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ms = milisegundos%100;


                if(corriendo){
                    milisegundos++;
                    if (ms==0 && milisegundos!=1 ){
                        segundos++;
                    }
                    if(segundos==60){
                        minutos++;
                        segundos=0;
                    }
                }
                String formato = String.format(Locale.getDefault(), "%02d:%02d:%02d",minutos,segundos,ms);
                tv_tiempo.setText(formato);
                handler.postDelayed(this,0);
            }
        };
        handler.post(runnable);
    }
}