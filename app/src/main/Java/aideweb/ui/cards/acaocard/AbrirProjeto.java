package aideweb.ui.cards.acaocard;

import android.app.Activity;
import android.content.Intent;

public class AbrirProjeto {

    public static final int CODIGO_SELECAO_PASTA = 1001;

    public static void executar(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        activity.startActivityForResult(intent, CODIGO_SELECAO_PASTA);
    }
}
