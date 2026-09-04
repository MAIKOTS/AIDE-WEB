package aideweb.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class GerenciadorPermissoes {

    private static final int CODIGO_PERMISSAO_ARMAZENAMENTO = 100;

    public static boolean verificarPermissoes(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ gerencia de forma diferente ou usa escopo de arquivos
            return true; 
        } else {
            int leitura = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int escrita = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return leitura == PackageManager.PERMISSION_GRANTED && escrita == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static void solicitarPermissoes(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(
                activity,
                new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                CODIGO_PERMISSAO_ARMAZENAMENTO
            );
        }
    }
}
