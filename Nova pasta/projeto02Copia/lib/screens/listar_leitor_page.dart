import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:intl/intl.dart';
import '../database/app_database.dart';
import 'cadastro_leitor_page.dart'; // Verifique o nome do arquivo/classe

class ListarLeitoresPage extends StatefulWidget {
  const ListarLeitoresPage({super.key});

  @override
  State<ListarLeitoresPage> createState() => _ListarLeitoresPageState();
}

class _ListarLeitoresPageState extends State<ListarLeitoresPage> {
  AppDatabase? _database;
  Stream<List<Leitor>>? _leitoresStream;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    final dbProvider = Provider.of<AppDatabase>(context, listen: false);
    if (_database != dbProvider || _leitoresStream == null) {
      _database = dbProvider;
      if (_database != null) {
        _leitoresStream = _database!.watchTodosLeitores();
      }
    }
  }

  Future<void> _navegarParaCadastro([Leitor? leitor]) async {
    if (!mounted) return;
    await Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => CadastroLeitorPage(leitor: leitor),
      ),
    );
    // setState(() {}); // Mantido da sua estrutura original, mas geralmente não é necessário
  }

  Future<void> _confirmarEExcluirLeitor(Leitor leitor) async {
    if (!mounted) return;
    final bool? confirmado = await showDialog<bool>(
      context: context,
      builder: (BuildContext dialogContext) {
        return AlertDialog(
          title: const Text('Confirmar Exclusão'),
          content: Text('Excluir "${leitor.nome}"?'),
          actions: <Widget>[
            TextButton(
              child: const Text('Cancelar'),
              onPressed: () => Navigator.of(dialogContext).pop(false),
            ),
            TextButton(
              style: TextButton.styleFrom(foregroundColor: Colors.red),
              child: const Text('Excluir'),
              onPressed: () => Navigator.of(dialogContext).pop(true),
            ),
          ],
        );
      },
    );

    if (confirmado == true && mounted && _database != null) {
      try {
        // Linha 51 do seu erro original poderia ser uma chamada incorreta aqui.
        // A chamada abaixo usa o método correto do AppDatabase.
        await _database!
            .excluirLeitor(leitor.id); // Usa o método de AppDatabase
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Leitor "${leitor.nome}" excluído.')),
          );
        }
      } catch (e) {
        if (mounted) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Erro ao excluir: $e')),
          );
        }
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    // Garante que _database e _leitoresStream sejam inicializados se não foram em didChangeDependencies
    if (_database == null || _leitoresStream == null) {
      final dbProvider = Provider.of<AppDatabase>(context, listen: false);
      _database = dbProvider;
      if (_database != null) {
        _leitoresStream = _database!.watchTodosLeitores();
      } else {
        // Erro crítico se o database não puder ser obtido
        return Scaffold(
          appBar: AppBar(title: const Text('Leitores Cadastrados')),
          body: const Center(
              child: Text("ERRO: Banco de dados não encontrado via Provider.")),
        );
      }
    }

    return Scaffold(
      appBar: AppBar(title: const Text('Leitores Cadastrados')),
      body: StreamBuilder<List<Leitor>>(
        stream: _leitoresStream,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting &&
              !snapshot.hasData) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasError) {
            return Center(
                child: Text('Erro ao carregar leitores: ${snapshot.error}'));
          }
          final leitores = snapshot.data ?? [];
          if (leitores.isEmpty) {
            return const Center(child: Text('Nenhum leitor cadastrado.'));
          }
          return ListView.builder(
            itemCount: leitores.length,
            itemBuilder: (context, index) {
              final leitor = leitores[index];
              return ListTile(
                title: Text(leitor.nome),
                subtitle: Text(
                  'Email: ${leitor.email}\n'
                  'Gênero: ${leitor.generoLiterarioPreferido ?? "N/A"} | '
                  'Frequência: ${leitor.frequenciaParticipacao ?? "N/A"}\n'
                  'Inscrito em: ${DateFormat('dd/MM/yyyy', 'pt_BR').format(leitor.dataInscricao)}',
                ),
                isThreeLine: true,
                trailing: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.edit),
                      onPressed: () => _navegarParaCadastro(leitor),
                    ),
                    IconButton(
                      icon: const Icon(Icons.delete),
                      color: Colors.redAccent,
                      onPressed: () => _confirmarEExcluirLeitor(leitor),
                    ),
                  ],
                ),
                onTap: () => _navegarParaCadastro(leitor),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _navegarParaCadastro(),
        child: const Icon(Icons.add),
      ),
    );
  }
}
