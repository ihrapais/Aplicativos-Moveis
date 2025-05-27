import 'package:drift/drift.dart';
import 'connection.dart';

part 'app_database.g.dart';

// Definição da tabela Leitores
@DataClassName('Leitor')
class Leitores extends Table {
  IntColumn get id => integer().autoIncrement()();
  TextColumn get nome => text()();
  TextColumn get email => text()();
  TextColumn get telefone => text().nullable()();
  TextColumn get generoLiterarioPreferido => text().nullable()();
  TextColumn get frequenciaParticipacao => text().nullable()();
  DateTimeColumn get dataInscricao => dateTime()();
}

// Define a classe do banco de dados
@DriftDatabase(tables: [Leitores])
class AppDatabase extends _$AppDatabase {
  AppDatabase() : super(openConnection());

  @override
  int get schemaVersion => 1;

  // --- Métodos CRUD (Create, Read, Update, Delete) ---

  // CREATE: Inserir um novo leitor
  Future<int> inserirLeitor(LeitoresCompanion leitor) {
    return into(leitores).insert(leitor);
  }

  // READ: Listar todos os leitores (lista futura)
  Future<List<Leitor>> listarLeitores() {
    return select(leitores).get();
  }

  // READ: Obter um leitor específico pelo ID
  Future<Leitor?> getLeitorPorId(int id) {
    return (select(
      leitores,
    )..where((tbl) => tbl.id.equals(id))).getSingleOrNull();
  }

  // UPDATE: Atualizar um leitor existente
  Future<bool> atualizarLeitor(Leitor leitor) {
    return update(leitores).replace(leitor);
  }

  // DELETE: Excluir um leitor pelo ID
  Future<int> excluirLeitor(int id) {
    return (delete(leitores)..where((tbl) => tbl.id.equals(id))).go();
  }

  // WATCH: Listar todos os leitores (reativo)
  Stream<List<Leitor>> watchTodosLeitores() {
    return select(leitores).watch();
  }
}
