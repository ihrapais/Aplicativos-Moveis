import 'package:flutter/material.dart';
import '../database/app_database.dart';
import 'cadastro_page.dart';

class ListarPage extends StatefulWidget {
  @override
  _ListarPageState createState() => _ListarPageState();
}

class _ListarPageState extends State<ListarPage> {
  final db = AppDatabase();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Leitores Cadastrados')),
      body: StreamBuilder<List<Leitore>>(
        stream: db.watchLeitores(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }
          final leitores = snapshot.data ?? [];

          if (leitores.isEmpty) {
            return Center(child: Text('Nenhum leitor cadastrado.'));
          }

          return ListView.builder(
            itemCount: leitores.length,
            itemBuilder: (context, index) {
              final leitor = leitores[index];
              return ListTile(
                title: Text(leitor.nome),
                subtitle: Text(
                  'Gênero: ${leitor.genero} | Frequência: ${leitor.frequencia}',
                ),
                trailing: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon: Icon(Icons.edit),
                      onPressed: () async {
                        await Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => CadastroPage(leitor: leitor),
                          ),
                        );
                        setState(() {});
                      },
                    ),
                    IconButton(
                      icon: Icon(Icons.delete),
                      onPressed: () async {
                        await db.deleteLeitor(leitor);
                      },
                    ),
                  ],
                ),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.add),
        onPressed: () async {
          await Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => CadastroPage()),
          );
          setState(() {});
        },
      ),
    );
  }
}
