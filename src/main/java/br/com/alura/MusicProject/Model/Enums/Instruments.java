package br.com.alura.MusicProject.Model.Enums;

public enum Instruments {
    GUITAR("Guitar","Violão"),
    DRUMS("Drums","Bateria"),
    BASS("Bass","Baixo"),
    VOICE("Voice","Voz"),
    ELETRIC_GUITAR("Eletric guitar","Guitarra");
    private  String instrumentEn;
    private  String instrumentPt;
    Instruments(String instrumentEn, String instrumentPt){
        this.instrumentEn = instrumentEn;
        this.instrumentPt = instrumentPt;
    }
    public static Instruments English(String instrument){
        for (Instruments i : Instruments.values()){
            if (i.instrumentEn.equalsIgnoreCase(instrument)){
                return i;
            }
        }
        throw new IllegalArgumentException("Nem um instrumento registrado como: " + instrument);
    }
    public static Instruments Portuguese(String instrument){
        for (Instruments i : Instruments.values()){
            if (i.instrumentPt.equalsIgnoreCase(instrument)){
                return i;
            }
        }
        throw new IllegalArgumentException("Nem um instrumento registrado como: " + instrument);
    }
}
