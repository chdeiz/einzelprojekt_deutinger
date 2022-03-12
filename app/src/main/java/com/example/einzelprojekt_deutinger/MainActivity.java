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

        // Matrikelnummer input
        TextView matnum = findViewById(R.id.inputMatnum);
        String matriculationString = matnum.getText().toString();
        int matriculationNumber = Integer.getInteger(matriculationString);

        Button btnSend = findViewById(R.id.inputButtonSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(matriculationString.isEmpty()){
                    Toast.makeText(MainActivity.this, "Matrikelnummer ist leer!\nBitte Matrikelnummer eingeben!", Toast.LENGTH_LONG).show();
                }
                TextView output = findViewById(R.id.outputChecksum);
                output.setText(matnum.getText().toString());
                matnum.toString().toCharArray();
            }
        });
    }
}