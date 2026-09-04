package aideweb.globalclass.settings;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class AppGlobalSettings {

    /**
     * 🚀 INICIALIZAÇÃO GLOBAL DO APLICATIVO
     */
    public static void inicializar(Activity activity) {
        if (activity == null) return;

        // 1. Log de Inicialização do App
        logSistema(activity, "AppGlobalSettings", "Iniciando subsistemas do MKIDE...");

        // 2. Configurações de UI e Hardware
        UISettings.aplicarModoFullScreen(activity);
        UISettings.manterTelaLigada(activity, true);
        UISettings.otimizarRenderizacaoHardware(activity);

        logSistema(activity, "AppGlobalSettings", "Todos os subsistemas foram carregados com sucesso.");
    }

    // =======================================================
    // 🪟 GERENCIAMENTO GLOBAL DE OVERLAYS / POPUPS
    // =======================================================

    public static void abrirOverlay(Activity activity, View overlayView) {
        PopUpOverlayManager.exibirOverlay(activity, overlayView);
    }

    public static boolean tratarBotaoVoltarOverlay(Activity activity) {
        return PopUpOverlayManager.fecharUltimoOverlay(activity);
    }

    public static boolean temOverlayAberto() {
        return PopUpOverlayManager.temOverlayAberto();
    }

    public static void limparTodosOverlays() {
        PopUpOverlayManager.limparTodosOverlays();
    }

    // =======================================================
    // 🔐 GERENCIAMENTO GLOBAL DE PERMISSÕES
    // =======================================================

    public static boolean temPermissaoArmazenamento(Activity activity) {
        return GerenciadorPermissoes.temPermissaoArmazenamento(activity);
    }

    public static void solicitarPermissaoArmazenamento(Activity activity) {
        GerenciadorPermissoes.solicitarPermissaoArmazenamento(activity);
    }

    public static void processarResultadoPermissao(Activity activity, int requestCode, int[] grantResults, Runnable onSuccess) {
        GerenciadorPermissoes.processarResultadoPermissao(activity, requestCode, grantResults, onSuccess);
    }

    public static boolean verificarResultadoActivityResult(Activity activity, int requestCode) {
        if (requestCode == GerenciadorPermissoes.CODIGO_REQUISICAO_GERENCIAR_STORAGE) {
            return GerenciadorPermissoes.temPermissaoArmazenamento(activity);
        }
        return false;
    }

    // =======================================================
    // 🪵 MÉTODOS PÚBLICOS DE LOG
    // =======================================================

    public static void logSistema(Context context, String tag, String mensagem) {
        LogSettings.registrar(context, LogSettings.TipoLog.SISTEMA, tag, mensagem);
    }

    public static void logCompilador(Context context, String tag, String mensagem) {
        LogSettings.registrar(context, LogSettings.TipoLog.COMPILADOR, tag, mensagem);
    }

    public static void limparLogs(Context context) {
        LogSettings.limparLogs(context);
    }

    // =======================================================
    // ⚙️ MÉTODOS UTILITÁRIOS REPASSADOS
    // =======================================================

    public static boolean manipularBotaoVoltar(Activity activity) {
        // Prioriza fechar overlays abertos antes de navegar na UI
        if (temOverlayAberto()) {
            tratarBotaoVoltarOverlay(activity);
            return true;
        }
        return NavigationSettings.manipularBotaoVoltar(activity);
    }

    public static void registrarMonitorDeMemoria(Context context, Runnable callbackLiberarMemoria) {
        MemorySettings.registrarMonitorDeMemoria(context, callbackLiberarMemoria);
    }

    public static void aplicarModoFullScreen(Activity activity) {
        UISettings.aplicarModoFullScreen(activity);
    }
}
