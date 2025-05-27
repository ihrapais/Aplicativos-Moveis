// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'app_database.dart';

// ignore_for_file: type=lint
class $LeitoresTable extends Leitores with TableInfo<$LeitoresTable, Leitor> {
  @override
  final GeneratedDatabase attachedDatabase;
  final String? _alias;
  $LeitoresTable(this.attachedDatabase, [this._alias]);
  static const VerificationMeta _idMeta = const VerificationMeta('id');
  @override
  late final GeneratedColumn<int> id = GeneratedColumn<int>(
    'id',
    aliasedName,
    false,
    hasAutoIncrement: true,
    type: DriftSqlType.int,
    requiredDuringInsert: false,
    defaultConstraints: GeneratedColumn.constraintIsAlways(
      'PRIMARY KEY AUTOINCREMENT',
    ),
  );
  static const VerificationMeta _nomeMeta = const VerificationMeta('nome');
  @override
  late final GeneratedColumn<String> nome = GeneratedColumn<String>(
    'nome',
    aliasedName,
    false,
    type: DriftSqlType.string,
    requiredDuringInsert: true,
  );
  static const VerificationMeta _emailMeta = const VerificationMeta('email');
  @override
  late final GeneratedColumn<String> email = GeneratedColumn<String>(
    'email',
    aliasedName,
    false,
    type: DriftSqlType.string,
    requiredDuringInsert: true,
  );
  static const VerificationMeta _telefoneMeta = const VerificationMeta(
    'telefone',
  );
  @override
  late final GeneratedColumn<String> telefone = GeneratedColumn<String>(
    'telefone',
    aliasedName,
    true,
    type: DriftSqlType.string,
    requiredDuringInsert: false,
  );
  static const VerificationMeta _generoLiterarioPreferidoMeta =
      const VerificationMeta('generoLiterarioPreferido');
  @override
  late final GeneratedColumn<String> generoLiterarioPreferido =
      GeneratedColumn<String>(
        'genero_literario_preferido',
        aliasedName,
        true,
        type: DriftSqlType.string,
        requiredDuringInsert: false,
      );
  static const VerificationMeta _frequenciaParticipacaoMeta =
      const VerificationMeta('frequenciaParticipacao');
  @override
  late final GeneratedColumn<String> frequenciaParticipacao =
      GeneratedColumn<String>(
        'frequencia_participacao',
        aliasedName,
        true,
        type: DriftSqlType.string,
        requiredDuringInsert: false,
      );
  static const VerificationMeta _dataInscricaoMeta = const VerificationMeta(
    'dataInscricao',
  );
  @override
  late final GeneratedColumn<DateTime> dataInscricao =
      GeneratedColumn<DateTime>(
        'data_inscricao',
        aliasedName,
        false,
        type: DriftSqlType.dateTime,
        requiredDuringInsert: true,
      );
  @override
  List<GeneratedColumn> get $columns => [
    id,
    nome,
    email,
    telefone,
    generoLiterarioPreferido,
    frequenciaParticipacao,
    dataInscricao,
  ];
  @override
  String get aliasedName => _alias ?? actualTableName;
  @override
  String get actualTableName => $name;
  static const String $name = 'leitores';
  @override
  VerificationContext validateIntegrity(
    Insertable<Leitor> instance, {
    bool isInserting = false,
  }) {
    final context = VerificationContext();
    final data = instance.toColumns(true);
    if (data.containsKey('id')) {
      context.handle(_idMeta, id.isAcceptableOrUnknown(data['id']!, _idMeta));
    }
    if (data.containsKey('nome')) {
      context.handle(
        _nomeMeta,
        nome.isAcceptableOrUnknown(data['nome']!, _nomeMeta),
      );
    } else if (isInserting) {
      context.missing(_nomeMeta);
    }
    if (data.containsKey('email')) {
      context.handle(
        _emailMeta,
        email.isAcceptableOrUnknown(data['email']!, _emailMeta),
      );
    } else if (isInserting) {
      context.missing(_emailMeta);
    }
    if (data.containsKey('telefone')) {
      context.handle(
        _telefoneMeta,
        telefone.isAcceptableOrUnknown(data['telefone']!, _telefoneMeta),
      );
    }
    if (data.containsKey('genero_literario_preferido')) {
      context.handle(
        _generoLiterarioPreferidoMeta,
        generoLiterarioPreferido.isAcceptableOrUnknown(
          data['genero_literario_preferido']!,
          _generoLiterarioPreferidoMeta,
        ),
      );
    }
    if (data.containsKey('frequencia_participacao')) {
      context.handle(
        _frequenciaParticipacaoMeta,
        frequenciaParticipacao.isAcceptableOrUnknown(
          data['frequencia_participacao']!,
          _frequenciaParticipacaoMeta,
        ),
      );
    }
    if (data.containsKey('data_inscricao')) {
      context.handle(
        _dataInscricaoMeta,
        dataInscricao.isAcceptableOrUnknown(
          data['data_inscricao']!,
          _dataInscricaoMeta,
        ),
      );
    } else if (isInserting) {
      context.missing(_dataInscricaoMeta);
    }
    return context;
  }

