package com.example.supermercadoapp;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Produto.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProdutoDao produtoDao();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "banco-supermercado")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}