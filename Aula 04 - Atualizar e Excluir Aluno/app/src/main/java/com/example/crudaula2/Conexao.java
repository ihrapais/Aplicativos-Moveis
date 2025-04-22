package com.example.crudaula2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {
    private static final String NAME = "banco.db";
    private static final int VERSION = 1;

    public Conexao(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE aluno (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR(50), cpf VARCHAR(14), telefone VARCHAR(15))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: Implementar se necessário
    }
}