  @override
  Set<GeneratedColumn> get $primaryKey => {id};
  @override
  Leitor map(Map<String, dynamic> data, {String? tablePrefix}) {
    final effectivePrefix = tablePrefix != null ? '$tablePrefix.' : '';
    return Leitor(
      id: attachedDatabase.typeMapping.read(
        DriftSqlType.int,
        data['${effectivePrefix}id'],
      )!,
      nome: attachedDatabase.typeMapping.read(
        DriftSqlType.string,
        data['${effectivePrefix}nome'],
      )!,
      email: attachedDatabase.typeMapping.read(
        DriftSqlType.string,
        data['${effectivePrefix}email'],
      )!,
      telefone: attachedDatabase.typeMapping.read(
        DriftSqlType.string,
        data['${effectivePrefix}telefone'],
      ),
      generoLiterarioPreferido: attachedDatabase.typeMapping.read(
        DriftSqlType.string,
        data['${effectivePrefix}genero_literario_preferido'],
      ),
      frequenciaParticipacao: attachedDatabase.typeMapping.read(
        DriftSqlType.string,
        data['${effectivePrefix}frequencia_participacao'],
      ),
      dataInscricao: attachedDatabase.typeMapping.read(
        DriftSqlType.dateTime,
        data['${effectivePrefix}data_inscricao'],
      )!,
    );
  }

  @override
  $LeitoresTable createAlias(String alias) {
    return $LeitoresTable(attachedDatabase, alias);
  }
}

class Leitor extends DataClass implements Insertable<Leitor> {
  final int id;
  final String nome;
  final String email;
  final String? telefone;
  final String? generoLiterarioPreferido;
  final String? frequenciaParticipacao;
  final DateTime dataInscricao;
  const Leitor({
    required this.id,
    required this.nome,
    required this.email,
    this.telefone,
    this.generoLiterarioPreferido,
    this.frequenciaParticipacao,
    required this.dataInscricao,
  });
  @override
  Map<String, Expression> toColumns(bool nullToAbsent) {
    final map = <String, Expression>{};
    map['id'] = Variable<int>(id);
    map['nome'] = Variable<String>(nome);
    map['email'] = Variable<String>(email);
    if (!nullToAbsent || telefone != null) {
      map['telefone'] = Variable<String>(telefone);
    }
    if (!nullToAbsent || generoLiterarioPreferido != null) {
      map['genero_literario_preferido'] = Variable<String>(
        generoLiterarioPreferido,
      );
    }
    if (!nullToAbsent || frequenciaParticipacao != null) {
      map['frequencia_participacao'] = Variable<String>(frequenciaParticipacao);
    }
    map['data_inscricao'] = Variable<DateTime>(dataInscricao);
    return map;
  }

  LeitoresCompanion toCompanion(bool nullToAbsent) {
    return LeitoresCompanion(
      id: Value(id),
      nome: Value(nome),
      email: Value(email),
      telefone: telefone == null && nullToAbsent
          ? const Value.absent()
          : Value(telefone),
      generoLiterarioPreferido: generoLiterarioPreferido == null && nullToAbsent
          ? const Value.absent()
          : Value(generoLiterarioPreferido),
      frequenciaParticipacao: frequenciaParticipacao == null && nullToAbsent
          ? const Value.absent()
          : Value(frequenciaParticipacao),
      dataInscricao: Value(dataInscricao),
    );
  }

