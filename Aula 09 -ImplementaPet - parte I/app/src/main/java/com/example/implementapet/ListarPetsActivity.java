package com.example.implementapet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ListarPetsActivity extends AppCompatActivity {
    private static final String TAG = "ListarPetsActivity";
    private ListView listViewPets;
    private Button btnVoltar;
    private PetDao petDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pets);
        Log.d(TAG, "onCreate: Iniciando ListarPetsActivity");

        // Vincular os componentes do layout
        listViewPets = findViewById(R.id.listViewPets);
        btnVoltar = findViewById(R.id.btnVoltar);

        // Verificar componentes
        if (listViewPets == null || btnVoltar == null) {
            Log.e(TAG, "onCreate: Um ou mais componentes do layout não encontrados");
            Toast.makeText(this, "Erro: Componentes não encontrados", Toast.LENGTH_LONG).show();
            return;
        }

        // Inicializar o DAO e obter a lista de PETs
        petDao = AppDatabase.getInstance(this).petDao();
        Log.d(TAG, "onCreate: PetDao inicializado");
        List<Pet> listaPets = petDao.obterTodos();
        Log.d(TAG, "onCreate: " + listaPets.size() + " PETs encontrados");

        // Configurar o adaptador para a ListView
        ArrayAdapter<Pet> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPets);
        listViewPets.setAdapter(adapter);

        // Configurar o botão Voltar
        btnVoltar.setOnClickListener(v -> {
            Log.d(TAG, "btnVoltar: Botão Voltar clicado");
            finish();
        });
    }
}