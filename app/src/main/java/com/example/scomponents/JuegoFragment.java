package com.example.scomponents;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuegoFragment extends Fragment implements SensorEventListener {

    String[] palabras = {"Conducir","Hablar","Caminar","Reir","Sonreir","Pensar"};
    int x = 0;
    FrameLayout frameLayout;
    SensorManager manager;
    Sensor sensor;
    TextView mostrarPalabra, p1,p2;
    int c_jugador1 = 0, c_jugador2 = 0;

    public JuegoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_juego, container, false);

        frameLayout = (FrameLayout)view.findViewById(R.id.frame_juego);
        manager = (SensorManager)this.getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        mostrarPalabra = view.findViewById(R.id.muestra);
        p1 = view.findViewById(R.id.p1);
        p2 = view.findViewById(R.id.p2);
        mostrarPalabra.setText(palabras[0]);
        p1.setText("");
        p2.setText("");
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String texto = String.valueOf(event.values[0]);
        float valor = Float.parseFloat(texto);

        if(valor == 0){
            x++;
            //cambia la palabra y suma
            if(x < 6){
                if(x == 1 || x == 3 || x == 5){
                    c_jugador1++;
                }else{
                    c_jugador2++;
                }

                frameLayout.setBackgroundColor(Color.BLUE);
                mostrarPalabra.setText(palabras[x]);
                Log.w("X:",String.valueOf(x));
            }else{
                mostrarPalabra.setText("JUEGO TERMINADO");
                p1.setText("Jugador 1: " + c_jugador1);
                p2.setText("Jugador 2: " + c_jugador2);
            }
        }else{
            if(x == 6){
                mostrarPalabra.setText("JUEGO TERMINADO");
            }else{
                Log.w("XX:",String.valueOf(x));
                frameLayout.setBackgroundColor(Color.YELLOW);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
