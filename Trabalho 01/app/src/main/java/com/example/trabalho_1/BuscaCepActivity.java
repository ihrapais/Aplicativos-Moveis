package com.example.trabalho_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuscaCepActivity extends AppCompatActivity {

    private EditText cep, logradouro, bairro, cidade, estado, numero, complemento;
    private ViaCepService viaCepService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cep); // Corrigido para activity_buscar_cep

        cep = findViewById(R.id.cep);
        logradouro = findViewById(R.id.logradouro);
        bairro = findViewById(R.id.bairro);
        cidade = findViewById(R.id.cidade);
        estado = findViewById(R.id.estado);
        numero = findViewById(R.id.numero);
        complemento = findViewById(R.id.complemento);
        Button btnBuscar = findViewById(R.id.btnBuscar);
        Button btnSalvarEndereco = findViewById(R.id.btnSalvarEndereco);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        viaCepService = retrofit.create(ViaCepService.class);

        btnBuscar.setOnClickListener(v -> buscarCep(v));
        btnSalvarEndereco.setOnClickListener(v -> salvarEndereco(v));
    }

    public void buscarCep(View view) {
        String cepText = cep.getText().toString().trim();
        if (cepText.length() != 8) {
            Toast.makeText(this, "CEP inválido! Digite 8 números.", Toast.LENGTH_SHORT).show();
            return;
        }

        viaCepService.getAddress(cepText).enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CepResponse cepResponse = response.body();
                    logradouro.setText(cepResponse.getLogradouro());
                    bairro.setText(cepResponse.getBairro());
                    cidade.setText(cepResponse.getLocalidade());
                    estado.setText(cepResponse.getUf());
                } else {
                    Toast.makeText(BuscaCepActivity.this, "CEP não encontrado!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                Toast.makeText(BuscaCepActivity.this, "Erro ao buscar CEP: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void salvarEndereco(View view) {
        String logradouroText = logradouro.getText().toString();
        String numeroText = numero.getText().toString();
        String complementoText = complemento.getText().toString();
        String bairroText = bairro.getText().toString();
        String cidadeText = cidade.getText().toString();
        String estadoText = estado.getText().toString();

        if (logradouroText.isEmpty() || numeroText.isEmpty() || bairroText.isEmpty() || cidadeText.isEmpty() || estadoText.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }

        String enderecoCompleto = complementoText.isEmpty() ?
                String.format("%s, %s - %s, %s - %s", logradouroText, numeroText, bairroText, cidadeText, estadoText) :
                String.format("%s, %s, %s - %s, %s - %s", logradouroText, numeroText, complementoText, bairroText, cidadeText, estadoText);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("enderecoCompleto", enderecoCompleto);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}