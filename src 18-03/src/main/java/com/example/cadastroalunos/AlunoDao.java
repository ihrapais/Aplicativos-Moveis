package com.example.cadastroalunos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class AlunoDao {
    private SQLiteDatabase banco;

    public AlunoDao(Context context) {
        Conexao conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        return banco.insert("aluno", null, values);
    }

    public List<Aluno> obterTodos() {
        return java.util.Collections.emptyList();
    }
}