package com.example.crudaula2;

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
        if (!validaCpf(aluno.getCpf())) {
            throw new IllegalArgumentException("CPF inválido!");
        }
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        return banco.insert("aluno", null, values);
    }

    public List<Aluno> obterTodos() {
        List<Aluno> alunos = new ArrayList<>();
        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            Aluno a = new Aluno();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setCpf(cursor.getString(2));
            a.setTelefone(cursor.getString(3));
            alunos.add(a);
        }
        cursor.close();
        return alunos;
    }

    public boolean validaCpf(String cpf) {
        // Remove pontos, traços e espaços
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se é uma sequência repetida
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Cálculo do primeiro dígito verificador (D1)
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int resto = soma % 11;
        int d1 = (resto < 2) ? 0 : 11 - resto;
        if (d1 != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        // Cálculo do segundo dígito verificador (D2)
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        resto = soma % 11;
        int d2 = (resto < 2) ? 0 : 11 - resto;
        return d2 == Character.getNumericValue(cpf.charAt(10));
    }
}