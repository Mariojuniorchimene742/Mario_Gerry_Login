package com.example.loginappmario_gerry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
// ... (outros imports)

public class MainActivity extends AppCompatActivity {

    // 1. VARIÁVEIS DE INSTÂNCIA
    EditText emailEt;
    EditText passEt;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. CONEXÃO UI
        emailEt = findViewById(R.id.emailEt);
        passEt = findViewById(R.id.passEt);
        loginBtn = findViewById(R.id.loginBtn);

        // 3. EVENTO DE CLIQUE
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLogin();
            }
        });
    } // Fim do onCreate

    // 4. LÓGICA DE LOGIN
    private void fazerLogin() {
        String email = emailEt.getText().toString().trim();
        String senha = passEt.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        autenticarUsuario(email, senha);
    }

    // 5. AUTENTICAÇÃO (MOCK/TESTE)
    private void autenticarUsuario(String email, String senha) {
        String EMAIL_CORRETO = "teste@exemplo.com";
        String SENHA_CORRETA = "123456";

        if (email.equals(EMAIL_CORRETO) && senha.equals(SENHA_CORRETA)) {
            Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();

            // CHAMA O REDIRECIONAMENTO
            iniciarNovaActivity(HomeActivity.class);

        } else {
            Toast.makeText(this, "Email ou Senha incorretos.", Toast.LENGTH_SHORT).show();
        }
    }

    // 6. MÉTODO DE REDIRECIONAMENTO (ACABOU DE ADICIONAR)
    private void iniciarNovaActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}