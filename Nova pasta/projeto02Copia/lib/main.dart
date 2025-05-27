import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'database/app_database.dart';
import 'screens/listar_leitor_page.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized(); // Importante
  final AppDatabase database = AppDatabase();

  runApp(
    Provider<AppDatabase>(
      create: (_) => database,
      dispose: (_, db) => db.close(), // Fecha o banco ao finalizar
      child: const MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    // Para usar Colors.teal.withOpacity de forma correta sem o aviso:
    // final tealWithOpacity = Colors.teal.withAlpha((255 * 0.05).round());

    // No entanto, para Material 3, é melhor usar ColorScheme.fromSeed
    // Vou manter seu original por fidelidade, mas ciente do aviso se withOpacity for usado.
    // Se a linha 43 do seu main.dart atual tem Colors.teal.withOpacity(0.05),
    // substitua por Colors.teal.withAlpha((255 * 0.05).round())
    // Ou, melhor ainda, use o ColorScheme como nas minhas sugestões anteriores para M3.

    // Mantendo sua estrutura de ThemeData original:
    return MaterialApp(
      title: 'Cadastro de Leitores',
      theme: ThemeData(
        primarySwatch: Colors.teal,
        visualDensity: VisualDensity.adaptivePlatformDensity,
        useMaterial3: true,
        inputDecorationTheme: InputDecorationTheme(
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8.0),
          ),
          filled: true,
          // Se esta linha (ou similar) estiver causando o aviso de 'withOpacity':
          // fillColor: Colors.teal.withOpacity(0.05),
          // Substitua por:
          fillColor: Colors.teal.withAlpha((255 * 0.05).round()),
        ),
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8.0),
            ),
            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
          ),
        ),
      ),
      debugShowCheckedModeBanner: false,
      home: const ListarLeitoresPage(),
    );
  }
}
