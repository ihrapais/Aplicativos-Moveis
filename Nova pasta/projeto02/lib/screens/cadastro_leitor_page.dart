// lib/screens/cadastro_page.dart
import 'package:drift/drift.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import '../database/app_database.dart';

class CadastroPage extends StatefulWidget {
  final Leitore? leitor;

  CadastroPage({this.leitor});

  @override
  _CadastroPageState createState() => _CadastroPageState();
}

class _CadastroPageState extends State<CadastroPage> {
  final nomeController = TextEditingController();
  final emailController = TextEditingController();
  final telefoneController = TextEditingController();
  final generoController = TextEditingController();
  String? frequenciaSelecionada;
  DateTime? participaDesde;

  @override
  void initState() {
    super.initState();
    if (widget.leitor != null) {
      nomeController.text = widget.leitor!.nome;
      emailController.text = widget.leitor!.email;
      telefoneController.text = widget.leitor!.telefone;
      generoController.text = widget.leitor!.genero;
      frequenciaSelecionada = widget.leitor!.frequencia;
      participaDesde = widget.leitor!.participaDesde;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.leitor == null ? 'Novo Leitor' : 'Editar Leitor'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Column(
            children: [
              TextField(
                controller: nomeController,
                decoration: InputDecoration(labelText: 'Nome'),
              ),
              TextField(
                controller: emailController,
                decoration: InputDecoration(labelText: 'Email'),
              ),
              TextField(
                controller: telefoneController,
                decoration: InputDecoration(labelText: 'Telefone'),
              ),
              TextField(
                controller: generoController,
                decoration: InputDecoration(labelText: 'Gênero Literário'),
              ),
              DropdownButtonFormField<String>(
                value: frequenciaSelecionada,
                decoration: InputDecoration(
                  labelText: 'Frequência de Participação',
                ),
                items: ['Semanal', 'Quinzenal', 'Mensal']
                    .map(
                      (frequencia) => DropdownMenuItem(
                        value: frequencia,
                        child: Text(frequencia),
                      ),
                    )
                    .toList(),
                onChanged: (value) {
                  setState(() => frequenciaSelecionada = value);
                },
              ),
              SizedBox(height: 10),
              Row(
                children: [
                  Text(
                    participaDesde == null
                        ? 'Participa desde: (não selecionado)'
                        : 'Participa desde: ${DateFormat('dd/MM/yyyy').format(participaDesde!)}',
                  ),
                  Spacer(),
                  TextButton(
                    child: Text('Selecionar Data'),
                    onPressed: () async {
                      final data = await showDatePicker(
                        context: context,
                        initialDate: participaDesde ?? DateTime.now(),
                        firstDate: DateTime(2000),
                        lastDate: DateTime.now(),
                      );
                      if (data != null) {
                        setState(() => participaDesde = data);
                      }
                    },
                  ),
                ],
              ),
              SizedBox(height: 20),
              ElevatedButton(
                child: Text('Salvar'),
                onPressed: () async {
                  if (frequenciaSelecionada == null || participaDesde == null)
                    return;

                  final db = AppDatabase();
                  final leitorCompanion = LeitoresCompanion(
                    nome: Value(nomeController.text),
                    email: Value(emailController.text),
                    telefone: Value(telefoneController.text),
                    genero: Value(generoController.text),
                    frequencia: Value(frequenciaSelecionada!),
                    participaDesde: Value(participaDesde!),
                  );

                  if (widget.leitor == null) {
                    await db.insertLeitor(leitorCompanion);
                  } else {
                    final leitorAtualizado = widget.leitor!.copyWith(
                      nome: nomeController.text,
                      email: emailController.text,
                      telefone: telefoneController.text,
                      genero: generoController.text,
                      frequencia: frequenciaSelecionada!,
                      participaDesde: participaDesde!,
                    );
                    await db.updateLeitor(leitorAtualizado);
                  }

                  Navigator.pop(context);
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}
