package com.bryan.view;

public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;

    public static String getCssPath(FontSize fontSize){
        switch (fontSize){
            case MEDIUM:
                return "css/fontMedium.css";
            case BIG:
                return "css/fontBig.css";
            case SMALL: default:
                return "css/fontSmall.css";

            //  C:\Users\Bryan\IdeaProjects\Java\JavaFxEmailClient\src\com\bryan\view\css
        }
    }
}
