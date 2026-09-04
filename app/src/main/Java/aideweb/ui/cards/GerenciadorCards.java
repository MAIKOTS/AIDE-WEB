package aideweb.ui.cards;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GerenciadorCards {

    public static LinearLayout criarCard(Context contexto, String tituloTexto, int corFundo, Runnable aoClicar) {
        LinearLayout card = new LinearLayout(contexto);
        card.setOrientation(LinearLayout.VERTICAL);
        
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(corFundo);
        drawable.setCornerRadius(24f);
        drawable.setStroke(2, Color.parseColor("#00B0FF"));
        card.setBackground(drawable);
        
        card.setPadding(48, 48, 48, 48);
        
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 16, 0, 16);
        card.setLayoutParams(cardParams);

        TextView title = new TextView(contexto);
        title.setText(tituloTexto);
        title.setTextColor(Color.parseColor("#00B0FF"));
        title.setTextSize(18f);
        title.setGravity(Gravity.CENTER);
        
        card.addView(title);
        
        int[] attrs = new int[]{android.R.attr.selectableItemBackground};
        android.content.res.TypedArray typedArray = contexto.obtainStyledAttributes(attrs);
        card.setForeground(typedArray.getDrawable(0));
        typedArray.recycle();
        
        if (aoClicar != null) {
            card.setOnClickListener(v -> aoClicar.run());
        }
        
        return card;
    }
}
