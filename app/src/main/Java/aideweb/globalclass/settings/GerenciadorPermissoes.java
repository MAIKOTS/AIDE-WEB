package aideweb.globalclass.settings;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

public final class GerenciadorPermissoes {

    public static final int CODIGO_REQUISICAO_PERMISSAO = 1001;
    public static final int CODIGO_REQUISICAO_GERENCIAR_STORAGE = 1002;

    private GerenciadorPermissoes() {}

    /**
     * Verifica se o aplicativo já possui permissão de leitura/escrita no armazenamento.
     */
    public static boolean temPermissaoArmazenamento(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int leitura = activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int escrita = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return leitura == PackageManager.PERMISSION_GRANTED && escrita == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    /**
     * Solicita as permissões adequadas dependendo da versão do Android do usuário.
     */
    public static void solicitarPermissaoArmazenamento(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, CODIGO_REQUISICAO_GERENCIAR_STORAGE);
            } catch (Exception e) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                activity.startActivityForResult(intent, CODIGO_REQUISICAO_GERENCIAR_STORAGE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    CODIGO_REQUISICAO_PERMISSAO
            );
        }
    }

    /**
     * Processa a resposta do usuário para permissões legadas (Android SDK < 30).
     */
    public static void processarResultadoPermissao(Activity activity, int requestCode, int[] grantResults, Runnable onSuccess) {
        if (requestCode == CODIGO_REQUISICAO_PERMISSAO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (onSuccess != null) {
                    onSuccess.run();
                }
            } else {
                Toast.makeText(activity, "Permissão de armazenamento negada.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
