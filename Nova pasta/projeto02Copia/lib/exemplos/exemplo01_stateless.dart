// Importa o pacote principal do Flutter (obrigatório para criar apps)
import 'package:flutter/material.dart';

// Define a classe da Primeira Tela, que é um widget sem estado (StatelessWidget)
class PrimeiraTela extends StatelessWidget {
  // Construtor da PrimeiraTela, recebendo uma Key opcional
  const PrimeiraTela({super.key});

  // Método build: monta a interface gráfica da tela
  @override
  Widget build(BuildContext context) {
    // Scaffold cria a estrutura básica da tela (AppBar + Body)
    return Scaffold(
      // Cria a barra superior (AppBar)
      appBar: AppBar(
        title: const Text('Primeira Tela'), // Texto mostrado na AppBar
      ),
      // Centraliza todo o conteúdo no meio da tela
      body: Center(
        // Organiza vários widgets em coluna (um abaixo do outro)
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center, // Alinha os widgets verticalmente ao centro
          // Lista de widgets filhos
          children: [
            const Text(
              'Olá, Flutter!',
              style: TextStyle(
                fontSize: 32,
                fontWeight: FontWeight.bold, // Fonte em negrito
                color: Colors.blue, // Cor azul para o texto
              ),
            ),
            const SizedBox(height: 20), // Espaço em branco

            // Botão para ir para a Segunda Tela
            ElevatedButton(
              // Função chamada quando o botão for pressionado
              onPressed: () {
                // Navega para a rota '/segunda'. 'context' é válido aqui dentro do build.
                Navigator.pushNamed(context, '/segunda');
              },
              child: const Text('Ir para a Segunda Tela'), // Texto do botão
            ),

            const SizedBox(height: 20), // Espaço entre os botões

            // Botão para ir para o Formulário Básico
            ElevatedButton(
              onPressed: () {
                // Navega para a rota '/formulario'
                Navigator.pushNamed(context, '/formulario');
              },
              child: const Text('Ir para o Formulário Básico'), // Texto do botão (ajustado)
            ),

            const SizedBox(height: 20), // Espaço entre os botões

            // Botão para ir para a Tela de Cadastro
            ElevatedButton(
              onPressed: () {
                // Navega para a rota '/cadastro'
                Navigator.pushNamed(context, '/cadastro');
              },
              child: const Text('Ir para Cadastro'), // Texto do botão (ajustado)
            ),
          ],
        ),
      ),
    );
  }
}