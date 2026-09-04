package aideweb;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import aideweb.globalclass.settings.AppGlobalSettings;
import aideweb.ui.TelaEditor;
import aideweb.ui.TelaInicial;
import aideweb.ui.TelaSelecaoArquivos;
import aideweb.ui.TelaWorkspace;
import aideweb.util.GerenciadorPermissoes;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (!GerenciadorPermissoes.verificarPermissoes(this)) {
            GerenciadorPermissoes.solicitarPermissoes(this);
        }
        
        AppGlobalSettings.aplicarModoFullScreen(this);
        AppGlobalSettings.inicializar(this);

        exibirTelaInicial();
    }

    private void exibirTelaInicial() {
        TelaInicial telaInicial = new TelaInicial(this);
        telaInicial.setOnAbrirProjetoListener(() -> {
            TelaSelecaoArquivos telaSelecao = new TelaSelecaoArquivos(this, new TelaSelecaoArquivos.ProjetoSelecionadoListener() {
                @Override
                public void onProjetoSelecionado(File pastaProjeto) {
                    TelaWorkspace workspace = new TelaWorkspace(MainActivity.this, pastaProjeto, arquivo -> {
                        TelaEditor telaEditor = new TelaEditor(MainActivity.this, arquivo);
                        setContentView(telaEditor.obterLayout());
                    });
                    setContentView(workspace.obterLayout());
                }
            });
            setContentView(telaSelecao.obterLayout());
        });
        setContentView(telaInicial.obterLayout());
    }
}
