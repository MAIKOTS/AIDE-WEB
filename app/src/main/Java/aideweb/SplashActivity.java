package aideweb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import aideweb.globalclass.settings.AppGlobalSettings;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AppGlobalSettings.aplicarModoFullScreen(this);
        AppGlobalSettings.inicializar(this);
        setContentView(R.layout.activity_splash);

        long tempoInicio = System.currentTimeMillis();

        // Utiliza a Thread de background para extrair os binários pesados do SDK sem travar a UI
        new Thread(() -> {

            long tempoDecorrido = System.currentTimeMillis() - tempoInicio;
            long tempoMinimo = 2000; // 2 segundos para exibição da marca
            long delay = Math.max(0, tempoMinimo - tempoDecorrido);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (!isFinishing() && !isDestroyed()) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, delay);
        }).start();
    }
}
