import 'dart:io';
import 'package:drift/drift.dart';
import 'package:drift/native.dart'; // Para NativeDatabase
import 'package:path_provider/path_provider.dart';
import 'package:path/path.dart' as p;

part 'app_database.g.dart'; // Arquivo gerado pelo build_runner

// Define a tabela 'Leitores'
@DataClassName('Leitor')
class Leitores extends Table {
  IntColumn get id => integer().autoIncrement()();
  TextColumn get nome => text()();
  TextColumn get email => text()(); // Campo de email
  TextColumn get telefone => text().nullable()(); // Opcional
  TextColumn get generoLiterarioPreferido => text().nullable()(); // Opcional
  TextColumn get frequenciaParticipacao => text().nullable()(); // Opcional
  DateTimeColumn get dataInscricao => dateTime()();

  // Para tornar o email único (opcional, requer migração se adicionado depois):
  // @override
  // List<String> get customConstraints => ['UNIQUE (email)'];
}

// Classe principal do banco de dados
@DriftDatabase(tables: [Leitores])
class AppDatabase extends _$AppDatabase {
  AppDatabase() : super(_openConnection());

  @override
  int get schemaVersion => 1; // Incremente ao mudar a estrutura da tabela

  // Métodos CRUD (Criar, Ler, Atualizar, Deletar)

  // Ler: Observar todos os leitores (para StreamBuilder)
  Stream<List<Leitor>> watchTodosLeitores() => select(leitores).watch();

  // Ler: Obter um leitor por ID (operação única)
  Future<Leitor?> getLeitorPorId(int id) {
    return (select(leitores)..where((tbl) => tbl.id.equals(id)))
        .getSingleOrNull();
  }

  // Criar: Inserir um novo leitor
  Future<int> inserirLeitor(LeitoresCompanion leitorData) {
    // O Drift usa LeitoresCompanion para inserção, permitindo campos opcionais
    return into(leitores).insert(leitorData);
  }

  // Atualizar: Atualizar um leitor existente
  Future<bool> atualizarLeitor(LeitoresCompanion leitorData) {
    // O Drift usa o 'id' no LeitoresCompanion para saber qual registro atualizar
    return update(leitores).replace(leitorData);
  }

  // Deletar: Excluir um leitor por ID
  Future<int> excluirLeitor(int id) {
    return (delete(leitores)..where((tbl) => tbl.id.equals(id))).go();
  }
}

// Função helper para abrir a conexão com o banco
LazyDatabase _openConnection() {
  return LazyDatabase(() async {
    final dbFolder = await getApplicationDocumentsDirectory();
    final file = File(
        p.join(dbFolder.path, 'leitores_clube.db')); // Nome do arquivo do banco
    // Usar createInBackground para operações de I/O não bloquearem a UI
    return NativeDatabase.createInBackground(file);
  });
}
