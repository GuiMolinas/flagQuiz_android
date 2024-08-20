package com.guimolinas.flaggame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Resultado extends AppCompatActivity {

    public TextView txtResultado;
    public TextView txtNome;

    public Button btnNovamente;
    public Button btnMenu;

    private int certas;
    private int total;
    private String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtNome = findViewById(R.id.txtNome);
        txtResultado = findViewById(R.id.txtResultado);

        btnNovamente = findViewById(R.id.btnNovamente);
        btnMenu = findViewById(R.id.btnMenu);

        Intent intent = getIntent();
        nome = intent.getStringExtra("nomeJogador");
        certas = intent.getIntExtra("totalCorretas", 0);
        total = intent.getIntExtra("totalPerguntas", 0);

        txtResultado.setText(certas + " / " + total);
        txtNome.setText("Seu nome: " + nome);

        btnNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.certas = 0;
                Intent intent = new Intent(Resultado.this, Game.class);
                startActivity(intent);
                finish();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Resultado.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
