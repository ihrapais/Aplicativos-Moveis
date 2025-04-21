package com.example.crudaula2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText editNome, editCpf, editTelefone;
    private Button buttonSalvar, buttonListar;
    private AlunoDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincular componentes
        editNome = findViewById(R.id.editNome);
        editCpf = findViewById(R.id.editCpf);
        editTelefone = findViewById(R.id.editTelefone);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonListar = findViewById(R.id.buttonListar);
        dao = new AlunoDao(this);

        // Evento do botão Salvar
        buttonSalvar.setOnClickListener(v -> {
            try {
                Aluno aluno = new Aluno();
                aluno.setNome(editNome.getText().toString());
                aluno.setCpf(editCpf.getText().toString());
                aluno.setTelefone(editTelefone.getText().toString());
                dao.inserir(aluno);
                Toast.makeText(this, "Aluno cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                // Limpar campos
                editNome.setText("");
                editCpf.setText("");
                editTelefone.setText("");
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Evento do botão Listar
        buttonListar.setOnClickListener(v -> irParaListar(v));
    }

    public void irParaListar(View view) {
        Intent intent = new Intent(this, ListarAlunosActivity.class);
        startActivity(intent);
    }
}