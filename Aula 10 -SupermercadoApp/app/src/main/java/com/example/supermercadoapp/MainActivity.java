package com.example.supermercadoapp;

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
    private EditText editNome, editPreco, editCategoria;
    private ProdutoDao produtoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Iniciando MainActivity");

        // Vincular os campos do layout
        editNome = findViewById(R.id.editNome);
        editPreco = findViewById(R.id.editPreco);
        editCategoria = findViewById(R.id.editCategoria);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        Button btnListar = findViewById(R.id.btnListar);

        // Verificar componentes
        if (editNome == null || editPreco == null || editCategoria == null) {
            Log.e(TAG, "onCreate: Um ou mais campos são nulos");
            Toast.makeText(this, "Erro: Campos não encontrados", Toast.LENGTH_LONG).show();
            return;
        }
        if (btnCadastrar == null || btnListar == null) {
            Log.e(TAG, "onCreate: Botões não encontrados");
            Toast.makeText(this, "Erro: Botões não encontrados", Toast.LENGTH_LONG).show();
            return;
        }

        // Inicializar o DAO
        produtoDao = AppDatabase.getInstance(this).produtoDao();
        Log.d(TAG, "onCreate: ProdutoDao inicializado");

        // Configurar o botão Cadastrar
        btnCadastrar.setOnClickListener(v -> {
            Log.d(TAG, "btnCadastrar: Botão Cadastrar clicado");
            String nome = editNome.getText().toString().trim();
            String precoStr = editPreco.getText().toString().trim();
            String categoria = editCategoria.getText().toString().trim();

            if (!nome.isEmpty() && !precoStr.isEmpty() && !categoria.isEmpty()) {
                try {
                    double preco = Double.parseDouble(precoStr);
                    Produto produto = new Produto();
                    produto.nome = nome;
                    produto.preco = preco;
                    produto.categoria = categoria;
                    produtoDao.inserir(produto);
                    Log.d(TAG, "btnCadastrar: Produto cadastrado - Nome: " + nome);
                    Toast.makeText(this, "Produto cadastrado!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } catch (NumberFormatException e) {
                    Log.e(TAG, "btnCadastrar: Erro ao converter preço: " + e.getMessage());
                    Toast.makeText(this, "Erro: Preço inválido", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, "btnCadastrar: Erro ao cadastrar produto: " + e.getMessage());
                    Toast.makeText(this, "Erro ao cadastrar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.w(TAG, "btnCadastrar: Campos vazios detectados");
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar o botão Listar Itens
        btnListar.setOnClickListener(v -> {
            Log.d(TAG, "btnListar: Abrindo ListarProdutosActivity");
            Intent intent = new Intent(this, ListarProdutosActivity.class);
            startActivity(intent);
        });
    }

    private void limparCampos() {
        Log.d(TAG, "limparCampos: Limpando campos de texto");
        editNome.setText("");
        editPreco.setText("");
        editCategoria.setText("");
    }
}