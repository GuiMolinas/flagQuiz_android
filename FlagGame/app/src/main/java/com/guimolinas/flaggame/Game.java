package com.guimolinas.flaggame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game extends AppCompatActivity {

    public RadioGroup rdgRespostas;

    public RadioButton rdbResposta1;
    public RadioButton rdbResposta2;
    public RadioButton rdbResposta3;
    public RadioButton rdbResposta4;

    public ImageView imgBandeira;

    public Button btnResposta;

    public static List<ItemPais> lista;

    public Random rand;

    public int vez = 1;

    public static int certas;
    public static String nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        certas = 0;

        rand = new Random();

        imgBandeira = (ImageView) findViewById(R.id.imgBandeira);

        rdgRespostas = (RadioGroup) findViewById(R.id.rdgRespostas);

        rdbResposta1 = (RadioButton) findViewById(R.id.rdbResposta1);
        rdbResposta2 = (RadioButton) findViewById(R.id.rdbResposta2);
        rdbResposta3 = (RadioButton) findViewById(R.id.rdbResposta3);
        rdbResposta4 = (RadioButton) findViewById(R.id.rdbResposta4);

        btnResposta = (Button) findViewById(R.id.btnResponder);

        lista = new ArrayList<>();

        nome = Home.nome;

        //adicionando nomes e bandeiras a lista

        for(int i = 0; i < new BancoDeDados().respostas.length; i++) {
            lista.add(new ItemPais(new BancoDeDados().respostas[i], new BancoDeDados().bandeiras[i]));
        }

        //Misturando os nomes
        Collections.shuffle(lista);

        novaPergunta(vez);

        btnResposta.setEnabled(false);

        rdgRespostas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                btnResposta.setEnabled(true);
            }
        });

        btnResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verifica se a resposta está correta
                boolean respostaCorreta = false;
                String respostaSelecionada = "";

                if (rdbResposta1.isChecked()) {
                    respostaSelecionada = rdbResposta1.getText().toString();
                } else if (rdbResposta2.isChecked()) {
                    respostaSelecionada = rdbResposta2.getText().toString();
                } else if (rdbResposta3.isChecked()) {
                    respostaSelecionada = rdbResposta3.getText().toString();
                } else if (rdbResposta4.isChecked()) {
                    respostaSelecionada = rdbResposta4.getText().toString();
                }

                if (respostaSelecionada.equalsIgnoreCase(lista.get(vez - 1).getNome())) {
                    respostaCorreta = true;
                }

                if (respostaCorreta) {
                    certas++;
                }

                // Verifica se acabou o jogo
                if (vez < lista.size()) {
                    vez++;
                    novaPergunta(vez);
                } else {
                    // Fim de jogo, navega para a tela de resultados
                    Intent intent = new Intent(Game.this, Resultado.class);
                    intent.putExtra("totalCorretas", certas);  // Passa o número de acertos
                    intent.putExtra("totalPerguntas", lista.size());  // Passa o total de perguntas
                    intent.putExtra("nomeJogador", nome);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void novaPergunta(int numero) {
        // Limpa a seleção atual
        rdgRespostas.clearCheck();
        
        //Envia imagem da bandeira para a tela
        imgBandeira.setImageResource(lista.get(numero - 1).getImagem());

        //Decide onde colocar a resposta correta
        int respostaCorreta = rand.nextInt(4) + 1;

        int primeiraAlternativa = numero - 1;
        int segundaAlternativa;
        int terceiraAlternativa;
        int quartaAlternativa;

        do {
            segundaAlternativa = rand.nextInt(lista.size());
        } while (segundaAlternativa == primeiraAlternativa);

        do {
            terceiraAlternativa = rand.nextInt(lista.size());
        } while (terceiraAlternativa == primeiraAlternativa || terceiraAlternativa == segundaAlternativa);

        do {
            quartaAlternativa = rand.nextInt(lista.size());
        } while (quartaAlternativa == primeiraAlternativa
                || quartaAlternativa == segundaAlternativa
                || quartaAlternativa == terceiraAlternativa);

        switch(respostaCorreta) {
            case 1:
                rdbResposta1.setText(lista.get(primeiraAlternativa).getNome());
                rdbResposta2.setText(lista.get(segundaAlternativa).getNome());
                rdbResposta3.setText(lista.get(terceiraAlternativa).getNome());
                rdbResposta4.setText(lista.get(quartaAlternativa).getNome());

                break;

            case 2:
                rdbResposta2.setText(lista.get(primeiraAlternativa).getNome());
                rdbResposta1.setText(lista.get(segundaAlternativa).getNome());
                rdbResposta3.setText(lista.get(terceiraAlternativa).getNome());
                rdbResposta4.setText(lista.get(quartaAlternativa).getNome());

                break;

            case 3:
                rdbResposta3.setText(lista.get(primeiraAlternativa).getNome());
                rdbResposta2.setText(lista.get(segundaAlternativa).getNome());
                rdbResposta1.setText(lista.get(terceiraAlternativa).getNome());
                rdbResposta4.setText(lista.get(quartaAlternativa).getNome());

                break;

            case 4:
                rdbResposta4.setText(lista.get(primeiraAlternativa).getNome());
                rdbResposta2.setText(lista.get(segundaAlternativa).getNome());
                rdbResposta3.setText(lista.get(terceiraAlternativa).getNome());
                rdbResposta1.setText(lista.get(quartaAlternativa).getNome());

                break;


        }
    }

}