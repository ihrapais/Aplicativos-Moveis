// Importa o pacote principal do Flutter
import 'package:flutter/material.dart';

// Define a terceira tela (formulário bÃ¡sico), um StatelessWidget
class FormularioBasicoPage extends StatelessWidget {
  // Construtor com Key opcional. Removido 'const' daqui porque a chave do formulÃ¡rio nÃ£o Ã© const.
  // O widget em si ainda pode ser instanciado como const onde for usado, como em main.dart.
  FormularioBasicoPage({super.key}); // CORREÃ‡ÃƒO: Removido 'const' aqui

  // Criamos uma chave para validar o formulÃ¡rio.
  // Deve ser 'final', mas NÃƒO 'const' porque GlobalKey Ã© criada em tempo de execuÃ§Ã£o.
  // Renomeada de '_formKey' para 'formKey' para seguir convenÃ§Ãµes de linter para variÃ¡veis locais/membro.
  final formKey = GlobalKey<FormState>(); // CORREÃ‡ÃƒO: Sintaxe e renomeado

  @override
  Widget build(BuildContext context) {
    // Estrutura principal da tela
    return Scaffold(
      appBar: AppBar(
        title: const Text('Formulário Básico'), // Título na AppBar
      ),
      body: Padding(
        // Adiciona espaçamento interno ao redor do conteúdo do formulário
        padding: const EdgeInsets.all(16.0),
        child: Form(
          // Liga o formulário a GlobalKey para validação. Uso 'formKey' (sem underscore)
          key: formKey, // CORREÃ‡ÃƒO: Uso da chave
          child: Column(
            // Alinha os filhos da coluna (campos e botão) ao início (esquerda)
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Campo de texto para nome
              TextFormField(
                decoration: const InputDecoration(
                  labelText: 'Digite seu nome', // Rótulo acima do campo
                  border: OutlineInputBorder(), // Adiciona uma borda ao campo (opcional, boa prÃ¡tica)
                ),
                // Função validator: é chamada quando formKey.currentState.validate() é executado
                validator: (value) {
                  // Validação simples: verifica se o campo está vazio
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira seu nome'; // Mensagem de erro
                  }
                  return null; // Retorna null se válido
                },
              ),

              // Espaço vertical entre o campo de texto e o botão
              const SizedBox(height: 20),

              // Botão de envio
              ElevatedButton(
                // Ação ao pressionar o botão
                onPressed: () {
                  // Se o formulário for válido (formKey.currentState.validate() retorna true)
                  if (formKey.currentState!.validate()) {
                    // Mostrar uma mensagem temporária (SnackBar) indicando sucesso
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(content: Text('Dados válidos! Processando...')),
                    );
                    // >>> Aqui você adicionaria a lógica para processar/salvar os dados <<<
                  } else {
                     // O validate() já exibiu as mensagens de erro nos campos inválidos
                     ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(content: Text('Por favor, corrija os erros')),
                    );
                  }
                },
                child: const Text('Enviar'), // Texto no botão
              ),

              // Opcional: Botão para voltar para a tela anterior
               const SizedBox(height: 20),
               ElevatedButton(
                 onPressed: () {
                   Navigator.pop(context); // Voltar para a tela anterior
                 },
                 child: const Text('Voltar'),
               ),
            ],
          ),
        ),
      ),
    );
  }
}