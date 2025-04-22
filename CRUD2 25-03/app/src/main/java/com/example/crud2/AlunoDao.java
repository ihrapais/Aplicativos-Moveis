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

    // Create
    public long inserir(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("fotoBytes", aluno.getFotoBytes());
        return banco.insert("aluno", null, values);
    }

    // Read (obter todos)
    public List<Aluno> obterTodos() {
        List<Aluno> alunos = new ArrayList<>();
        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone", "fotoBytes"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            Aluno a = new Aluno();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setCpf(cursor.getString(2));
            a.setTelefone(cursor.getString(3));
            a.setFotoBytes(cursor.getBlob(4));
            alunos.add(a);
        }
        cursor.close();
        return alunos;
    }

    // Update
    public void atualizar(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("fotoBytes", aluno.getFotoBytes());
        banco.update("aluno", values, "id = ?", new String[]{aluno.getId().toString()});
    }

    // Delete
    public void excluir(Aluno aluno) {
        banco.delete("aluno", "id = ?", new String[]{aluno.getId().toString()});
    }

    // Validações (exemplo)
    public boolean validaCpf(String cpf) {
        return cpf.length() == 11; // Simplificado, adicione validação real se necessário
    }

    public boolean validaTelefone(String telefone) {
        return telefone.matches("\\(\\d{2}\\)\\s9\\d{4}-\\d{4}"); // Exemplo: (11) 91234-5678
    }

    public boolean cpfExistente(String cpf) {
        Cursor cursor = banco.rawQuery("SELECT 1 FROM aluno WHERE cpf = ?", new String[]{cpf});
        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }
}