import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import '../database/app_database.dart';
import 'cadastro_leitor_page.dart';

class ListarLeitoresPage extends StatefulWidget {
  const ListarLeitoresPage({super.key});

  @override
  State<ListarLeitoresPage> createState() => _ListarLeitoresPageState();
}

class _ListarLeitoresPageState extends State<ListarLeitoresPage> {
  @override
  Widget build(BuildContext context) {
    final database = Provider.of<AppDatabase>(context);

    return Scaffold(
      appBar: AppBar(title: const Text('Leitores Cadastrados')),
      body: StreamBuilder<List<Leitor>>(
        stream: database.watchTodosLeitores(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasError) {
            return Center(
              child: Text('Erro ao carregar leitores: ${snapshot.error}'),
            );
          }
          final leitores = snapshot.data ?? [];

          if (leitores.isEmpty) {
            return const Center(child: Text('Nenhum leitor cadastrado ainda.'));
          }

          return ListView.builder(
            itemCount: leitores.length,
            itemBuilder: (context, index) {
              final leitor = leitores[index];
              return Card(
                margin: const EdgeInsets.symmetric(
                  horizontal: 8.0,
                  vertical: 4.0,
                ),
                child: ListTile(
                  title: Text(
                    leitor.nome,
                    style: const TextStyle(fontWeight: FontWeight.bold),
                  ),
                  subtitle: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('Email: ${leitor.email}'),
                      if (leitor.telefone != null &&
                          leitor.telefone!.isNotEmpty)
                        Text('Telefone: ${leitor.telefone}'),
                      if (leitor.generoLiterarioPreferido != null &&
                          leitor.generoLiterarioPreferido!.isNotEmpty)
                        Text('Gênero: ${leitor.generoLiterarioPreferido}'),
                      if (leitor.frequenciaParticipacao != null &&
                          leitor.frequenciaParticipacao!.isNotEmpty)
                        Text('Frequência: ${leitor.frequenciaParticipacao}'),
                      Text(
                        'Inscrito desde: ${DateFormat('dd/MM/yyyy').format(leitor.dataInscricao)}',
                      ),
                    ],
                  ),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: const Icon(Icons.edit, color: Colors.blue),
                        onPressed: () {
                          Navigator.of(context).push(
                            MaterialPageRoute(
                              builder: (context) =>
                                  CadastroLeitorPage(leitor: leitor),
                            ),
                          );
                        },
                      ),
                      IconButton(
                        icon: const Icon(Icons.delete, color: Colors.red),
                        onPressed: () =>
                            _confirmarExclusao(context, database, leitor),
                      ),
                    ],
                  ),
                  isThreeLine: true,
                ),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.of(context).push(
            MaterialPageRoute(builder: (context) => const CadastroLeitorPage()),
          );
        },
        tooltip: 'Cadastrar Novo Leitor',
        child: const Icon(Icons.add),
      ),
    );
  }

  void _confirmarExclusao(
    BuildContext context,
    AppDatabase database,
    Leitor leitor,
  ) {
    showDialog(
      context: context,
      builder: (BuildContext ctx) {
        return AlertDialog(
          title: const Text('Confirmar Exclusão'),
          content: Text(
            'Tem certeza que deseja excluir o leitor "${leitor.nome}"?',
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('Cancelar'),
              onPressed: () {
                Navigator.of(ctx).pop();
              },
            ),
            TextButton(
              style: TextButton.styleFrom(foregroundColor: Colors.red),
              child: const Text('Excluir'),
              onPressed: () async {
                await database.excluirLeitor(leitor.id);
                Navigator.of(ctx).pop();
                if (mounted) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(
                      content: Text(
                        'Leitor "${leitor.nome}" excluído com sucesso!',
                      ),
                    ),
                  );
                }
              },
            ),
          ],
        );
      },
    );
  }
}
