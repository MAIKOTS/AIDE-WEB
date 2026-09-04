package aideweb.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TelaEditor {
    
    private final Context contexto;
    private final LinearLayout rootLayout;
    private final File arquivoAtual;
    private EditText campoTexto;

    public TelaEditor(Context contexto, File arquivoAtual) {
        this.contexto = contexto;
        this.arquivoAtual = arquivoAtual;

        rootLayout = new LinearLayout(contexto);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(Color.parseColor("#121212"));
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ));

        construirCabecalho();
        construirAreaEdicao();
    }

    private void construirCabecalho() {
        LinearLayout barraTopo = new LinearLayout(contexto);
        barraTopo.setOrientation(LinearLayout.HORIZONTAL);
        barraTopo.setPadding(32, 32, 32, 16);
        barraTopo.setGravity(Gravity.CENTER_VERTICAL);

        TextView titulo = new TextView(contexto);
        titulo.setText("📝 " + arquivoAtual.getName());
        titulo.setTextColor(Color.parseColor("#00B0FF"));
        titulo.setTextSize(16f);
        titulo.setLayoutParams(new LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        ));

        TextView btnSalvar = new TextView(contexto);
        btnSalvar.setText("Salvar");
        btnSalvar.setTextColor(Color.parseColor("#00B0FF"));
        btnSalvar.setTextSize(16f);
        btnSalvar.setPadding(16, 16, 16, 16);
        btnSalvar.setOnClickListener(v -> salvarArquivo());

        barraTopo.addView(titulo);
        barraTopo.addView(btnSalvar);
        rootLayout.addView(barraTopo);
    }

    private void construirAreaEdicao() {
        ScrollView scrollView = new ScrollView(contexto);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ));

        campoTexto = new EditText(contexto);
        campoTexto.setBackgroundColor(Color.TRANSPARENT);
        campoTexto.setTextColor(Color.WHITE);
        campoTexto.setHintTextColor(Color.GRAY);
        campoTexto.setTextSize(14f);
        campoTexto.setTypeface(android.graphics.Typeface.MONOSPACE);
        campoTexto.setGravity(Gravity.TOP | Gravity.START);
        campoTexto.setPadding(32, 32, 32, 32);

        lerConteudoArquivo();

        scrollView.addView(campoTexto);
        rootLayout.addView(scrollView);
    }

    private void lerConteudoArquivo() {
        StringBuilder conteudo = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoAtual))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
            campoTexto.setText(conteudo.toString());
        } catch (IOException e) {
            Toast.makeText(contexto, "Erro ao ler arquivo", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarArquivo() {
        try (FileWriter writer = new FileWriter(arquivoAtual)) {
            writer.write(campoTexto.getText().toString());
            Toast.makeText(contexto, "Arquivo salvo com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(contexto, "Erro ao salvar arquivo", Toast.LENGTH_SHORT).show();
        }
    }

    public LinearLayout obterLayout() {
        return rootLayout;
    }
}
