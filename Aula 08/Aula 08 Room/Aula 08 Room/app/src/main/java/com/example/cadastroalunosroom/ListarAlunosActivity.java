package com.example.cadastroalunosroom;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity {
    private ListView listViewAlunos;
    private Button btnVoltar;
    private AlunoDao alunoDao;
    private List<Aluno> listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        listViewAlunos = findViewById(R.id.listViewAlunos);
        btnVoltar = findViewById(R.id.btnVoltar);

        // Conectar ao banco de dados e pegar a lista de alunos
        alunoDao = AppDatabase.getInstance(this).alunoDao();
        listaAlunos = alunoDao.obterTodos();

        // Configurar o adaptador para exibir a lista na ListView
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaAlunos);
        listViewAlunos.setAdapter(adapter);

        // Fechar a tela ao clicar em "Voltar"
        btnVoltar.setOnClickListener(v -> finish());
    }
}