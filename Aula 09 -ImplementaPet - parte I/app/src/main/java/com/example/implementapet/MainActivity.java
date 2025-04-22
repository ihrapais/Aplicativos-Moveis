package com.example.implementapet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText editNomePet, editCPF, editTelefone;
    private PetDao petDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Iniciando MainActivity");

        // Vincular os campos do layout
        editNomePet = findViewById(R.id.editNome);
        editCPF = findViewById(R.id.editCPF);
        editTelefone = findViewById(R.id.editTelefone);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        Button btnListar = findViewById(R.id.btnListar);

        // Verificar componentes
        if (editNomePet == null) {
            Log.e(TAG, "onCreate: editNome é nulo");
            Toast.makeText(this, "Erro: Campo Nome não encontrado", Toast.LENGTH_LONG).show();
            return;
        }
        if (editCPF == null) {
            Log.e(TAG, "onCreate: editCPF é nulo");
            Toast.makeText(this, "Erro: Campo CPF não encontrado", Toast.LENGTH_LONG).show();
            return;
        }
        if (editTelefone == null) {
            Log.e(TAG, "onCreate: editTelefone é nulo");
            Toast.makeText(this, "Erro: Campo Telefone não encontrado", Toast.LENGTH_LONG).show();
            return;
        }
        if (btnCadastrar == null || btnListar == null) {
            Log.e(TAG, "onCreate: Botões não encontrados");
            Toast.makeText(this, "Erro: Botões não encontrados", Toast.LENGTH_LONG).show();
            return;
        }

        // Inicializar o DAO
        petDao = AppDatabase.getInstance(this).petDao();
        Log.d(TAG, "onCreate: PetDao inicializado");

        // Configurar o botão Cadastrar
        btnCadastrar.setOnClickListener(v -> {
            Log.d(TAG, "btnCadastrar: Botão Cadastrar clicado");
            String nomePet = editNomePet.getText().toString().trim();
            String cpf = editCPF.getText().toString().trim();
            String telefone = editTelefone.getText().toString().trim();

            if (!nomePet.isEmpty() && !cpf.isEmpty() && !telefone.isEmpty()) {
                try {
                    Pet pet = new Pet();
                    pet.cpf = cpf;
                    pet.nome = nomePet;
                    pet.telefone = telefone;
                    petDao.inserir(pet);
                    Log.d(TAG, "btnCadastrar: PET cadastrado - CPF: " + cpf);
                    Toast.makeText(this, "PET cadastrado!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } catch (Exception e) {
                    Log.e(TAG, "btnCadastrar: Erro ao cadastrar PET: " + e.getMessage());
                    Toast.makeText(this, "Erro ao cadastrar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.w(TAG, "btnCadastrar: Campos vazios detectados");
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar o botão Listar PETs
        btnListar.setOnClickListener(v -> {
            Log.d(TAG, "btnListar: Abrindo ListarPetsActivity");
            Intent intent = new Intent(this, ListarPetsActivity.class);
            startActivity(intent);
        });
    }

    private void limparCampos() {
        Log.d(TAG, "limparCampos: Limpando campos de texto");
        editNomePet.setText("");
        editCPF.setText("");
        editTelefone.setText("");
    }
}