import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'database/app_database.dart';
import 'screens/listar_leitor_page.dart';

void main() {
  // Garante que os bindings do Flutter estão inicializados
  // WidgetsFlutterBinding.ensureInitialized(); // Descomente se necessário para plugins específicos antes do runApp

  // Cria uma instância do banco de dados
  final AppDatabase database = AppDatabase();

  runApp(
    // Provider para disponibilizar o banco de dados na árvore de widgets
    Provider<AppDatabase>(
      create: (_) => database,
      dispose: (_, db) => db.close(), // Fecha o banco ao डिस्पोज करना
      child: const MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Cadastro de Leitores',
      theme: ThemeData(
        primarySwatch: Colors.teal, // Você pode escolher outra cor primária
        visualDensity: VisualDensity.adaptivePlatformDensity,
        useMaterial3: true, // Habilita Material 3 para um visual mais moderno
        // Estilo básico para botões e inputs, se desejar
        inputDecorationTheme: InputDecorationTheme(
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8.0),
          ),
          filled: true,
          fillColor: Colors.teal.withOpacity(0.05),
        ),
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            // primary: Colors.teal, // Cor de fundo do botão
            // onPrimary: Colors.white, // Cor do texto do botão
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8.0),
            ),
            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
          ),
        ),
      ),
      debugShowCheckedModeBanner: false, // Remove o banner de debug
      home: const ListarLeitoresPage(), // Tela inicial da aplicação
    );
  }
}
