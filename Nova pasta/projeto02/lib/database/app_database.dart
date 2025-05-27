import 'dart:io';
import 'package:drift/drift.dart';
import 'package:drift/native.dart';
import 'package:path_provider/path_provider.dart';
import 'package:path/path.dart' as p;

// Define o nome do arquivo gerado pelo Drift.
// Deve ser 'app_database.g.dart', onde 'app_database' é o nome deste arquivo.
part 'app_database.g.dart';

// Define a tabela Leitores
@DataClassName('Leitor') // Nome da classe de dados que será gerada
class Leitores extends Table {
  // Chave primária auto-incrementada
  IntColumn get id => integer().autoIncrement()();

  // Nome do leitor - não pode ser nulo
  TextColumn get nome => text()();

  // Email do leitor - não pode ser nulo
  TextColumn get email => text()();

  // Telefone do leitor - pode ser nulo
  TextColumn get telefone => text().nullable()();

  // Gênero literário preferido - pode ser nulo
  TextColumn get generoLiterarioPreferido => text().nullable()();

  // Frequência de participação - pode ser nulo
  TextColumn get frequenciaParticipacao => text().nullable()();

  // Data de inscrição - não pode ser nula
  DateTimeColumn get dataInscricao => dateTime()();

  // Adicionando uma restrição de unicidade para o email, se desejado
  // @override
  // Set<Column> get primaryKey => {id}; // id já é chave primária por padrão
  // descomente a linha abaixo se quiser que o email seja único
  // @override
  // List<String> get customConstraints => ['UNIQUE (email)'];
}

// Define a classe do banco de dados
@DriftDatabase(tables: [Leitores])
class AppDatabase extends _$AppDatabase {
  AppDatabase() : super(_openConnection());

  // O schemaVersion é importante para migrações futuras.
  // Incremente este número se você alterar a estrutura da tabela.
  @override
  int get schemaVersion => 1;

  // Métodos CRUD para a tabela Leitores

  // Listar todos os leitores (reativo)
  Stream<List<Leitor>> watchTodosLeitores() => select(leitores).watch();

  // Obter um leitor pelo ID
  Future<Leitor?> getLeitorPorId(int id) {
    return (select(leitores)..where((tbl) => tbl.id.equals(id)))
        .getSingleOrNull();
  }

  // Inserir um novo leitor
  Future<int> inserirLeitor(LeitoresCompanion leitor) {
    return into(leitores).insert(leitor);
  }

  // Atualizar um leitor existente
  Future<bool> atualizarLeitor(LeitoresCompanion leitor) {
    // Certifique-se de que o 'id' está presente no companion para a atualização
    // O Drift usa o 'id' na cláusula WHERE para a atualização.
    return update(leitores).replace(leitor);
  }

  // Excluir um leitor pelo ID
  Future<int> excluirLeitor(int id) {
    return (delete(leitores)..where((tbl) => tbl.id.equals(id))).go();
  }
}

// Função helper para abrir a conexão com o banco de dados
LazyDatabase _openConnection() {
  return LazyDatabase(() async {
    final dbFolder = await getApplicationDocumentsDirectory();
    final file = File(p.join(dbFolder.path, 'db_leitores.sqlite'));
    return NativeDatabase.createInBackground(file);
  });
}
