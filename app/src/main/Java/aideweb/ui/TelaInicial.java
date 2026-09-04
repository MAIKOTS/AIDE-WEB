package aideweb.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import aideweb.ui.cards.GerenciadorCards;
import aideweb.ui.menus.MenuLateral;

public class TelaInicial {

    private final Context contexto;
    private RelativeLayout rootLayout;
    private OnAbrirProjetoListener abrirProjetoListener;

    public interface OnAbrirProjetoListener {
        void onAbrirProjetoClicado();
    }

    public void setOnAbrirProjetoListener(OnAbrirProjetoListener listener) {
        this.abrirProjetoListener = listener;
    }

    public TelaInicial(Context contexto) {
        this.contexto = contexto;
        construirInterface();
    }

    private void construirInterface() {
        rootLayout = new RelativeLayout(contexto);
        rootLayout.setBackgroundColor(Color.parseColor("#121212"));
        rootLayout.setLayoutParams(new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        ));

        RelativeLayout topBar = new RelativeLayout(contexto);
        RelativeLayout.LayoutParams topParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            140
        );
        topParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        topBar.setLayoutParams(topParams);

        ImageButton leftBurger = criarBotaoMenu(android.R.drawable.ic_menu_sort_by_size);
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(96, 96);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL);
        leftParams.setMargins(24, 0, 0, 0);
        leftBurger.setLayoutParams(leftParams);
        leftBurger.setOnClickListener(v -> abrirMenuLateral(true));

        ImageButton rightBurger = criarBotaoMenu(android.R.drawable.ic_menu_manage);
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(96, 96);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rightParams.setMargins(0, 0, 24, 0);
        rightBurger.setLayoutParams(rightParams);
        rightBurger.setOnClickListener(v -> abrirMenuLateral(false));

        topBar.addView(leftBurger);
        topBar.addView(rightBurger);
        rootLayout.addView(topBar);

        LinearLayout centerContainer = new LinearLayout(contexto);
        centerContainer.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams centerParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        centerParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        centerParams.setMargins(64, 0, 64, 0);
        centerContainer.setLayoutParams(centerParams);

        LinearLayout cardNew = GerenciadorCards.criarCard(contexto, "Novo Projeto", Color.parseColor("#1E1E1E"), () -> {
        });

        LinearLayout cardOpen = GerenciadorCards.criarCard(contexto, "Abrir Projeto", Color.parseColor("#1E1E1E"), () -> {
            if (abrirProjetoListener != null) {
                abrirProjetoListener.onAbrirProjetoClicado();
            }
        });

        LinearLayout cardTemplates = GerenciadorCards.criarCard(contexto, "Templates", Color.parseColor("#1E1E1E"), () -> {
        });

        centerContainer.addView(cardNew);
        centerContainer.addView(cardOpen);
        centerContainer.addView(cardTemplates);

        rootLayout.addView(centerContainer);
    }

    public RelativeLayout obterLayout() {
        return rootLayout;
    }

    private ImageButton criarBotaoMenu(int iconeResId) {
        ImageButton btn = new ImageButton(contexto);
        btn.setImageResource(iconeResId);
        btn.setColorFilter(Color.parseColor("#00B0FF"), PorterDuff.Mode.SRC_IN);
        
        int[] attrs = new int[]{android.R.attr.selectableItemBackgroundBorderless};
        android.content.res.TypedArray typedArray = contexto.obtainStyledAttributes(attrs);
        btn.setBackground(typedArray.getDrawable(0));
        typedArray.recycle();
        
        return btn;
    }

    private void abrirMenuLateral(boolean esquerdo) {
        MenuLateral menu = new MenuLateral(contexto, esquerdo);
        View menuExistente = rootLayout.findViewWithTag("TAG_MENU_LATERAL");
        if (menuExistente != null) {
            rootLayout.removeView(menuExistente);
        }
        
        LinearLayout painel = menu.obterPainel();
        painel.setTag("TAG_MENU_LATERAL");
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(600, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, rootLayout.getChildAt(0).getId());
        if (esquerdo) {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.setMargins(24, 0, 0, 0);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.setMargins(0, 0, 24, 0);
        }
        
        painel.setLayoutParams(params);
        rootLayout.addView(painel);
        
        rootLayout.setOnClickListener(v -> {
            rootLayout.removeView(painel);
            rootLayout.setOnClickListener(null);
        });
    }
}
