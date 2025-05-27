import 'package:drift/drift.dart';
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
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _nomeController;
  late TextEditingController _emailController;
  late TextEditingController _telefoneController;
  late TextEditingController _generoController;
  late TextEditingController _frequenciaController;
  late DateTime _dataInscricao;

  @override
  void initState() {
    super.initState();
    _nomeController = TextEditingController(text: widget.leitor?.nome ?? '');
    _emailController = TextEditingController(text: widget.leitor?.email ?? '');
    _telefoneController = TextEditingController(
      text: widget.leitor?.telefone ?? '',
    );
    _generoController = TextEditingController(
      text: widget.leitor?.generoLiterarioPreferido ?? '',
    );
    _frequenciaController = TextEditingController(
      text: widget.leitor?.frequenciaParticipacao ?? '',
    );
    _dataInscricao = widget.leitor?.dataInscricao ?? DateTime.now();
  }

  @override
  void dispose() {
    _nomeController.dispose();
    _emailController.dispose();
    _telefoneController.dispose();
    _generoController.dispose();
    _frequenciaController.dispose();
    super.dispose();
  }

  Future<void> _selecionarData(BuildContext context) async {
    final DateTime? dataSelecionada = await showDatePicker(
      context: context,
      initialDate: _dataInscricao,
      firstDate: DateTime(2000),
      lastDate: DateTime(2101),
    );
    if (dataSelecionada != null && dataSelecionada != _dataInscricao) {
      setState(() {
        _dataInscricao = dataSelecionada;
      });
    }
  }

  void _salvarLeitor() async {
    if (_formKey.currentState!.validate()) {
      final database = Provider.of<AppDatabase>(context, listen: false);

      if (widget.leitor == null) {
        final leitorCompanion = LeitoresCompanion(
          nome: Value(_nomeController.text),
          email: Value(_emailController.text),
          telefone: Value(
            _telefoneController.text.isEmpty ? null : _telefoneController.text,
          ),
          generoLiterarioPreferido: Value(
            _generoController.text.isEmpty ? null : _generoController.text,
          ),
          frequenciaParticipacao: Value(
            _frequenciaController.text.isEmpty
                ? null
                : _frequenciaController.text,
          ),
          dataInscricao: Value(_dataInscricao),
        );
        await database.inserirLeitor(leitorCompanion);
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Leitor cadastrado com sucesso!')),
        );
      } else {
        final leitorAtualizado = Leitor(
          id: widget.leitor!.id,
          nome: _nomeController.text,
          email: _emailController.text,
          telefone: _telefoneController.text.isEmpty
              ? null
              : _telefoneController.text,
          generoLiterarioPreferido: _generoController.text.isEmpty
              ? null
              : _generoController.text,
          frequenciaParticipacao: _frequenciaController.text.isEmpty
              ? null
              : _frequenciaController.text,
          dataInscricao: _dataInscricao,
        );
        await database.atualizarLeitor(leitorAtualizado);
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Leitor atualizado com sucesso!')),
        );
      }
      if (mounted) {
        Navigator.of(context).pop();
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          widget.leitor == null ? 'Cadastrar Leitor' : 'Editar Leitor',
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: <Widget>[
              TextFormField(
                controller: _nomeController,
                decoration: const InputDecoration(labelText: 'Nome Completo*'),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira o nome.';
                  }
                  return null;
                },
              ),
              const SizedBox(height: 10),
              TextFormField(
                controller: _emailController,
                decoration: const InputDecoration(labelText: 'Email*'),
                keyboardType: TextInputType.emailAddress,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira o email.';
                  }
                  if (!value.contains('@')) {
                    return 'Por favor, insira um email válido.';
                  }
                  return null;
                },
              ),
              const SizedBox(height: 10),
              TextFormField(
                controller: _telefoneController,
                decoration: const InputDecoration(labelText: 'Telefone'),
                keyboardType: TextInputType.phone,
              ),
              const SizedBox(height: 10),
              TextFormField(
                controller: _generoController,
                decoration: const InputDecoration(
                  labelText: 'Gênero Literário Preferido',
                ),
              ),
              const SizedBox(height: 10),
              TextFormField(
                controller: _frequenciaController,
                decoration: const InputDecoration(
                  labelText: 'Frequência de Participação (Ex: Semanal)',
                ),
              ),
              const SizedBox(height: 20),
              Row(
                children: [
                  Expanded(
                    child: Text(
                      "Data de Inscrição: ${DateFormat('dd/MM/yyyy').format(_dataInscricao)}",
                    ),
                  ),
                  TextButton(
                    onPressed: () => _selecionarData(context),
                    child: const Text('Selecionar Data'),
                  ),
                ],
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: _salvarLeitor,
                child: Text(
                  widget.leitor == null
                      ? 'Salvar Cadastro'
                      : 'Atualizar Cadastro',
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
