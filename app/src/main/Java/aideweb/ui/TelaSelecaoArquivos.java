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

public class TelaSelecaoArquivos {

    private final Context contexto;
    private final ScrollView rootLayout;
    private final LinearLayout containerLista;
    private File diretorioAtual;
    private final ProjetoSelecionadoListener listener;

    public interface ProjetoSelecionadoListener {
        void onProjetoSelecionado(File pastaProjeto);
    }

    public TelaSelecaoArquivos(Context contexto, ProjetoSelecionadoListener listener) {
        this.contexto = contexto;
        this.listener = listener;
        this.diretorioAtual = GerenciadorArquivos.obterDiretorioRaiz();

        rootLayout = new ScrollView(contexto);
        rootLayout.setBackgroundColor(Color.parseColor("#121212"));
        
        containerLista = new LinearLayout(contexto);
        containerLista.setOrientation(LinearLayout.VERTICAL);
        containerLista.setPadding(32, 32, 32, 32);
        
        rootLayout.addView(containerLista);
        carregarDiretorio(diretorioAtual);
    }

    private void carregarDiretorio(File diretorio) {
        containerLista.removeAllViews();
        
        TextView txtCaminho = new TextView(contexto);
        txtCaminho.setText("📁 " + diretorio.getAbsolutePath());
        txtCaminho.setTextColor(Color.parseColor("#00B0FF"));
        txtCaminho.setTextSize(16f);
        txtCaminho.setPadding(0, 0, 0, 32);
        containerLista.addView(txtCaminho);

        if (diretorio.getParentFile() != null) {
            TextView btnVoltar = criarItemVisual("📁 .. (Pasta Acima)", v -> {
                diretorioAtual = diretorio.getParentFile();
                carregarDiretorio(diretorioAtual);
            });
            containerLista.addView(btnVoltar);
        }

        List<File> arquivos = GerenciadorArquivos.listarArquivos(diretorio);
        for (File arquivo : arquivos) {
            if (arquivo.isDirectory()) {
                TextView itemView = criarItemVisual("📂 " + arquivo.getName(), v -> {
                    diretorioAtual = arquivo;
                    carregarDiretorio(diretorioAtual);
                });
                containerLista.addView(itemView);
            }
        }

        TextView btnSelecionar = criarItemVisual("✔ [ Usar esta pasta ]", v -> {
            if (listener != null) {
                listener.onProjetoSelecionado(diretorio);
            }
        });
        btnSelecionar.setTextColor(Color.parseColor("#00B0FF"));
        containerLista.addView(btnSelecionar);
    }

    private TextView criarItemVisual(String texto, View.OnClickListener aoClicar) {
        TextView item = new TextView(contexto);
        item.setText(texto);
        item.setTextColor(Color.WHITE);
        item.setTextSize(18f);
        item.setPadding(32, 36, 32, 36);
        
        int[] attrs = new int[]{android.R.attr.selectableItemBackground};
        TypedArray typedArray = contexto.obtainStyledAttributes(attrs);
        item.setBackground(typedArray.getDrawable(0));
        typedArray.recycle();
        
        item.setOnClickListener(aoClicar);
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8);
        item.setLayoutParams(params);

        return item;
    }

    public ScrollView obterLayout() {
        return rootLayout;
    }
}
