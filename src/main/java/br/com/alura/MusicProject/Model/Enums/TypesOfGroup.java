package br.com.alura.MusicProject.Model.Enums;

public enum TypesOfGroup {
    SOLO("Solo","Solo"),
    DUET("Duet","Dueto"),
    TRIO("Trio","Trio"),
    BAND("Band","Banda");
    private  String typePt;
    private  String typeEn;
    TypesOfGroup(String typeEn, String typePt){
        this.typeEn = typeEn;
        this.typePt = typePt;
    }

    public static TypesOfGroup english(String type){
        for (TypesOfGroup i : TypesOfGroup.values()){
            if (i.typeEn.equalsIgnoreCase(type)){
                return i;
            }
        }
        throw new IllegalArgumentException("Nem um tipo de grupo registrado como: " + type);
    }
    public static TypesOfGroup portugues(String type){
        for (TypesOfGroup i : TypesOfGroup.values()){
            if (i.typePt.equalsIgnoreCase(type)){
                return i;
            }
        }
        throw new IllegalArgumentException("Nem um tipo de grupo registrado como: " + type);
    }
}
