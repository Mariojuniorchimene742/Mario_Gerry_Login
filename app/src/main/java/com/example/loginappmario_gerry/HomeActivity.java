package com.example.loginappmario_gerry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Igrejas+");
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar);
        }

        TextView welcome = findViewById(R.id.home_welcome);
        TextView churchTv = findViewById(R.id.home_church);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String name = prefs.getString("name", "Usuário");
        String church = prefs.getString("church", "");

        welcome.setText("Bem-vindo, " + name + "!");
        if (church != null && !church.isEmpty()) {
            churchTv.setText("Igreja: " + church);
        } else {
            churchTv.setText("");
        }

        // Botão Sair
        Button logout = findViewById(R.id.home_logout);
        if (logout != null) {
            logout.setOnClickListener(v -> {
                // Opcional: limpar prefs se quiser mesmo logout completo
                // getSharedPreferences("MyAppPrefs", MODE_PRIVATE).edit().clear().apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
        }

        // Ações rápidas (placeholders)
        FloatingActionButton fab = findViewById(R.id.home_fab);
        if (fab != null) {
            fab.setOnClickListener(v -> Toast.makeText(this, "Adicionar novo evento (em desenvolvimento)", Toast.LENGTH_SHORT).show());
        }

        Button events = findViewById(R.id.btn_events);
        Button members = findViewById(R.id.btn_members);
        Button offerings = findViewById(R.id.btn_offerings);
        Button messages = findViewById(R.id.btn_messages);

        if (events != null) events.setOnClickListener(v -> Toast.makeText(this, "Eventos — em desenvolvimento", Toast.LENGTH_SHORT).show());
        if (members != null) members.setOnClickListener(v -> Toast.makeText(this, "Membros — em desenvolvimento", Toast.LENGTH_SHORT).show());
        if (offerings != null) offerings.setOnClickListener(v -> Toast.makeText(this, "Ofertas — em desenvolvimento", Toast.LENGTH_SHORT).show());
        if (messages != null) messages.setOnClickListener(v -> Toast.makeText(this, "Mensagens — em desenvolvimento", Toast.LENGTH_SHORT).show());
    }
}

