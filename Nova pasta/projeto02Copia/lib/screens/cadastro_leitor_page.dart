import 'package:drift/drift.dart' as drift;
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import '../database/app_database.dart';

class CadastroLeitorPage extends StatefulWidget {
  final Leitor? leitor;

  const CadastroLeitorPage({super.key, this.leitor});

  @override
  State<CadastroLeitorPage> createState() => _CadastroLeitorPageState();
}

class _CadastroLeitorPageState extends State<CadastroLeitorPage> {
  final nomeController = TextEditingController();
  final emailController = TextEditingController();
  final telefoneController = TextEditingController();
  final generoController = TextEditingController();
  String? frequenciaSelecionada;
  DateTime? dataInscricao;

  @override
  void initState() {
    super.initState();
    if (widget.leitor != null) {
      nomeController.text = widget.leitor!.nome;
      emailController.text = widget.leitor!.email;
      telefoneController.text = widget.leitor!.telefone ?? '';
      generoController.text = widget.leitor!.generoLiterarioPreferido ?? '';
      frequenciaSelecionada = widget.leitor!.frequenciaParticipacao;
      dataInscricao = widget.leitor!.dataInscricao;
    }
  }

  Future<void> _selecionarData() async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: dataInscricao ?? DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime.now(),
    );
    if (picked != null && picked != dataInscricao) {
      setState(() {
        dataInscricao = picked;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    final db = Provider.of<AppDatabase>(context, listen: false);

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
                decoration: const InputDecoration(labelText: 'Nome'),
              ),
              const SizedBox(height: 10),
              TextField(
                controller: emailController,
                decoration: const InputDecoration(labelText: 'Email'),
                keyboardType: TextInputType.emailAddress,
              ),
              const SizedBox(height: 10),
              TextField(
                controller: telefoneController,
                decoration: const InputDecoration(labelText: 'Telefone'),
                keyboardType: TextInputType.phone,
              ),
              const SizedBox(height: 10),
              TextField(
                controller: generoController,
                decoration:
                    const InputDecoration(labelText: 'Gênero Literário'),
              ),
              const SizedBox(height: 10),
              DropdownButtonFormField<String>(
                value: frequenciaSelecionada,
                decoration: const InputDecoration(
                  labelText: 'Frequência de Participação',
                ),
                items: const [
                  DropdownMenuItem(value: 'Semanal', child: Text('Semanal')),
                  DropdownMenuItem(
                      value: 'Quinzenal', child: Text('Quinzenal')),
                  DropdownMenuItem(value: 'Mensal', child: Text('Mensal')),
                ],
                onChanged: (String? newValue) {
                  setState(() {
                    frequenciaSelecionada = newValue;
                  });
                },
              ),
              const SizedBox(height: 10),
              Row(
                children: [
                  Expanded(
                    child: Text(
                      dataInscricao == null
                          ? 'Data de Inscrição: (não selecionado)'
                          : 'Data de Inscrição: ${DateFormat('dd/MM/yyyy').format(dataInscricao!)}',
                    ),
                  ),
                  TextButton(
                    onPressed: _selecionarData,
                    child: const Text('Selecionar Data'),
                  ),
                ],
              ),
              const SizedBox(height: 20),
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: () async {
                    if (nomeController.text.isEmpty ||
                        emailController.text.isEmpty ||
                        frequenciaSelecionada == null ||
                        dataInscricao == null) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(
                          content: Text(
                              'Por favor, preencha todos os campos obrigatórios'),
                          backgroundColor: Colors.red,
                        ),
                      );
                      return;
                    }

                    try {
                      if (widget.leitor == null) {
                        final leitorCompanion = LeitoresCompanion.insert(
                          nome: nomeController.text,
                          email: emailController.text,
                          telefone: drift.Value(telefoneController.text.isEmpty
                              ? null
                              : telefoneController.text),
                          generoLiterarioPreferido: drift.Value(
                              generoController.text.isEmpty
                                  ? null
                                  : generoController.text),
                          frequenciaParticipacao:
                              drift.Value(frequenciaSelecionada!),
                          dataInscricao: dataInscricao!,
                        );
                        await db.into(db.leitores).insert(leitorCompanion);
                      } else {
                        await db.update(db.leitores).replace(
                              widget.leitor!.copyWith(
                                nome: nomeController.text,
                                email: emailController.text,
                                telefone: drift.Value(
                                    telefoneController.text.isEmpty
                                        ? null
                                        : telefoneController.text),
                                generoLiterarioPreferido: drift.Value(
                                    generoController.text.isEmpty
                                        ? null
                                        : generoController.text),
                                frequenciaParticipacao:
                                    drift.Value(frequenciaSelecionada),
                                dataInscricao: dataInscricao!,
                              ),
                            );
                      }

                      if (!mounted)
                        return; // Verifica se o widget ainda está montado
                      Navigator.pop(context);
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(
                          content: Text('Leitor salvo com sucesso!'),
                          backgroundColor: Colors.green,
                        ),
                      );
                    } catch (e) {
                      if (!mounted)
                        return; // Verifica se o widget ainda está montado
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                          content: Text('Erro ao salvar: $e'),
                          backgroundColor: Colors.red,
                        ),
                      );
                    }
                  },
                  child: const Text('Salvar'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    nomeController.dispose();
    emailController.dispose();
    telefoneController.dispose();
    generoController.dispose();
    super.dispose();
  }
}
