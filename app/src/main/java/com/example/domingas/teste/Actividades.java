package com.example.domingas.teste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Actividades extends AppCompatActivity {
    ImageView btnMulher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        btnMulher = (ImageView) findViewById(R.id.mulher);

        btnMulher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Actividades.this, PrincipalMulher.class);
                startActivity(i);
            }
        });
    }
}
