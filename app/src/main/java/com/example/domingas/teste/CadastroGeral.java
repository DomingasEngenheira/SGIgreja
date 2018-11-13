package com.example.domingas.teste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CadastroGeral extends AppCompatActivity {
    Button btnMembro, btnCoro, btnDireccao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_geral);
        btnMembro = (Button) findViewById(R.id.btnMembro);
        btnCoro = (Button) findViewById(R.id.btnCoro);
        btnDireccao = (Button) findViewById(R.id.btnDireccao);

        btnMembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CadastroGeral.this, CadastroMembro.class);
                startActivity(i);
            }
        });



       }

}

