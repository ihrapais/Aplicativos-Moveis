package com.example.supermercadoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ListarProdutosActivity extends AppCompatActivity {
    private static final String TAG = "ListarProdutosActivity";
    private ListView listViewProdutos;
    private Button btnVoltar;
    private ProdutoDao produtoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produtos);
        Log.d(TAG, "onCreate: Iniciando ListarProdutosActivity");

        // Vincular os componentes do layout
        listViewProdutos = findViewById(R.id.listViewProdutos);
        btnVoltar = findViewById(R.id.btnVoltar);

        // Verificar componentes
        if (listViewProdutos == null || btnVoltar == null) {
            Log.e(TAG, "onCreate: Um ou mais componentes do layout não encontrados");
            Toast.makeText(this, "Erro: Componentes não encontrados", Toast.LENGTH_LONG).show();
            return;
        }

        // Inicializar o DAO e obter a lista de produtos
        produtoDao = AppDatabase.getInstance(this).produtoDao();
        Log.d(TAG, "onCreate: ProdutoDao inicializado");
        List<Produto> listaProdutos = produtoDao.obterTodos();
        Log.d(TAG, "onCreate: " + listaProdutos.size() + " produtos encontrados");

        // Configurar o adaptador para a ListView
        ArrayAdapter<Produto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProdutos);
        listViewProdutos.setAdapter(adapter);

        // Configurar o botão Voltar
        btnVoltar.setOnClickListener(v -> {
            Log.d(TAG, "btnVoltar: Botão Voltar clicado");
            finish();
        });
    }
}