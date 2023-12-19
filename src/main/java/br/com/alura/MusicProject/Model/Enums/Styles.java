package br.com.alura.MusicProject.Model.Enums;

public enum Styles {
    ROCK("Rock"),
    RAP("Rap"),
    SERTANEJO("Sertanejo"),
    COUNTRY("Country"),
    MPB("MPB"),
    SAMBA("Samba");
    private  String style;
    Styles(String style){
        this.style = style;
    }
    public static Styles styles(String type){
        for (Styles i : Styles.values()){
            if (i.style.equalsIgnoreCase(type)){
                return i;
            }
        }
        throw new IllegalArgumentException("Nem um estilo registrado como: " + type);
    }
}
