package com.example.scomponents;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class LoginFragment extends Fragment {

    Button inicio, bluetooth;
    BluetoothAdapter AB;
    Set<BluetoothDevice> pairedDEvices;
    int flag = 0;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        inicio = view.findViewById(R.id.inicio);
        bluetooth = view.findViewById(R.id.bluetooth);

        AB = BluetoothAdapter.getDefaultAdapter();

        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0){
                    on(v);
                    visible(v);
                    flag = 1;
                }else{
                    off(v);
                    flag = 0;
                }

            }
        });

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se va al otro fragment
                MainActivity.manager.beginTransaction().replace(R.id.fragment_container, new JuegoFragment(),null).commit();
            }
        });
        return view;
    }

    public void on(View v){
        if (!AB.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getContext(), "Bluetooth encendido!",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Encendiendo..", Toast.LENGTH_LONG).show();
        }
    }

    public void off(View v){
        AB.disable();
        Toast.makeText(getContext(), "Bluetooth apagado!" ,Toast.LENGTH_LONG).show();
    }


    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }
}
