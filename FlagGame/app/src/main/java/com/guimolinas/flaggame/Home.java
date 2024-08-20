package com.guimolinas.flaggame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.text.Editable;
import android.text.TextWatcher;

public class Home extends AppCompatActivity {

    public Button btnPlay;
    public Button btnExit;

    public EditText edtNome;

    public static String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnPlay = findViewById(R.id.btnPlay);
        btnExit = findViewById(R.id.btnExit);
        edtNome = findViewById(R.id.edtNome);

        // Desabilita o botão "Jogar" inicialmente
        btnPlay.setEnabled(false);

        // Habilitar o botão "Jogar" apenas quando algo for digitado
        edtNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não precisa implementar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Habilitar o botão "Jogar" se o campo de texto não estiver vazio
                btnPlay.setEnabled(s.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Não precisa implementar
            }
        });

        // Lógica para o botão "Jogar"
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = edtNome.getText().toString();
                Intent intent = new Intent(Home.this, Game.class);
                intent.putExtra("nomeJogador", nome);
                startActivity(intent);
            }
        });

        // Lógica para o botão "Sair"
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity(); // Fecha a activity
            }
        });
    }
}
