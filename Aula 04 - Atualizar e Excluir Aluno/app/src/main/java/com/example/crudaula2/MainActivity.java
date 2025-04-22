package com.example.crudaula2;

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
    private EditText editNome;
    private EditText editCpf;
    private EditText editTelefone;
    private Button buttonSalvar;
    private Button buttonListar;
    private AlunoDao dao;
    private Aluno aluno = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Carregando layout R.layout.activity_main");
        try {
            setContentView(R.layout.activity_main);
            Toast.makeText(this, "MainActivity iniciada", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate: Iniciando MainActivity");

            // Inicializar componentes
            editNome = findViewById(R.id.editNome);
            editCpf = findViewById(R.id.editCpf);
            editTelefone = findViewById(R.id.editTelefone);
            buttonSalvar = findViewById(R.id.buttonSalvar);
            buttonListar = findViewById(R.id.buttonListar);

            // Verificar componentes
            if (editNome == null) {
                Log.e(TAG, "onCreate: editNome é nulo");
                Toast.makeText(this, "Erro: Campo Nome não encontrado", Toast.LENGTH_LONG).show();
                return;
            }
            if (editCpf == null) {
                Log.e(TAG, "onCreate: editCpf é nulo");
                Toast.makeText(this, "Erro: Campo CPF não encontrado", Toast.LENGTH_LONG).show();
                return;
            }
            if (editTelefone == null) {
                Log.e(TAG, "onCreate: editTelefone é nulo");
                Toast.makeText(this, "Erro: Campo Telefone não encontrado", Toast.LENGTH_LONG).show();
                return;
            }
            if (buttonSalvar == null) {
                Log.e(TAG, "onCreate: buttonSalvar é nulo");
                Toast.makeText(this, "Erro: Botão Salvar não encontrado", Toast.LENGTH_LONG).show();
                return;
            }
            if (buttonListar == null) {
                Log.e(TAG, "onCreate: buttonListar é nulo");
                Toast.makeText(this, "Erro: Botão Listar não encontrado", Toast.LENGTH_LONG).show();
                return;
            }

            // Inicializar DAO
            dao = new AlunoDao(this);
            if (dao == null) {
                Log.e(TAG, "onCreate: AlunoDao é nulo");
                Toast.makeText(this, "Erro: Não foi possível inicializar o banco de dados", Toast.LENGTH_LONG).show();
                return;
            }
            Log.d(TAG, "onCreate: AlunoDao inicializado com sucesso");

            // Verificar modo (cadastro ou atualização)
            Intent it = getIntent();
            if (it != null && it.hasExtra("aluno")) {
                aluno = (Aluno) it.getSerializableExtra("aluno");
                if (aluno != null) {
                    Log.d(TAG, "onCreate: Aluno recebido para atualização: ID=" + aluno.getId() + ", Nome=" + aluno.getNome() + ", CPF=" + aluno.getCpf() + ", Telefone=" + aluno.getTelefone());
                    editNome.setText(aluno.getNome());
                    editCpf.setText(aluno.getCpf());
                    editTelefone.setText(aluno.getTelefone());
                } else {
                    Log.e(TAG, "onCreate: Aluno recebido é nulo");
                    Toast.makeText(this, "Erro: Aluno não recebido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(TAG, "onCreate: Modo cadastro");
            }

            // Configurar clique do botão Salvar
            buttonSalvar.setOnClickListener(v -> {
                Log.d(TAG, "buttonSalvar: Clique detectado");
                Toast.makeText(this, "Botão Salvar clicado", Toast.LENGTH_SHORT).show();
                salvar(v);
            });

            // Configurar clique do botão Listar
            buttonListar.setOnClickListener(v -> {
                Log.d(TAG, "buttonListar: Abrindo ListarAlunosActivity");
                Intent intent = new Intent(this, ListarAlunosActivity.class);
                startActivity(intent);
            });

            Log.d(TAG, "onCreate: Inicialização concluída com sucesso");
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Erro durante inicialização: " + e.getMessage(), e);
            Toast.makeText(this, "Erro ao iniciar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void salvar(View view) {
        try {
            Log.d(TAG, "salvar: Iniciando salvamento");
            Toast.makeText(this, "Salvar chamado", Toast.LENGTH_SHORT).show();

            String nomeDigitado = editNome.getText().toString().trim();
            String cpfDigitado = editCpf.getText().toString().trim();
            String telefoneDigitado = editTelefone.getText().toString().trim();

            Log.d(TAG, "salvar: Dados digitados - Nome: " + nomeDigitado + ", CPF=" + cpfDigitado + ", Telefone=" + telefoneDigitado);

            if (nomeDigitado.isEmpty() || cpfDigitado.isEmpty() || telefoneDigitado.isEmpty()) {
                Log.w(TAG, "salvar: Campos vazios detectados");
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar CPF
            if (!dao.validaCpf(cpfDigitado)) {
                Log.w(TAG, "salvar: CPF inválido: " + cpfDigitado);
                Toast.makeText(this, "CPF inválido. Use 11 dígitos numéricos (ex.: 12345678901).", Toast.LENGTH_SHORT).show();
                return;
            }

            if (aluno == null) {
                // Verificar CPF duplicado para novos cadastros
                if (dao.cpfExistente(cpfDigitado)) {
                    Log.w(TAG, "salvar: CPF duplicado: " + cpfDigitado);
                    Toast.makeText(this, "CPF já cadastrado. Insira um CPF diferente.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Aluno novoAluno = new Aluno();
                novoAluno.setNome(nomeDigitado);
                novoAluno.setCpf(cpfDigitado);
                novoAluno.setTelefone(telefoneDigitado);
                long id = dao.inserir(novoAluno);
                if (id != -1) {
                    Log.d(TAG, "salvar: Aluno inserido com id: " + id);
                    Toast.makeText(this, "Aluno inserido com id: " + id, Toast.LENGTH_SHORT).show();
                    // Limpar campos após salvar
                    editNome.setText("");
                    editCpf.setText("");
                    editTelefone.setText("");
                } else {
                    Log.e(TAG, "salvar: Erro ao inserir aluno");
                    Toast.makeText(this, "Erro ao inserir aluno.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                // Verificar CPF duplicado para atualizações (exceto se for o mesmo CPF do aluno atual)
                if (!cpfDigitado.equals(aluno.getCpf()) && dao.cpfExistente(cpfDigitado)) {
                    Log.w(TAG, "salvar: CPF duplicado: " + cpfDigitado);
                    Toast.makeText(this, "CPF já cadastrado. Insira um CPF diferente.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG, "salvar: Atualizando aluno com ID: " + aluno.getId());
                aluno.setNome(nomeDigitado);
                aluno.setCpf(cpfDigitado);
                aluno.setTelefone(telefoneDigitado);
                dao.atualizar(aluno);
                Log.d(TAG, "salvar: Aluno atualizado com sucesso");
                Toast.makeText(this, "Aluno atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                // Limpar campos após atualizar
                editNome.setText("");
                editCpf.setText("");
                editTelefone.setText("");
                aluno = null; // Resetar para modo cadastro
            }
            // Removido finish() para manter MainActivity na pilha
        } catch (Exception e) {
            Log.e(TAG, "salvar: Erro ao salvar/atualizar: " + e.getMessage(), e);
            Toast.makeText(this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}