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

        final Double maxMatricleNumber = 9.E9;

        Button btnSend = findViewById(R.id.inputButtonSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Matrikelnummer input
                TextView matnum = findViewById(R.id.inputMatnum);
                String matriculationString = matnum.getText().toString();
                Double matriculationDouble;
                Long matriculationNumber;
                if (matriculationString == null || matriculationString.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Matrikelnummer ist leer!\nBitte Matrikelnummer eingeben!", Toast.LENGTH_LONG).show();
                } else if (matriculationString != null) {
                    matriculationDouble = Double.parseDouble(matriculationString);
                    if (matriculationDouble <= 0 || matriculationDouble >= maxMatricleNumber) {
                        Toast.makeText(MainActivity.this, "Matrikelnummer ist ungültig!\nBitte eine gültige Matrikelnummer eingeben!", Toast.LENGTH_LONG).show();
                    } else {
                        matriculationNumber = Long.parseLong(matriculationString);
                        TextView output = findViewById(R.id.outputChecksum);
                        output.setText(Long.toString(matriculationNumber));
                    }
                }
            }
        });
    }
}