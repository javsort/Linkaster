import 'package:flutter/material.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:http/http.dart' as http;
import './screens/logIn_page.dart';

void main() {
  initializeDateFormatting().then((_) => runApp(LinkasterApp()));
}

class LinkasterApp extends StatefulWidget {
  @override
  _LinkasterAppState createState() => _LinkasterAppState();
}

class _LinkasterAppState extends State<LinkasterApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'L!nkaster',
      theme: ThemeData(
        primaryColor: Color.fromARGB(255, 253, 9, 9),
        colorScheme: ColorScheme.fromSeed(
          seedColor: Color.fromARGB(255, 106, 134, 116),
          primary: Color.fromARGB(255, 253, 9, 9),
        ),
      ),
      home: LoginPage(httpClient: http.Client()),
    );
  }
}
