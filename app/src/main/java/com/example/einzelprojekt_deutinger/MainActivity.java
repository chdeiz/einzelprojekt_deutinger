package com.example.einzelprojekt_deutinger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Double maxMatNumber = 9.E12; // max of 9 Billion people + 3 digits for uni + sem
        final Double minMatNumber = 1000D; // min of three digits for university and semester

        Button btnSend = findViewById(R.id.inputButtonSend);

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
                    if (/*matriculationDouble < minMatNumber ||*/ matriculationDouble >= maxMatNumber) {
                        Toast.makeText(MainActivity.this, "Matrikelnummer ist ungültig!\nBitte eine gültige Matrikelnummer eingeben!", Toast.LENGTH_LONG).show();
                    } else {
                        // mat. number is alright -> next steps send to Server and calc checksum
                        matriculationNumber = Long.parseLong(matriculationString);
                        TextView output = findViewById(R.id.outputChecksum);
                        String checksum = calcChecksum(matriculationNumber);
                        output.setText("Quersumme Matrikelnummer:\n" + checksum);
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
            matnum/=10;
        }

        return Integer.toString(checksum);
    }
}