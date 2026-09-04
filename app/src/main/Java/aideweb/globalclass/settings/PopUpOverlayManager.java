package aideweb.globalclass.settings;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Stack;

public class PopUpOverlayManager {

    private static final Stack<View> pilhaOverlays = new Stack<>();

    public static void exibirOverlay(Activity activity, View overlayView) {
        if (activity == null || overlayView == null) return;

        if (!pilhaOverlays.isEmpty() && pilhaOverlays.peek() == overlayView) {
            return;
        }

        ViewGroup rootContainer = activity.findViewById(android.R.id.content);
        if (rootContainer != null) {
            if (overlayView.getParent() instanceof ViewGroup) {
                ((ViewGroup) overlayView.getParent()).removeView(overlayView);
            }

            rootContainer.addView(overlayView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            ));

            pilhaOverlays.push(overlayView);
            AppGlobalSettings.aplicarModoFullScreen(activity);
        }
    }

    public static boolean fecharUltimoOverlay(Activity activity) {
        while (!pilhaOverlays.isEmpty()) {
            View ultimoOverlay = pilhaOverlays.pop();

            if (ultimoOverlay != null && ultimoOverlay.getParent() instanceof ViewGroup) {
                ((ViewGroup) ultimoOverlay.getParent()).removeView(ultimoOverlay);

                if (activity != null) {
                    AppGlobalSettings.aplicarModoFullScreen(activity);
                }

                return true;
            }
        }

        return false;
    }

    public static void limparTodosOverlays() {
        while (!pilhaOverlays.isEmpty()) {
            View overlay = pilhaOverlays.pop();
            if (overlay != null && overlay.getParent() instanceof ViewGroup) {
                ((ViewGroup) overlay.getParent()).removeView(overlay);
            }
        }
    }

    public static boolean temOverlayAberto() {
        while (!pilhaOverlays.isEmpty() && !(pilhaOverlays.peek().getParent() instanceof ViewGroup)) {
            pilhaOverlays.pop();
        }
        return !pilhaOverlays.isEmpty();
    }
}
