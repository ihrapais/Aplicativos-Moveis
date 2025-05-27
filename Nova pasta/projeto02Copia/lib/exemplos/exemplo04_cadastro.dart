// Importa o pacote principal do Flutter
import 'package:flutter/material.dart';

// Define a tela de cadastro, como um StatelessWidget para simplificar
// (Se precisar gerenciar o estado dos campos de forma complexa, seria StatefulWidget)
class TelaDeCadastroPage extends StatelessWidget {
  // CORREÇÃO: Removida a palavra-chave 'const' do construtor aqui
  // porque a chave do formulário (_formKey) não é uma constante de tempo de compilação.
  // O widget em si ainda pode ser constante se todos os seus membros finais forem inicializados
  // com valores constantes (como a chave do formulário neste caso, pois ela é final e GlobalKey<FormState>()
  // é criado uma vez por instância do widget).
  // Mantenha o 'const' na chamada em main.dart: '/cadastro': (context) => const TelaDeCadastroPage(),
  TelaDeCadastroPage({super.key}); // Construtor corrigido (removido 'const')

  // Criamos uma chave para validar o formulário nesta tela
  // Deve ser 'final', mas NÃO 'const'
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Cadastro'), // Título na AppBar
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0), // Espaçamento interno
        child: Form(
          key: _formKey, // Liga o formulário à chave para validação
          child: ListView( // Usamos ListView para permitir rolagem se o conteúdo for grande
            children: [
              // Campo para Nome
              TextFormField(
                decoration: const InputDecoration(
                  labelText: 'Nome Completo',
                  border: OutlineInputBorder(),
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira o nome';
                  }
                   if (value.length < 3) {
                     return 'O nome deve ter pelo menos 3 caracteres';
                   }
                  return null;
                },
              ),

              const SizedBox(height: 20), // Espaço entre os campos

              // Campo para CPF
              TextFormField(
                decoration: const InputDecoration(
                  labelText: 'CPF',
                  border: OutlineInputBorder(),
                ),
                 keyboardType: TextInputType.number, // Teclado numérico
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira o CPF';
                  }
                   // >>> Adicionar validação de formato/CPF válido aqui se necessário <<<
                   // Exemplo básico de tamanho:
                   if (value.length != 11) { // CPF sem pontos/hifens
                     return 'O CPF deve ter 11 dígitos';
                   }
                  return null;
                },
              ),

              const SizedBox(height: 20), // Espaço entre os campos

              // Campo para Telefone
              TextFormField(
                decoration: const InputDecoration(
                  labelText: 'Telefone',
                  border: OutlineInputBorder(),
                ),
                 keyboardType: TextInputType.phone, // Teclado de telefone
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira o telefone';
                  }
                   // >>> Adicionar validação de formato de telefone aqui se necessário <<<
                   // Exemplo básico de tamanho:
                   if (value.length < 8) { // Tamanho mínimo de um telefone simples
                     return 'Telefone inválido';
                   }
                  return null;
                },
              ),

              const SizedBox(height: 30), // Espaço antes dos botões

              // Botão Salvar
              ElevatedButton(
                onPressed: () {
                  // Valida todos os campos do formulário
                  if (_formKey.currentState!.validate()) {
                    // Se válido, mostra mensagem de sucesso
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(content: Text('Dados de cadastro válidos! Salvar...')),
                    );
                    // >>> Lógica para salvar os dados (usando os controllers, por exemplo) <<<
                  } else {
                     ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(content: Text('Por favor, corrija os erros no formulário')),
                    );
                  }
                },
                child: const Text('Salvar Cadastro'), // Texto no botão
              ),

              // Espaço entre os botões
              const SizedBox(height: 10),

              // Botão para Voltar
              OutlinedButton(
                onPressed: () {
                  Navigator.pop(context); // Voltar para a tela anterior
                },
                child: const Text('Cancelar e Voltar'), // Texto no botão
              ),
            ],
          ),
        ),
      ),
    );
  }
}