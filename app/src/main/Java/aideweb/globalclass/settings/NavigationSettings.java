package aideweb.globalclass.settings;

import android.app.Activity;

public class NavigationSettings {

    public static boolean manipularBotaoVoltar(Activity activity) {
        // Se houver fragments na pilha de navegação, volta entre eles
        if (activity.getFragmentManager().getBackStackEntryCount() > 0) {
            activity.getFragmentManager().popBackStack();
            return true; // Indica que o evento foi consumido internamente
        }
        
        // Se não houver para onde voltar na UI, apenas retorna false 
        // para impedir que o app feche ou faça qualquer outra ação indesejada.
        return false; 
    }
}
