package aideweb.globalclass.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class AppStateSettings {

    private static final String PREF_NAME = "mkide_state";
    private static final String KEY_CODIGO = "ultimo_codigo";
    private static final String KEY_CURSOR_POS = "posicao_cursor";
    private static final String KEY_CAMINHO_ARQUIVO = "caminho_arquivo_aberto";
    private static final String KEY_TELA_ATUAL = "tela_atual";

    public static void salvarEstadoCompleto(Context context, String codigo, int posicaoCursor, String caminhoArquivo, String telaAtual) {
        if (context == null) return;

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_CODIGO, codigo)
                .putInt(KEY_CURSOR_POS, posicaoCursor)
                .putString(KEY_CAMINHO_ARQUIVO, caminhoArquivo)
                .putString(KEY_TELA_ATUAL, telaAtual)
                .apply();
    }

    public static void salvarEstado(Context context, String codigo, int posicaoCursor) {
        if (context == null) return;

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_CODIGO, codigo)
                .putInt(KEY_CURSOR_POS, posicaoCursor)
                .apply();
    }

    public static String obterCodigoSalvo(Context context) {
        if (context == null) return "";
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_CODIGO, "");
    }

    public static int obterPosicaoCursorSalva(Context context) {
        if (context == null) return 0;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_CURSOR_POS, 0);
    }

    public static String obterCaminhoArquivoSalvo(Context context) {
        if (context == null) return null;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_CAMINHO_ARQUIVO, null);
    }

    public static String obterTelaAtualSalva(Context context) {
        if (context == null) return null;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_TELA_ATUAL, null);
    }
}
