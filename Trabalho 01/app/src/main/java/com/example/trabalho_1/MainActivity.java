package com.example.trabalho_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editEnderecoCompleto;
    private static final int REQUEST_CEP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEnderecoCompleto = findViewById(R.id.editEnderecoCompleto);
        Button btnBuscarCep = findViewById(R.id.btnBuscarCep);

        btnBuscarCep.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuscaCepActivity.class);
            startActivityForResult(intent, REQUEST_CEP);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CEP && resultCode == RESULT_OK && data != null) { // Adicionado "data != null"
            String enderecoCompleto = data.getStringExtra("enderecoCompleto");
            if (enderecoCompleto != null) { // Verificação adicional
                editEnderecoCompleto.setText(enderecoCompleto);
            }
        }
    }
}