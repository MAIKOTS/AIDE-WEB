package aideweb.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.File;
import java.util.List;
import aideweb.util.GerenciadorArquivos;

public class TelaWorkspace {

    private final Context contexto;
    private final LinearLayout rootLayout;
    private final File pastaRaizProjeto;
    private File diretorioAtual;
    private final LinearLayout containerLista;
    private final TextView tituloCaminho;
    private ArquivoSelecionadoListener listener;

    public interface ArquivoSelecionadoListener {
        void onArquivoSelecionado(File arquivo);
    }

    public TelaWorkspace(Context contexto, File pastaProjeto, ArquivoSelecionadoListener listener) {
        this.contexto = contexto;
        this.pastaRaizProjeto = pastaProjeto;
        this.diretorioAtual = pastaProjeto;
        this.listener = listener;

        rootLayout = new LinearLayout(contexto);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(Color.parseColor("#121212"));
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ));

        tituloCaminho = new TextView(contexto);
        tituloCaminho.setTextColor(Color.parseColor("#00B0FF"));
        tituloCaminho.setTextSize(16f);
        tituloCaminho.setPadding(32, 32, 32, 16);
        rootLayout.addView(tituloCaminho);

        ScrollView scrollView = new ScrollView(contexto);
        containerLista = new LinearLayout(contexto);
        containerLista.setOrientation(LinearLayout.VERTICAL);
        containerLista.setPadding(32, 16, 32, 32);
        scrollView.addView(containerLista);
        rootLayout.addView(scrollView);

        carregarDiretorio(diretorioAtual);
    }

    public void carregarDiretorio(File diretorio) {
        this.diretorioAtual = diretorio;
        tituloCaminho.setText("📁 " + diretorio.getAbsolutePath());
        containerLista.removeAllViews();

        if (diretorio.getParentFile() != null && !diretorio.equals(pastaRaizProjeto)) {
            TextView btnVoltar = criarItemVisual("📁 .. (Pasta Acima)", v -> {
                carregarDiretorio(diretorio.getParentFile());
            });
            containerLista.addView(btnVoltar);
        }

        List<File> arquivos = GerenciadorArquivos.listarArquivos(diretorio);
        for (File arquivo : arquivos) {
            String icone = arquivo.isDirectory() ? "📂 " : "📄 ";
            TextView itemView = criarItemVisual(icone + arquivo.getName(), v -> {
                if (arquivo.isDirectory()) {
                    carregarDiretorio(arquivo);
                } else {
                    if (listener != null) {
                        listener.onArquivoSelecionado(arquivo);
                    }
                }
            });
            containerLista.addView(itemView);
        }
    }

    private TextView criarItemVisual(String texto, View.OnClickListener aoClicar) {
        TextView item = new TextView(contexto);
        item.setText(texto);
        item.setTextColor(Color.WHITE);
        item.setTextSize(16f);
        item.setPadding(24, 24, 24, 24);

        int[] attrs = new int[]{android.R.attr.selectableItemBackground};
        TypedArray typedArray = contexto.obtainStyledAttributes(attrs);
        item.setBackground(typedArray.getDrawable(0));
        typedArray.recycle();

        item.setOnClickListener(aoClicar);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 4, 0, 4);
        item.setLayoutParams(params);

        return item;
    }

    public LinearLayout obterLayout() {
        return rootLayout;
    }

    public boolean voltarDiretorio() {
        if (diretorioAtual != null && !diretorioAtual.equals(pastaRaizProjeto)) {
            carregarDiretorio(diretorioAtual.getParentFile());
            return true;
        }
        return false;
    }
}
