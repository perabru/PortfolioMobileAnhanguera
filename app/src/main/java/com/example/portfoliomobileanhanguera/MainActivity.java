package com.example.portfoliomobileanhanguera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    int quantidade = 1;
    int precoBase = 20;
    TextView tvQuantidade, tvPreco, tvResumo;
    CheckBox cbBacon, cbQueijo, cbOnion;
    EditText etNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
    }

    public void inicializarComponentes() {
        tvQuantidade = findViewById(R.id.tvQuantidade);
        tvPreco = findViewById(R.id.tvPreco);
        tvResumo = findViewById(R.id.tvResumo);
        cbBacon = findViewById(R.id.cbBacon);
        cbQueijo = findViewById(R.id.cbQueijo);
        cbOnion = findViewById(R.id.cbOnion);
        etNome = findViewById(R.id.etNome);

        findViewById(R.id.btnMais).setOnClickListener(v -> {
            quantidade++;
            atualizarPreco();
        });

        findViewById(R.id.btnMenos).setOnClickListener(v -> {
            if (quantidade > 1) {
                quantidade--;
                atualizarPreco();
            }
        });

        findViewById(R.id.btnEnviar).setOnClickListener(v -> enviarPedido());
    }

    public void atualizarPreco() {
        tvQuantidade.setText(String.valueOf(quantidade));
        int total = calcularTotal();
        tvPreco.setText("Preço: R$" + total);
    }

    public int calcularTotal() {
        int total = precoBase;
        if (cbBacon.isChecked()) total += 2;
        if (cbQueijo.isChecked()) total += 2;
        if (cbOnion.isChecked()) total += 3;
        return total * quantidade;
    }

    public void enviarPedido() {
        String nome = etNome.getText().toString();
        String resumo = "Nome do cliente: " + nome + "\n"
                + "Tem Bacon? " + (cbBacon.isChecked() ? "Sim" : "Não") + "\n"
                + "Tem Queijo? " + (cbQueijo.isChecked() ? "Sim" : "Não") + "\n"
                + "Tem Onion Rings? " + (cbOnion.isChecked() ? "Sim" : "Não") + "\n"
                + "Quantidade: " + quantidade + "\n"
                + "Preço final: R$" + calcularTotal();

        tvResumo.setText(resumo);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + nome);
        intent.putExtra(Intent.EXTRA_TEXT, resumo);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}