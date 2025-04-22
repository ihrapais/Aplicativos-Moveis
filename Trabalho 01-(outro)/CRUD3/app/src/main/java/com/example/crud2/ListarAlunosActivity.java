package com.example.crud2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Importar Log para debug (opcional)
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

// Import correto do R da sua aplicação
import com.example.crud2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity {

    private static final String TAG = "ListarAlunosActivity"; // Tag para Logs

    private ListView listViewAlunos;
    private Button btnVoltar;
    private EditText edtPesquisaNome;
    private Button btnOrdenarAZ, btnOrdenarZA; // Botões de Ordenação
    private AlunoDao alunoDao;
    private List<Aluno> listaAlunos; // Lista completa vinda do banco
    private List<Aluno> listaFiltrada; // Lista exibida (pode ser filtrada/ordenada)
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);
        Log.d(TAG, "onCreate: Iniciando ListarAlunosActivity");

        // --- Vinculação de Componentes ---
        listViewAlunos = findViewById(R.id.listViewAlunos);
        btnVoltar = findViewById(R.id.btnVoltar);
        edtPesquisaNome = findViewById(R.id.edtPesquisaNome);
        // Botão Buscar usa android:onClick, não precisa findViewById aqui
        btnOrdenarAZ = findViewById(R.id.btnOrdenarAZ);
        btnOrdenarZA = findViewById(R.id.btnOrdenarZA);

        // --- Verificação de Componentes (Defensivo) ---
        if (listViewAlunos == null || btnVoltar == null || edtPesquisaNome == null || btnOrdenarAZ == null || btnOrdenarZA == null) {
            Log.e(TAG, "onCreate: Erro ao vincular componentes! Verifique os IDs no XML.");
            Toast.makeText(this, "Erro interno ao carregar a tela. Verifique os IDs no layout.", Toast.LENGTH_LONG).show();
            finish(); // Fecha a activity se componentes essenciais não foram encontrados
            return;
        }
        Log.d(TAG, "onCreate: Componentes vinculados com sucesso.");

        // --- Inicialização de Dados ---
        alunoDao = new AlunoDao(this);
        listaAlunos = new ArrayList<>(); // Inicializa vazia para evitar NullPointerException
        listaFiltrada = new ArrayList<>(); // Inicializa vazia

        // Carrega dados do banco (com tratamento de erro)
        try {
            listaAlunos = alunoDao.listar();
            if (listaAlunos == null) { // Verifica se o DAO retornou null
                Log.w(TAG, "onCreate: alunoDao.listar() retornou null.");
                Toast.makeText(this, "Não foi possível carregar a lista de alunos (DAO retornou null).", Toast.LENGTH_LONG).show();
                listaAlunos = new ArrayList<>(); // Garante que não seja null
            } else {
                Log.d(TAG, "onCreate: " + listaAlunos.size() + " alunos carregados do DAO.");
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Erro ao chamar alunoDao.listar()", e);
            Toast.makeText(this, "Erro ao carregar alunos do banco de dados: " + e.getMessage(), Toast.LENGTH_LONG).show();
            listaAlunos = new ArrayList<>(); // Garante que não seja null em caso de exceção
        }

        listaFiltrada.addAll(listaAlunos); // Popula a lista filtrada inicialmente
        Log.d(TAG, "onCreate: listaFiltrada inicializada com " + listaFiltrada.size() + " alunos.");

        // --- Configuração do Adapter e ListView ---
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaFiltrada);
        listViewAlunos.setAdapter(adapter);
        Log.d(TAG, "onCreate: Adapter configurado na ListView.");

        registerForContextMenu(listViewAlunos); // Registrar menu de contexto

        // --- Configuração dos Listeners ---
        btnVoltar.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Botão Voltar pressionado.");
            finish();
        });

        // Listeners dos botões de ordenação (com verificação defensiva feita acima)
        btnOrdenarAZ.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Botão Ordenar A-Z pressionado.");
            ordenarLista(true);
        });
        btnOrdenarZA.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Botão Ordenar Z-A pressionado.");
            ordenarLista(false);
        });

        Log.d(TAG, "onCreate: Listeners configurados.");
    }

    // --- Lógica de Ordenação ---
    private void ordenarLista(boolean ascendente) {
        Log.d(TAG, "ordenarLista: Ordenando. Ascendente = " + ascendente);
        if (listaFiltrada == null || listaFiltrada.isEmpty()) {
            Log.w(TAG, "ordenarLista: Lista filtrada vazia ou nula, nada para ordenar.");
            Toast.makeText(this, "Lista vazia, nada para ordenar.", Toast.LENGTH_SHORT).show();
            return;
        }

        Comparator<Aluno> comparador = (aluno1, aluno2) -> {
            if (aluno1 == null || aluno1.getNome() == null) return (aluno2 == null || aluno2.getNome() == null) ? 0 : 1; // Nulls/Empty no fim
            if (aluno2 == null || aluno2.getNome() == null) return -1; // Nulls/Empty no fim
            return aluno1.getNome().compareToIgnoreCase(aluno2.getNome());
        };

        if (!ascendente) {
            comparador = comparador.reversed(); // API 24+
            // Alternativa manual: comparador = (a1, a2) -> a2.getNome().compareToIgnoreCase(a1.getNome());
        }

        try {
            Collections.sort(listaFiltrada, comparador);
            adapter.notifyDataSetChanged();
            Log.d(TAG, "ordenarLista: Lista ordenada e adapter notificado.");
            String ordem = ascendente ? "A-Z" : "Z-A";
            Toast.makeText(this, "Lista ordenada (" + ordem + ")", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "ordenarLista: Erro durante a ordenação", e);
            Toast.makeText(this, "Erro ao ordenar a lista.", Toast.LENGTH_SHORT).show();
        }
    }

    // --- Lógica de Pesquisa ---
    // Este método é chamado pelo android:onClick="pesquisarAluno" no XML
    public void pesquisarAluno(View view) {
        String textoPesquisa = edtPesquisaNome.getText().toString().trim(); // Não converter para lower case aqui ainda
        Log.d(TAG, "pesquisarAluno: Pesquisando por '" + textoPesquisa + "'");

        listaFiltrada.clear(); // Limpa a lista que está sendo exibida

        if (textoPesquisa.isEmpty()) {
            Log.d(TAG, "pesquisarAluno: Pesquisa vazia, adicionando todos os alunos.");
            listaFiltrada.addAll(listaAlunos); // Adiciona todos da lista original completa
        } else {
            Log.d(TAG, "pesquisarAluno: Aplicando filtro.");
            String textoPesquisaLower = textoPesquisa.toLowerCase(); // Converte para lower case para comparação
            for (Aluno aluno : listaAlunos) { // Itera sobre a lista original completa
                if (aluno != null && aluno.getNome() != null && aluno.getNome().toLowerCase().contains(textoPesquisaLower)) {
                    listaFiltrada.add(aluno);
                }
            }
            Log.d(TAG, "pesquisarAluno: Filtro aplicado, " + listaFiltrada.size() + " alunos encontrados.");
        }

        adapter.notifyDataSetChanged(); // Notifica o adapter sobre a mudança na listaFiltrada

        if (!textoPesquisa.isEmpty() && listaFiltrada.isEmpty()) {
            Toast.makeText(this, "Nenhum aluno encontrado.", Toast.LENGTH_SHORT).show();
        }
    }


    // --- Menu de Contexto ---
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewAlunos) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_contexto, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        try {
            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        } catch (ClassCastException e) {
            Log.e(TAG, "onContextItemSelected: Não foi possível obter AdapterContextMenuInfo", e);
            return super.onContextItemSelected(item);
        }

        if (info == null || info.position < 0 || info.position >= listaFiltrada.size()) {
            Log.w(TAG, "onContextItemSelected: Posição inválida ou info nulo.");
            return super.onContextItemSelected(item);
        }

        int position = info.position;
        Aluno alunoSelecionado = listaFiltrada.get(position); // Pega da lista filtrada!
        Log.d(TAG, "onContextItemSelected: Aluno selecionado: " + alunoSelecionado.getNome());

        int itemId = item.getItemId();

        if (itemId == R.id.menu_excluir) {
            Log.d(TAG, "onContextItemSelected: Opção Excluir selecionada.");
            confirmarExclusao(alunoSelecionado);
            return true;
        } else if (itemId == R.id.menu_atualizar) {
            Log.d(TAG, "onContextItemSelected: Opção Atualizar selecionada.");
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("aluno", alunoSelecionado); // Aluno precisa ser Serializable
            startActivity(intent);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void confirmarExclusao(Aluno aluno) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar Exclusão")
                .setMessage("Tem certeza que deseja excluir o aluno " + aluno.getNome() + "?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    try {
                        alunoDao.excluir(aluno);
                        Log.d(TAG, "confirmarExclusao: Aluno excluído do DAO: " + aluno.getNome());
                        // Remove das listas em memória para atualizar a UI imediatamente
                        boolean removedOriginal = listaAlunos.remove(aluno);
                        boolean removedFiltrada = listaFiltrada.remove(aluno);
                        Log.d(TAG, "confirmarExclusao: Removido da listaAlunos? " + removedOriginal + ". Removido da listaFiltrada? " + removedFiltrada);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Aluno excluído!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(TAG, "confirmarExclusao: Erro ao excluir aluno", e);
                        Toast.makeText(this, "Erro ao excluir aluno: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Não", (dialog, which) -> Log.d(TAG, "confirmarExclusao: Exclusão cancelada."))
                .show();
    }


    // --- Ciclo de Vida ---
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Recarregando e refiltrando lista.");
        // Recarrega a lista completa do banco
        try {
            listaAlunos = alunoDao.listar();
            if (listaAlunos == null) {
                Log.w(TAG, "onResume: alunoDao.listar() retornou null.");
                listaAlunos = new ArrayList<>();
            }
        } catch (Exception e) {
            Log.e(TAG, "onResume: Erro ao chamar alunoDao.listar()", e);
            Toast.makeText(this, "Erro ao recarregar alunos do banco.", Toast.LENGTH_SHORT).show();
            listaAlunos = new ArrayList<>();
        }

        // Reaplica o filtro que estava no EditText (mantém o texto)
        pesquisarAluno(null); // A view é null, mas o método pega o texto do EditText
        // Nota: a ordem A-Z/Z-A será perdida aqui.
    }
}