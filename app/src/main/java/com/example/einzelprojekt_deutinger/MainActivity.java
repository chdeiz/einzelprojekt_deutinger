package com.example.einzelprojekt_deutinger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Double maxMatNumber = 9.E12; // max of 9 Billion people + 3 digits for uni + sem
        // final Double minMatNumber = 1000D; // min of three digits for university and semester
        final int minLength = 4;

        Button btnCalc = findViewById(R.id.inputButtonChecksum);
        Button btnSend = findViewById(R.id.inputButtonSend);


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // matriculation number input and input checks
                TextView matnum = findViewById(R.id.inputMatnum);
                String matriculationString = matnum.getText().toString();
                Double matriculationDouble;
                Long matriculationNumber;
                if (matriculationString == null || matriculationString.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Matrikelnummer ist leer!\nBitte Matrikelnummer eingeben!", Toast.LENGTH_LONG).show();
                } else if (matriculationString != null) {
                    matriculationDouble = Double.parseDouble(matriculationString);
                    if (matriculationString.length() < minLength || matriculationDouble >= maxMatNumber) {
                        Toast.makeText(MainActivity.this, "Matrikelnummer ist ungültig!\nBitte eine gültige Matrikelnummer eingeben!", Toast.LENGTH_LONG).show();
                    } else {
                        // mat. number is alright -> calc checksum
                        matriculationNumber = Long.parseLong(matriculationString);
                        TextView output = findViewById(R.id.outputChecksum);
                        String checksum = calcChecksum(matriculationNumber);
                        output.setText("Quersumme Matrikelnummer:\n" + checksum + "\t(binär)");
                    }
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // matriculation number input
                TextView matnum = findViewById(R.id.inputMatnum);
                String matriculationString = matnum.getText().toString();
                Double matriculationDouble;
                Long matriculationNumber;
                if (matriculationString == null || matriculationString.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Matrikelnummer ist leer!\nBitte Matrikelnummer eingeben!", Toast.LENGTH_LONG).show();
                } else if (matriculationString != null) {
                    matriculationDouble = Double.parseDouble(matriculationString);
                    if (matriculationString.length() < minLength || matriculationDouble >= maxMatNumber) {
                        Toast.makeText(MainActivity.this, "Matrikelnummer ist ungültig!\nBitte eine gültige Matrikelnummer eingeben!", Toast.LENGTH_LONG).show();
                    } else {
                        // mat. number is alright -> next steps send to Server and show reply

                        String serverReply = "";
                        // handle networking
                        try {
                            serverReply += handleConnection(matriculationString);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        TextView output = findViewById(R.id.outputServerReply);
                        output.setText("Rückgabe vom Server:\n" + serverReply);
                    }
                }
            }
        });
    }

    private String calcChecksum(Long matriculationNumber) {
        Long matnum = matriculationNumber;
        int count = 0;
        do {
            matnum /= 10;
            count++;
        } while (matnum > 0);

        matnum = matriculationNumber;
        int checksum = 0;

        for (int i = 0; i < count; i++) {
            checksum += matnum % 10;
            matnum /= 10;
        }

        return Integer.toBinaryString(checksum * 2);
    }

    /**
     * I want to start and end the connection in this section, so the user isn't connected the whole time when the app is opened
     * send matriculation num to Server
     */
    private String handleConnection(String matriculationNumber) throws IOException {
        // policy changes so the app doesn't crash
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //create client socket and connect to server
        Socket client = new Socket("se2-isys.aau.at", 53212);

        // create and send output to server
        DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());
        outToServer.writeBytes(matriculationNumber + "\n");

        // reply from server
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String reply = inFromServer.readLine();

        client.close();

        return reply;
    }


}