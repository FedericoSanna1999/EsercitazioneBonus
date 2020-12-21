// Federico Sanna (65614)

package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistrazioneActivity extends AppCompatActivity {
    private EditText username, password, confermaPassword, cittaDiProvenienza, dataDiNascita;
    private TextView usernameNonValido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confermaPassword = findViewById(R.id.conferma_password);
        cittaDiProvenienza = findViewById(R.id.citta_di_provenienza);
        dataDiNascita = findViewById(R.id.data_di_nascita);
        Button registrati = findViewById(R.id.button_registrati);
        usernameNonValido = findViewById(R.id.error_username_non_valido);

        dataDiNascita.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) showDialog(); });

        registrati.setOnClickListener(v -> { if (isValidSignUp()) finish(); });
    }

    protected boolean isValidSignUp() {
        boolean validSignUp = true;

        if (username.getText().toString().length() == 0) {
            username.setError("Inserisci l'username");
            validSignUp = false;
        } else username.setError(null);

        if (password.getText().toString().length() == 0) {
            password.setError("Inserisci la password");
            validSignUp = false;
        } else password.setError(null);

        if (confermaPassword.getText().toString().length() == 0 || !confermaPassword.getText().toString().equals(password.getText().toString())) {
            confermaPassword.setError("Inserisci la password per confermare");
            validSignUp = false;
        } else confermaPassword.setError(null);

        if (cittaDiProvenienza.getText().toString().length() == 0) {
            cittaDiProvenienza.setError("Inserisci la città di provenienza");
            validSignUp = false;
        } else cittaDiProvenienza.setError(null);

        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dataDiNascita.getText().toString());

            if (!(date != null && dataDiNascita.getText().toString().equals(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date.getTime())))) {
                dataDiNascita.setError("Inserisci la data di nascita (dd/MM/yyyy)");
                validSignUp = false;
            } else dataDiNascita.setError(null);
        } catch (ParseException e) {
            dataDiNascita.setError("Inserisci la data di nascita (dd/MM/yyyy)");
            validSignUp = false;
        }

        if (validSignUp)
            try {
                Calendar birthDate = Calendar.getInstance();
                Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dataDiNascita.getText().toString());

                if (date != null) {
                    birthDate.setTime(date);
                    User user = new User(false, username.getText().toString(), password.getText().toString(), cittaDiProvenienza.getText().toString(), birthDate);

                    if (Database.containsUsername(user.getUsername())) {
                        usernameNonValido.setVisibility(View.VISIBLE);
                        validSignUp = false;
                    } else {
                        Database.putUser(user);
                        usernameNonValido.setVisibility(View.GONE);
                    }
                }
            } catch (ParseException e) { e.printStackTrace(); }
        else usernameNonValido.setVisibility(View.GONE);

        return validSignUp;
    }

    protected void showDialog() {
        DialogFragment dialogFragment = DatePickerFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    protected void doPositiveClick(Calendar calendar) {
        dataDiNascita.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(calendar.getTime()));
        dataDiNascita.setError(null);
    }

    protected void doNegativeClick() { }
}