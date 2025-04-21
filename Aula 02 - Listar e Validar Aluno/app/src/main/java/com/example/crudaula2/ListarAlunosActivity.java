package com.example.crudaula2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity {
    private ListView listView;
    private AlunoDao dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        listView = findViewById(R.id.lista_alunos);
        btnVoltar = findViewById(R.id.btnVoltar);
        dao = new AlunoDao(this);
        alunos = dao.obterTodos();
        alunosFiltrados.addAll(alunos);

        ArrayAdapter<Aluno> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunosFiltrados);
        listView.setAdapter(adaptador);

        // Evento do botão Voltar
        btnVoltar.setOnClickListener(v -> irParaCadastro());
    }

    private void irParaCadastro() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}