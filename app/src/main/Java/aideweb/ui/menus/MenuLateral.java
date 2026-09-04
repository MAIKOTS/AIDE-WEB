package aideweb.ui.menus;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuLateral {

    private final Context contexto;
    private LinearLayout containerMenu;

    public MenuLateral(Context contexto, boolean isEsquerdo) {
        this.contexto = contexto;
        construirMenu(isEsquerdo);
    }

    private void construirMenu(boolean isEsquerdo) {
        containerMenu = new LinearLayout(contexto);
        containerMenu.setOrientation(LinearLayout.VERTICAL);
        
        // Fundo escuro com cantos arredondados e borda sutil para o menu suspenso
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#1E1E1E"));
        drawable.setCornerRadius(16f);
        containerMenu.setBackground(drawable);
        
        containerMenu.setPadding(32, 32, 32, 32);

        // Exemplo de itens do menu
        adicionarItem("Configurações");
        adicionarItem("Sobre a IDE");
        adicionarItem("Ajuda");
    }

    private void adicionarItem(String texto) {
        TextView item = new TextView(contexto);
        item.setText(texto);
        item.setTextColor(Color.WHITE);
        item.setTextSize(16f);
        item.setPadding(16, 24, 16, 24);
        
        containerMenu.addView(item);
    }

    public LinearLayout obterPainel() {
        return containerMenu;
    }
}
