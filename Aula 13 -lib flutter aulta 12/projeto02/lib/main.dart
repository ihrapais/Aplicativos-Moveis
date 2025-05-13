// Importa o pacote principal do Flutter (obrigatório para criar apps)
import 'package:flutter/material.dart';

// Importa os arquivos das telas que serão usadas nas rotas
// Certifique-se de que esses caminhos estão corretos no seu projeto
import 'exemplos/exemplo01_stateless.dart'; // Primeira Tela (StatelessWidget)
import 'exemplos/exemplo02_stateful.dart'; // Segunda Tela (StatefulWidget)
import 'exemplos/exemplo03_formulario.dart'; // Tela de Formulário Básico (StatelessWidget)
import 'exemplos/exemplo04_cadastro.dart'; // Tela de Cadastro (StatelessWidget)
// AJUSTE O NOME DO ARQUIVO IMPORTADO AQUI:
import 'exemplos/exemplo05_listar.dart'; // Importa a tela de listagem de clientes

// Função principal do app, o ponto de entrada
void main() {
  // Executa o aplicativo, chamando o widget MyApp
  runApp(const MyApp());
}

// Widget principal do app que configura o MaterialApp e o sistema de rotas
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    // MaterialApp configura o aplicativo, tema e o sistema de navegação (rotas)
    return MaterialApp(
      debugShowCheckedModeBanner:
          false, // Remove a faixa "DEBUG" no modo debug (opcional)
      title:
          'Exemplos de Telas e Rotas', // Título do app (para a barra de tarefas/aba do navegador)
      theme: ThemeData(
        // Configurações de tema (opcional)
        primarySwatch: Colors.blue, // Define a cor primária
        visualDensity:
            VisualDensity.adaptivePlatformDensity, // Adapta a densidade visual
      ),
      initialRoute:
          '/', // Define a rota inicial do aplicativo (qual tela carregar primeiro)
      // Mapa de rotas nomeadas.
      // A chave (String) é o nome da rota.
      // O valor (Function) é uma função que constrói o widget da tela para essa rota.
      routes: {
        '/': (context) => const PrimeiraTela(), // Rota para a Primeira Tela
        '/segunda':
            (context) => const SegundaTela(), // Rota para a Segunda Tela
        '/formulario':
            (context) =>
                FormularioBasicoPage(), // Rota para o Formulário Básico
        '/cadastro':
            (context) => TelaDeCadastroPage(), // Rota para a Tela de Cadastro
        // A rota para ListarClientesPage permanece a mesma, o import acima é o que define qual arquivo usar
        '/listar':
            (context) =>
                const ListarClientesPage(), // Rota para Listar Clientes
      },
    );
  }
}
