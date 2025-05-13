// Importa o pacote principal do Flutter (obrigatório para criar apps)
import 'package:flutter/material.dart';

// Função principal do app, o ponto de entrada
void main() {
  // Executa o aplicativo, chamando o widget MyApp
  runApp(const MyApp());
}

// Define o widget principal do app (sem estado, apenas estrutura inicial)
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    // Método que monta a interface gráfica
    return MaterialApp( // Define o app com configuração básica
      debugShowCheckedModeBanner: false, // Remove a faixa "DEBUG" do canto (opcional)
      title: 'Exemplo Stateful', // Título do app (aparece na barra de tarefas/aba do navegador)
      theme: ThemeData( // Configurações de tema básicas (opcional)
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity, // Adapta a densidade visual
      ),
      // Estrutura visual principal (tela com barra superior + corpo)
      home: Scaffold(
        appBar: AppBar( // Barra superior
          title: const Text('Exemplo Stateful'), // Título que aparece na barra
        ),
        // Centraliza o conteúdo na tela
        body: const Center(
          // AQUI usamos o nosso widget que pode mudar de estado
          child: MeuTextoStateful(),
        ),
      ),
    );
  }
}

// ================================================================
// NOSSO WIDGET STATEFUL COMEÇA AQUI
// Define o widget que pode mudar (Stateful)
class MeuTextoStateful extends StatefulWidget {
  const MeuTextoStateful({super.key});

  // Cria o objeto que vai controlar o estado (State)
  @override
  State<MeuTextoStateful> createState() => _MeuTextoStatefulState();
}

// Define a classe de estado associada a MeuTextoStateful
// Tudo que pode mudar (variáveis) e a lógica para mudá-las vai aqui
class _MeuTextoStatefulState extends State<MeuTextoStateful> {
  // Variável que guarda o texto atual mostrado na tela
  String mensagem = 'Olá, Flutter!';

  // Função que altera o valor da variável 'mensagem'
  // Esta função é chamada quando o botão é pressionado
  void alterarMensagem() {
    // setState avisa o Flutter que o estado mudou
    // e que a UI precisa ser redesenhada (o método build será chamado novamente)
    setState(() {
      mensagem = 'Mensagem alterada!'; // Altera o valor da variável de estado
      // Você pode adicionar outras lógicas de mudança de estado aqui
    });
  }

  // Este é o método build da CLASSE DE ESTADO,
  // ele descreve a parte da UI que este widget (e seu estado) representa.
  // Este método é chamado inicialmente e sempre que setState for invocado.
  @override
  Widget build(BuildContext context) {
    // Coloca elementos um em cima do outro (verticalmente)
    return Column(
      mainAxisAlignment: MainAxisAlignment.center, // Centraliza os filhos na coluna
      children: [
        Text( // Exibe o texto atual
          mensagem, // Mostra o valor da variável 'mensagem' definida acima
          style: const TextStyle( // Estiliza o texto
            fontSize: 32, // Define o tamanho da fonte
            fontWeight: FontWeight.bold, // Deixa o texto em negrito
            color: Colors.blue, // Cor azul para o texto
          ),
        ),

        const SizedBox(height: 20), // Espaço em branco de 20 pixels entre o texto e o botão

        // Botão elevado (Material Design)
        ElevatedButton(
          onPressed: alterarMensagem, // Quando clicar, chama a função alterarMensagem
          child: const Text('Alterar Mensagem'), // Texto que aparece no botão
        ), // ElevatedButton
      ],
    ); // Column
  }
}
// FIM DO NOSSO WIDGET STATEFUL
// ================================================================