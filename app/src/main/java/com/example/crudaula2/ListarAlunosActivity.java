package com.example.crudaula2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity {
    private static final String TAG = "ListarAlunosActivity";
    private ListView listaAlunos;
    private Button btnVoltar;
    private AlunoDao dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();
    private ArrayAdapter<Aluno> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Carregando layout R.layout.activity_listar_alunos");
        try {
            setContentView(R.layout.activity_listar_alunos);
            Toast.makeText(this, "ListarAlunosActivity iniciada", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreate: Iniciando ListarAlunosActivity");

            listaAlunos = findViewById(R.id.lista_alunos);
            btnVoltar = findViewById(R.id.btnVoltar);
            dao = new AlunoDao(this);
            alunos = dao.obterTodos();
            alunosFiltrados.addAll(alunos);
            adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunosFiltrados);
            listaAlunos.setAdapter(adaptador);
            registerForContextMenu(listaAlunos);

            if (btnVoltar != null) {
                btnVoltar.setOnClickListener(v -> {
                    Log.d(TAG, "btnVoltar: Retornando à Activity anterior");
                    Toast.makeText(this, "Retornando à MainActivity", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                Log.e(TAG, "onCreate: btnVoltar é nulo");
                Toast.makeText(this, "Erro: Botão Voltar não encontrado", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Erro durante inicialização: " + e.getMessage(), e);
            Toast.makeText(this, "Erro ao iniciar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoExcluir = alunosFiltrados.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir o aluno?")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alunosFiltrados.remove(alunoExcluir);
                        alunos.remove(alunoExcluir);
                        dao.excluir(alunoExcluir);
                        adaptador.notifyDataSetChanged();
                    }
                }).create();
        dialog.show();
    }

    public void atualizar(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoAtualizar = alunosFiltrados.get(menuInfo.position);
        Log.d(TAG, "atualizar: Aluno selecionado para atualização - ID: " + alunoAtualizar.getId());
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("aluno", alunoAtualizar);
        startActivity(it);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_excluir) {
            excluir(item);
            return true;
        } else if (item.getItemId() == R.id.menu_atualizar) {
            atualizar(item);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            alunos = dao.obterTodos();
            alunosFiltrados.clear();
            alunosFiltrados.addAll(alunos);
            Log.d(TAG, "onResume: Alunos carregados: " + alunosFiltrados.size());
            adaptador.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "onResume: Erro ao recarregar lista: " + e.getMessage(), e);
            Toast.makeText(this, "Erro ao recarregar lista: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}