  factory Leitor.fromJson(
    Map<String, dynamic> json, {
    ValueSerializer? serializer,
  }) {
    serializer ??= driftRuntimeOptions.defaultSerializer;
    return Leitor(
      id: serializer.fromJson<int>(json['id']),
      nome: serializer.fromJson<String>(json['nome']),
      email: serializer.fromJson<String>(json['email']),
      telefone: serializer.fromJson<String?>(json['telefone']),
      generoLiterarioPreferido: serializer.fromJson<String?>(
        json['generoLiterarioPreferido'],
      ),
      frequenciaParticipacao: serializer.fromJson<String?>(
        json['frequenciaParticipacao'],
      ),
      dataInscricao: serializer.fromJson<DateTime>(json['dataInscricao']),
    );
  }
  @override
  Map<String, dynamic> toJson({ValueSerializer? serializer}) {
    serializer ??= driftRuntimeOptions.defaultSerializer;
    return <String, dynamic>{
      'id': serializer.toJson<int>(id),
      'nome': serializer.toJson<String>(nome),
      'email': serializer.toJson<String>(email),
      'telefone': serializer.toJson<String?>(telefone),
      'generoLiterarioPreferido': serializer.toJson<String?>(
        generoLiterarioPreferido,
      ),
      'frequenciaParticipacao': serializer.toJson<String?>(
        frequenciaParticipacao,
      ),
      'dataInscricao': serializer.toJson<DateTime>(dataInscricao),
    };
  }

  Leitor copyWith({
    int? id,
    String? nome,
    String? email,
    Value<String?> telefone = const Value.absent(),
    Value<String?> generoLiterarioPreferido = const Value.absent(),
    Value<String?> frequenciaParticipacao = const Value.absent(),
    DateTime? dataInscricao,
  }) => Leitor(
    id: id ?? this.id,
    nome: nome ?? this.nome,
    email: email ?? this.email,
    telefone: telefone.present ? telefone.value : this.telefone,
    generoLiterarioPreferido: generoLiterarioPreferido.present
        ? generoLiterarioPreferido.value
        : this.generoLiterarioPreferido,
    frequenciaParticipacao: frequenciaParticipacao.present
        ? frequenciaParticipacao.value
        : this.frequenciaParticipacao,
    dataInscricao: dataInscricao ?? this.dataInscricao,
  );
  Leitor copyWithCompanion(LeitoresCompanion data) {
    return Leitor(
      id: data.id.present ? data.id.value : this.id,
      nome: data.nome.present ? data.nome.value : this.nome,
      email: data.email.present ? data.email.value : this.email,
      telefone: data.telefone.present ? data.telefone.value : this.telefone,
      generoLiterarioPreferido: data.generoLiterarioPreferido.present
          ? data.generoLiterarioPreferido.value
          : this.generoLiterarioPreferido,
      frequenciaParticipacao: data.frequenciaParticipacao.present
          ? data.frequenciaParticipacao.value
          : this.frequenciaParticipacao,
      dataInscricao: data.dataInscricao.present
          ? data.dataInscricao.value
          : this.dataInscricao,
    );
  }

  @override
  String toString() {
    return (StringBuffer('Leitor(')
          ..write('id: $id, ')
          ..write('nome: $nome, ')
          ..write('email: $email, ')
          ..write('telefone: $telefone, ')
          ..write('generoLiterarioPreferido: $generoLiterarioPreferido, ')
          ..write('frequenciaParticipacao: $frequenciaParticipacao, ')
          ..write('dataInscricao: $dataInscricao')
          ..write(')'))
        .toString();
  }

  @override
  int get hashCode => Object.hash(
    id,
    nome,
    email,
    telefone,
    generoLiterarioPreferido,
    frequenciaParticipacao,
    dataInscricao,
  );
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (other is Leitor &&
          other.id == this.id &&
          other.nome == this.nome &&
          other.email == this.email &&
          other.telefone == this.telefone &&
          other.generoLiterarioPreferido == this.generoLiterarioPreferido &&
          other.frequenciaParticipacao == this.frequenciaParticipacao &&
          other.dataInscricao == this.dataInscricao);
}

