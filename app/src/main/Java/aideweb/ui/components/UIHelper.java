package com.aideweb.ui.components;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UIHelper {

    public static LinearLayout createCard(Context context, String titleText, int backgroundColor, int radius) {
        LinearLayout card = new LinearLayout(context);
        card.setOrientation(LinearLayout.VERTICAL);
        
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(backgroundColor);
        drawable.setCornerRadius(radius);
        card.setBackground(drawable);
        
        card.setPadding(32, 32, 32, 32);

        TextView title = new TextView(context);
        title.setText(titleText);
        title.setTextColor(0xFFFFFFFF);
        title.setTextSize(16f);
        title.setGravity(Gravity.CENTER);
        
        card.addView(title);
        return card;
    }
}
