package com.example.implementapet;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Pet.class}, version = 2) // Incrementado para versão 2
public abstract class AppDatabase extends RoomDatabase {
    public abstract PetDao petDao();

    private static AppDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Criar nova tabela com estrutura atualizada
            database.execSQL("CREATE TABLE IF NOT EXISTS Pet_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, cpf TEXT NOT NULL, nome TEXT, telefone TEXT)");
            // Copiar dados da tabela antiga
            database.execSQL("INSERT INTO Pet_new (cpf, nome, telefone) SELECT cpf, nome, telefone FROM Pet");
            // Remover tabela antiga
            database.execSQL("DROP TABLE Pet");
            // Renomear nova tabela
            database.execSQL("ALTER TABLE Pet_new RENAME TO Pet");
        }
    };

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "banco-de-dados")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }
}