class LeitoresCompanion extends UpdateCompanion<Leitor> {
  final Value<int> id;
  final Value<String> nome;
  final Value<String> email;
  final Value<String?> telefone;
  final Value<String?> generoLiterarioPreferido;
  final Value<String?> frequenciaParticipacao;
  final Value<DateTime> dataInscricao;
  const LeitoresCompanion({
    this.id = const Value.absent(),
    this.nome = const Value.absent(),
    this.email = const Value.absent(),
    this.telefone = const Value.absent(),
    this.generoLiterarioPreferido = const Value.absent(),
    this.frequenciaParticipacao = const Value.absent(),
    this.dataInscricao = const Value.absent(),
  });
  LeitoresCompanion.insert({
    this.id = const Value.absent(),
    required String nome,
    required String email,
    this.telefone = const Value.absent(),
    this.generoLiterarioPreferido = const Value.absent(),
    this.frequenciaParticipacao = const Value.absent(),
    required DateTime dataInscricao,
  }) : nome = Value(nome),
       email = Value(email),
       dataInscricao = Value(dataInscricao);
  static Insertable<Leitor> custom({
    Expression<int>? id,
    Expression<String>? nome,
    Expression<String>? email,
    Expression<String>? telefone,
    Expression<String>? generoLiterarioPreferido,
    Expression<String>? frequenciaParticipacao,
    Expression<DateTime>? dataInscricao,
  }) {
    return RawValuesInsertable({
      if (id != null) 'id': id,
      if (nome != null) 'nome': nome,
      if (email != null) 'email': email,
      if (telefone != null) 'telefone': telefone,
      if (generoLiterarioPreferido != null)
        'genero_literario_preferido': generoLiterarioPreferido,
      if (frequenciaParticipacao != null)
        'frequencia_participacao': frequenciaParticipacao,
      if (dataInscricao != null) 'data_inscricao': dataInscricao,
    });
  }

  LeitoresCompanion copyWith({
    Value<int>? id,
    Value<String>? nome,
    Value<String>? email,
    Value<String?>? telefone,
    Value<String?>? generoLiterarioPreferido,
    Value<String?>? frequenciaParticipacao,
    Value<DateTime>? dataInscricao,
  }) {
    return LeitoresCompanion(
      id: id ?? this.id,
      nome: nome ?? this.nome,
      email: email ?? this.email,
      telefone: telefone ?? this.telefone,
      generoLiterarioPreferido:
          generoLiterarioPreferido ?? this.generoLiterarioPreferido,
      frequenciaParticipacao:
          frequenciaParticipacao ?? this.frequenciaParticipacao,
      dataInscricao: dataInscricao ?? this.dataInscricao,
    );
  }

  @override
  Map<String, Expression> toColumns(bool nullToAbsent) {
    final map = <String, Expression>{};
    if (id.present) {
      map['id'] = Variable<int>(id.value);
    }
    if (nome.present) {
      map['nome'] = Variable<String>(nome.value);
    }
    if (email.present) {
      map['email'] = Variable<String>(email.value);
    }
    if (telefone.present) {
      map['telefone'] = Variable<String>(telefone.value);
    }
    if (generoLiterarioPreferido.present) {
      map['genero_literario_preferido'] = Variable<String>(
        generoLiterarioPreferido.value,
      );
    }
    if (frequenciaParticipacao.present) {
      map['frequencia_participacao'] = Variable<String>(
        frequenciaParticipacao.value,
      );
    }
    if (dataInscricao.present) {
      map['data_inscricao'] = Variable<DateTime>(dataInscricao.value);
    }
    return map;
  }

  @override
  String toString() {
    return (StringBuffer('LeitoresCompanion(')
          ..write('id: $id, ')
          ..write('nome: $nome, ')
          ..write('email: $email, ')
          ..write('telefone: $telefone, ')
          ..write('generoLiterarioPreferido: $generoLiterarioPreferido, ')
          ..write('frequenciaParticipacao: $frequenciaParticipacao, ')
          ..write('dataInscricao: $dataInscricao')
          ..write(')'))
        .toString();
  }
}

abstract class _$AppDatabase extends GeneratedDatabase {
  _$AppDatabase(QueryExecutor e) : super(e);
  $AppDatabaseManager get managers => $AppDatabaseManager(this);
  late final $LeitoresTable leitores = $LeitoresTable(this);
  @override
  Iterable<TableInfo<Table, Object?>> get allTables =>
      allSchemaEntities.whereType<TableInfo<Table, Object?>>();
  @override
  List<DatabaseSchemaEntity> get allSchemaEntities => [leitores];
}

