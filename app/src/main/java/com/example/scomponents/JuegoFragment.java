package com.example.scomponents;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuegoFragment extends Fragment implements SensorEventListener {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String[] palabras = {"Conducir","Hablar","Caminar","Reir","Sonreir","Pensar"};
    int x = 0;
    FrameLayout frameLayout;
    SensorManager manager;
    Sensor sensor;
    TextView mostrarPalabra, p1,p2;
    int c_jugador1 = 0, c_jugador2 = 0;
    Button enviar;

    String message;

    String numeroTelefono = "0050689469996";

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
        enviar = (Button) view.findViewById(R.id.send_sms);

        mostrarPalabra.setText(palabras[0]);
        p1.setText("");
        p2.setText("");
        enviar.setVisibility(View.INVISIBLE);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(numeroTelefono,null,message,null,null);
                    Toast.makeText(getContext(), "SMS enviado exitisamente!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "Fallo al enviar SMS, intenta de nuevo.", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                enviar.setVisibility(View.VISIBLE);
                message = "Eres el ganador del juego!\n" +
                        "¡¡FELICIDADES!!\n" +
                        "Tú puntaje fue de: " + c_jugador1 + " puntos.";
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
/*
    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(numeroTelefono, null, message, null, null);
                    Toast.makeText(getContext(), "SMS enviado.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(),
                            "Fallo al enviar SMS, intenta de nuevo!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }*/

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
