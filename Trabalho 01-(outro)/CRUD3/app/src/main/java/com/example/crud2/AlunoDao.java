package com.example.crud2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public AlunoDao(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("foto", aluno.getFoto());
        return banco.insert("aluno", null, values);
    }

    public List<Aluno> listar() {
        List<Aluno> alunos = new ArrayList<>();
        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone", "foto"},
                null, null, null, null, "nome ASC");
        while (cursor.moveToNext()) {
            Aluno a = new Aluno();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setCpf(cursor.getString(2));
            a.setTelefone(cursor.getString(3));
            a.setFoto(cursor.getBlob(4));
            alunos.add(a);
        }
        cursor.close();
        return alunos;
    }

    public void excluir(Aluno aluno) {
        if (aluno.getId() != null) {
            banco.delete("aluno", "id = ?", new String[]{aluno.getId().toString()});
        }
    }

    public void atualizar(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("foto", aluno.getFoto());
        if (aluno.getId() != null) {
            banco.update("aluno", values, "id = ?", new String[]{aluno.getId().toString()});
        }
    }

    public boolean validaCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }
        return true; // Validação simplificada; adicione lógica real se necessário
    }
}