typedef $$LeitoresTableCreateCompanionBuilder =
    LeitoresCompanion Function({
      Value<int> id,
      required String nome,
      required String email,
      Value<String?> telefone,
      Value<String?> generoLiterarioPreferido,
      Value<String?> frequenciaParticipacao,
      required DateTime dataInscricao,
    });
typedef $$LeitoresTableUpdateCompanionBuilder =
    LeitoresCompanion Function({
      Value<int> id,
      Value<String> nome,
      Value<String> email,
      Value<String?> telefone,
      Value<String?> generoLiterarioPreferido,
      Value<String?> frequenciaParticipacao,
      Value<DateTime> dataInscricao,
    });

class $$LeitoresTableFilterComposer
    extends Composer<_$AppDatabase, $LeitoresTable> {
  $$LeitoresTableFilterComposer({
    required super.$db,
    required super.$table,
    super.joinBuilder,
    super.$addJoinBuilderToRootComposer,
    super.$removeJoinBuilderFromRootComposer,
  });
  ColumnFilters<int> get id => $composableBuilder(
    column: $table.id,
    builder: (column) => ColumnFilters(column),
  );

  ColumnFilters<String> get nome => $composableBuilder(
    column: $table.nome,
    builder: (column) => ColumnFilters(column),
  );

  ColumnFilters<String> get email => $composableBuilder(
    column: $table.email,
    builder: (column) => ColumnFilters(column),
  );

  ColumnFilters<String> get telefone => $composableBuilder(
    column: $table.telefone,
    builder: (column) => ColumnFilters(column),
  );

  ColumnFilters<String> get generoLiterarioPreferido => $composableBuilder(
    column: $table.generoLiterarioPreferido,
    builder: (column) => ColumnFilters(column),
  );

  ColumnFilters<String> get frequenciaParticipacao => $composableBuilder(
    column: $table.frequenciaParticipacao,
    builder: (column) => ColumnFilters(column),
  );

  ColumnFilters<DateTime> get dataInscricao => $composableBuilder(
    column: $table.dataInscricao,
    builder: (column) => ColumnFilters(column),
  );
}

class $$LeitoresTableOrderingComposer
    extends Composer<_$AppDatabase, $LeitoresTable> {
  $$LeitoresTableOrderingComposer({
    required super.$db,
    required super.$table,
    super.joinBuilder,
    super.$addJoinBuilderToRootComposer,
    super.$removeJoinBuilderFromRootComposer,
  });
  ColumnOrderings<int> get id => $composableBuilder(
    column: $table.id,
    builder: (column) => ColumnOrderings(column),
  );

  ColumnOrderings<String> get nome => $composableBuilder(
    column: $table.nome,
    builder: (column) => ColumnOrderings(column),
  );

  ColumnOrderings<String> get email => $composableBuilder(
    column: $table.email,
    builder: (column) => ColumnOrderings(column),
  );

  ColumnOrderings<String> get telefone => $composableBuilder(
    column: $table.telefone,
    builder: (column) => ColumnOrderings(column),
  );

  ColumnOrderings<String> get generoLiterarioPreferido => $composableBuilder(
    column: $table.generoLiterarioPreferido,
    builder: (column) => ColumnOrderings(column),
  );

  ColumnOrderings<String> get frequenciaParticipacao => $composableBuilder(
    column: $table.frequenciaParticipacao,
    builder: (column) => ColumnOrderings(column),
  );

  ColumnOrderings<DateTime> get dataInscricao => $composableBuilder(
    column: $table.dataInscricao,
    builder: (column) => ColumnOrderings(column),
  );
}

