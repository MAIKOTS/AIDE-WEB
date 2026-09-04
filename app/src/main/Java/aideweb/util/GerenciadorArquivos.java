package aideweb.util;

import android.os.Environment;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivos {

    public static File obterDiretorioRaiz() {
        // Retorna o diretório de armazenamento externo público ou o diretório de documentos do app
        return Environment.getExternalStorageDirectory();
    }

    public static List<File> listarArquivos(File diretorio) {
        List<File> listaArquivos = new ArrayList<>();
        if (diretorio != null && diretorio.exists() && diretorio.isDirectory()) {
            File[] arquivos = diretorio.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    // Opcional: filtrar arquivos ocultos
                    if (!arquivo.getName().startsWith(".")) {
                        listaArquivos.add(arquivo);
                    }
                }
            }
        }
        return listaArquivos;
    }
}
