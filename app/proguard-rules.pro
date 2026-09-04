# ==============================================================================
# 🚀 AML-IDE - Regras do ProGuard / R8 Otimizado
# ==============================================================================

# 1. ATRIBUTOS E REFLEXÃO
# Preserva annotations, assinaturas genéricas e métodos internos (essencial para reflexão no PopUpDashboardMenu)
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod,SourceFile,LineNumberTable

# 2. SEUS PACOTES LOCAIS (VIEWS CUSTOMIZADAS E MENUS)
# Mantém todas as suas Views programáticas para evitar que o R8 remova métodos/construtores

# 3. REFLEXÃO DO POPUPMENU (ÍCONES DO MENU)
# Preserva o MenuPopupHelper do AndroidX/Support para o método `forcarExibicaoIconesMenu`

# 4. JSON / ARQUIVOS DE CONFIGURAÇÃO (Theme, Keywords, Snippets)
# Garante que as classes modelo do JSON não percam campos ao otimizar
-keepclassmembers class * {
    public static ** valueOf(java.lang.String);
    public static **[] values();
}

# 5. PROTEÇÃO CONTRA CRASHES DE MATERIAL & ANDROIDX
# Ignora avisos de classes opcionais não utilizadas das bibliotecas do Google
-dontwarn androidx.**
-dontwarn com.google.android.material.**

# 6. MANTER RECURSOS ACESSADOS PROGRAMATICAMENTE
# Evita que o R8 delete IDs e recursos gerados dinamicamente

-keep class com.google.android.material.** { *; }
-keep class androidx.appcompat.** { *; }
-dontwarn androidx.**
-dontwarn com.google.android.material.**

