import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {
    private static final String NAME = "banco.db";
    private static final int VERSION = 2; // Versão atualizada para suportar foto

    public Conexao(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE aluno (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome VARCHAR(50), " +
                "cpf VARCHAR(50), " +
                "telefone VARCHAR(50), " +
                "fotoBytes BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE aluno ADD COLUMN fotoBytes BLOB");
        }
    }
}