package com.example.crudaula2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao {
    private static final String TAG = "AlunoDao";
    private Conexao conexao;
    private SQLiteDatabase banco;

    public AlunoDao(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        Log.d(TAG, "AlunoDao: Banco de dados inicializado");
    }

    public long inserir(Aluno aluno) {
        Log.d(TAG, "inserir: Inserindo aluno: " + aluno.getNome());
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("fotoBytes", aluno.getFotoBytes()); // Adicionar foto
        long id = banco.insert("aluno", null, values);
        Log.d(TAG, "inserir: Inserção concluída com id: " + id);
        return id;
    }

    public List<Aluno> obterTodos() {
        Log.d(TAG, "obterTodos: Buscando todos os alunos");
        List<Aluno> alunos = new ArrayList<>();
        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone", "fotoBytes"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            Aluno a = new Aluno();
            a.setId(cursor.getLong(0));
            a.setNome(cursor.getString(1));
            a.setCpf(cursor.getString(2));
            a.setTelefone(cursor.getString(3));
            a.setFotoBytes(cursor.getBlob(4)); // Adicionar foto
            alunos.add(a);
        }
        cursor.close();
        Log.d(TAG, "obterTodos: " + alunos.size() + " alunos encontrados");
        return alunos;
    }

    public void excluir(Aluno a) {
        Log.d(TAG, "excluir: Excluindo aluno com id: " + a.getId());
        banco.delete("aluno", "id = ?", new String[]{a.getId().toString()});
        Log.d(TAG, "excluir: Aluno excluído");
    }

    public void atualizar(Aluno aluno) {
        Log.d(TAG, "atualizar: Atualizando aluno com id: " + aluno.getId());
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("fotoBytes", aluno.getFotoBytes()); // Adicionar foto
        int rowsAffected = banco.update("aluno", values, "id = ?", new String[]{aluno.getId().toString()});
        Log.d(TAG, "atualizar: Linhas afetadas: " + rowsAffected);
    }

    public boolean validaCpf(String cpf) {
        Log.d(TAG, "validaCpf: Validando CPF: " + cpf);
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            Log.w(TAG, "validaCpf: CPF com tamanho inválido: " + cpf.length());
            return false;
        }
        boolean todosIguais = true;
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                todosIguais = false;
                break;
            }
        }
        if (todosIguais) {
            Log.w(TAG, "validaCpf: CPF com todos os dígitos iguais");
            return false;
        }
        try {
            int[] pesos = {10, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Integer.parseInt(cpf.substring(i, i + 1)) * pesos[i];
            }
            int resto = soma % 11;
            int digito1 = resto < 2 ? 0 : 11 - resto;
            if (digito1 != Integer.parseInt(cpf.substring(9, 10))) {
                Log.w(TAG, "validaCpf: Primeiro dígito verificador inválido");
                return false;
            }
            int[] pesos2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Integer.parseInt(cpf.substring(i, i + 1)) * pesos2[i];
            }
            resto = soma % 11;
            int digito2 = resto < 2 ? 0 : 11 - resto;
            boolean valido = digito2 == Integer.parseInt(cpf.substring(10, 11));
            Log.d(TAG, "validaCpf: CPF válido: " + valido);
            return valido;
        } catch (Exception e) {
            Log.e(TAG, "validaCpf: Erro ao validar CPF: " + e.getMessage());
            return false;
        }
    }

    public boolean cpfExistente(String cpf) {
        Log.d(TAG, "cpfExistente: Verificando CPF: " + cpf);
        Cursor cursor = banco.rawQuery("SELECT cpf FROM aluno WHERE cpf = ?", new String[]{cpf});
        boolean existe = cursor.moveToFirst();
        cursor.close();
        Log.d(TAG, "cpfExistente: CPF existe: " + existe);
        return existe;
    }

    public boolean validaTelefone(String telefone) {
        Log.d(TAG, "validaTelefone: Validando telefone: " + telefone);
        String regex = "\\(\\d{2}\\)\\s9\\d{4}-\\d{4}";
        boolean valido = telefone.matches(regex);
        Log.d(TAG, "validaTelefone: Telefone válido: " + valido);
        return valido;
    }
}