package com.example.cadastroalunosroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editNome, editCPF, editTelefone;
    private AlunoDao alunoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = findViewById(R.id.editNome);
        editCPF = findViewById(R.id.editCPF);
        editTelefone = findViewById(R.id.editTelefone);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnListar = findViewById(R.id.btnListar);

        alunoDao = AppDatabase.getInstance(this).alunoDao();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editNome.getText().toString();
                String cpf = editCPF.getText().toString();
                String telefone = editTelefone.getText().toString();

                if (!nome.isEmpty() && !cpf.isEmpty() && !telefone.isEmpty()) {
                    if (AlunoValidator.isCpfValido(cpf) && AlunoValidator.isTelefoneValido(telefone)) {
                        if (alunoDao.cpfExistente(cpf) == 0) {
                            Aluno aluno = new Aluno();
                            aluno.setNome(nome);
                            aluno.setCpf(cpf);
                            aluno.setTelefone(telefone);
                            alunoDao.inserir(aluno);
                            Toast.makeText(MainActivity.this, "Aluno salvo!", Toast.LENGTH_SHORT).show();
                            limparCampos();
                        } else {
                            Toast.makeText(MainActivity.this, "CPF já cadastrado!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "CPF ou Telefone inválido!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarAlunosActivity.class);
                startActivity(intent);
            }
        });
    }

    private void limparCampos() {
        editNome.setText("");
        editCPF.setText("");
        editTelefone.setText("");
    }
}