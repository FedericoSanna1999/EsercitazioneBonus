// Federico Sanna (65614)

package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    TextView password;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView benvenuto = findViewById(R.id.benvenuto);
        TextView admin = findViewById(R.id.admin);
        TextView username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        TextView cittaDiProvenienza = findViewById(R.id.citta_di_provenienza);
        TextView dataDiNascita = findViewById(R.id.data_di_nascita);
        Button gestisciUtenti = findViewById(R.id.button_gestisci_utenti);
        Button logout = findViewById(R.id.button_logout);
        TextView modificaPassword = findViewById(R.id.link_modifica_password);

        user = Database.getUser(getIntent().getStringExtra("username"));

        if (user != null) {
            benvenuto.setText(benvenuto.getText().toString().concat(" ").concat(user.getUsername()).concat("!"));
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            cittaDiProvenienza.setText(user.getHometown());
            dataDiNascita.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(user.getBirthDate().getTime()));

            if (user.isAdmin()) {
                admin.setVisibility(View.VISIBLE);
                gestisciUtenti.setVisibility(View.VISIBLE);
            }
        } else finish();

        gestisciUtenti.setOnClickListener(v -> {
            Intent gestisciUtentiIntent = new Intent(HomeActivity.this, GestisciUtentiActivity.class);
            startActivity(gestisciUtentiIntent);
        });

        logout.setOnClickListener(v -> finish());

        modificaPassword.setOnClickListener(v -> {
            Intent modificaPasswordIntent = new Intent(HomeActivity.this, ModificaPasswordActivity.class);
            modificaPasswordIntent.putExtra("username", username.getText().toString());
            startActivity(modificaPasswordIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        user = Database.getUser(user.getUsername());

        if (user != null)
            password.setText(user.getPassword());
    }
}