class $$LeitoresTableAnnotationComposer
    extends Composer<_$AppDatabase, $LeitoresTable> {
  $$LeitoresTableAnnotationComposer({
    required super.$db,
    required super.$table,
    super.joinBuilder,
    super.$addJoinBuilderToRootComposer,
    super.$removeJoinBuilderFromRootComposer,
  });
  GeneratedColumn<int> get id =>
      $composableBuilder(column: $table.id, builder: (column) => column);

  GeneratedColumn<String> get nome =>
      $composableBuilder(column: $table.nome, builder: (column) => column);

  GeneratedColumn<String> get email =>
      $composableBuilder(column: $table.email, builder: (column) => column);

  GeneratedColumn<String> get telefone =>
      $composableBuilder(column: $table.telefone, builder: (column) => column);

  GeneratedColumn<String> get generoLiterarioPreferido => $composableBuilder(
    column: $table.generoLiterarioPreferido,
    builder: (column) => column,
  );

  GeneratedColumn<String> get frequenciaParticipacao => $composableBuilder(
    column: $table.frequenciaParticipacao,
    builder: (column) => column,
  );

  GeneratedColumn<DateTime> get dataInscricao => $composableBuilder(
    column: $table.dataInscricao,
    builder: (column) => column,
  );
}

class $$LeitoresTableTableManager
    extends
        RootTableManager<
          _$AppDatabase,
          $LeitoresTable,
          Leitor,
          $$LeitoresTableFilterComposer,
          $$LeitoresTableOrderingComposer,
          $$LeitoresTableAnnotationComposer,
          $$LeitoresTableCreateCompanionBuilder,
          $$LeitoresTableUpdateCompanionBuilder,
          (Leitor, BaseReferences<_$AppDatabase, $LeitoresTable, Leitor>),
          Leitor,
          PrefetchHooks Function()
        > {
  $$LeitoresTableTableManager(_$AppDatabase db, $LeitoresTable table)
    : super(
        TableManagerState(
          db: db,
          table: table,
          createFilteringComposer: () =>
              $$LeitoresTableFilterComposer($db: db, $table: table),
          createOrderingComposer: () =>
              $$LeitoresTableOrderingComposer($db: db, $table: table),
          createComputedFieldComposer: () =>
              $$LeitoresTableAnnotationComposer($db: db, $table: table),
          updateCompanionCallback:
              ({
                Value<int> id = const Value.absent(),
                Value<String> nome = const Value.absent(),
                Value<String> email = const Value.absent(),
                Value<String?> telefone = const Value.absent(),
                Value<String?> generoLiterarioPreferido = const Value.absent(),
                Value<String?> frequenciaParticipacao = const Value.absent(),
                Value<DateTime> dataInscricao = const Value.absent(),
              }) => LeitoresCompanion(
                id: id,
                nome: nome,
                email: email,
                telefone: telefone,
                generoLiterarioPreferido: generoLiterarioPreferido,
                frequenciaParticipacao: frequenciaParticipacao,
                dataInscricao: dataInscricao,
              ),
          createCompanionCallback:
              ({
                Value<int> id = const Value.absent(),
                required String nome,
                required String email,
                Value<String?> telefone = const Value.absent(),
                Value<String?> generoLiterarioPreferido = const Value.absent(),
                Value<String?> frequenciaParticipacao = const Value.absent(),
                required DateTime dataInscricao,
              }) => LeitoresCompanion.insert(
                id: id,
                nome: nome,
                email: email,
                telefone: telefone,
                generoLiterarioPreferido: generoLiterarioPreferido,
                frequenciaParticipacao: frequenciaParticipacao,
                dataInscricao: dataInscricao,
              ),
          withReferenceMapper: (p0) => p0
              .map((e) => (e.readTable(table), BaseReferences(db, table, e)))
              .toList(),
          prefetchHooksCallback: null,
        ),
      );
}

typedef $$LeitoresTableProcessedTableManager =
    ProcessedTableManager<
      _$AppDatabase,
      $LeitoresTable,
      Leitor,
      $$LeitoresTableFilterComposer,
      $$LeitoresTableOrderingComposer,
      $$LeitoresTableAnnotationComposer,
      $$LeitoresTableCreateCompanionBuilder,
      $$LeitoresTableUpdateCompanionBuilder,
      (Leitor, BaseReferences<_$AppDatabase, $LeitoresTable, Leitor>),
      Leitor,
      PrefetchHooks Function()
    >;

class $AppDatabaseManager {
  final _$AppDatabase _db;
  $AppDatabaseManager(this._db);
  $$LeitoresTableTableManager get leitores =>
      $$LeitoresTableTableManager(_db, _db.leitores);
}
