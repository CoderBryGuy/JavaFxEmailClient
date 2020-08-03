package com.bryan.view;

public enum ColorTheme {
    LIGHT,
    DEFAULT,
    DARK;

    public static String getCssPath(ColorTheme colorTheme){
        switch (colorTheme){
            case LIGHT:
                return "css/themeLight.css";
            case DEFAULT:
                return "css/themeDefault.css";
            case DARK: default:
                return "css/themeDark.css";

          //  C:\Users\Bryan\IdeaProjects\Java\JavaFxEmailClient\src\com\bryan\view\css


        }
    }
}
