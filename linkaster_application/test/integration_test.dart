import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:linkaster_application/screens/login_page.dart';
import 'package:http/http.dart' as http;
import 'package:mockito/mockito.dart';
import 'dart:convert';

// Mock HttpClient
class MockHttpClient extends Mock implements http.Client {
  @override
  Future<http.Response> post(Uri url,
      {Map<String, String>? headers, body, Encoding? encoding}) {
    // Mocking will happen in individual tests
    return super.noSuchMethod(
        Invocation.method(#post, [url], {#headers: headers, #body: body}),
        returnValue: Future.value(
            http.Response('{"message": "Invalid credentials"}', 400)),
        returnValueForMissingStub: Future.value(
            http.Response('{"message": "Invalid credentials"}', 400)));
  }
}

void main() {
  group('Resgister and LogIn Test', () {
    late MockHttpClient mockHttpClient;

    setUp(() {
      mockHttpClient = MockHttpClient();
    });

    testWidgets(
      'Register Page inputs valid credentials and goes to logIn',
      (WidgetTester tester) async {
        const String email = "student@example.com";
        const String password = "student_password";
        const String token = "mocked_token";

        // Mock HTTP response for login request
        when(mockHttpClient.post(
          Uri.parse('http://localhost:8080/api/auth/student/login'),
          headers: {'Content-Type': 'application/json'},
          body: jsonEncode({"userEmail": email, "password": password}),
        )).thenAnswer(
          (_) async => http.Response(jsonEncode({'token': token}), 200),
        );

        // Pump LoginPage with MockHttpClient
        await tester.pumpWidget(MaterialApp(
          home: LoginPage(httpClient: mockHttpClient),
        ));

        // Fill email and password fields
        await tester.enterText(find.byType(TextField).at(0), email);
        await tester.enterText(find.byType(TextField).at(1), password);

        // Tap login button
        await tester.tap(find.byType(ElevatedButton));
        await tester.pumpAndSettle();

        // Verify HTTP POST request was made
        verify(mockHttpClient.post(
          Uri.parse('http://localhost:8080/api/auth/student/login'),
          headers: {'Content-Type': 'application/json'},
          body: jsonEncode({"userEmail": email, "password": password}),
        )).called(1);

        // Verify if token retrieval happens here (you can also verify based on your UI updates)
      },
    );
  });
}
