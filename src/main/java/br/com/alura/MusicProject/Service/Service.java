package br.com.alura.MusicProject.Service;

import br.com.alura.MusicProject.Model.Enums.Instruments;
import br.com.alura.MusicProject.Model.Enums.Styles;
import br.com.alura.MusicProject.Model.Enums.TypesOfGroup;

import java.util.List;
import java.util.function.Function;

public class Service {
    public boolean inInstrumentPortuguese(String word){
        try {
            Instruments instrument = Instruments.Portuguese(word);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }
    public boolean inInstrumentEnglish(String word){
        try {
            Instruments instrument = Instruments.English(word);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }
    public boolean inTypePortuguese(String word){
        try {
            TypesOfGroup type = TypesOfGroup.portugues(word);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }
    public boolean inTypeEnglish(String word){
        try {
            TypesOfGroup type = TypesOfGroup.english(word);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }
    public boolean inStyle(String word){
        try {
            Styles style = Styles.styles(word);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }
    public static  <T> String listName(List<T> items, Function<T, String> attribute){
        StringBuilder result = new StringBuilder();
        for (T item : items){
            result.append(attribute.apply(item)).append(", ");
        }
        if (!result.isEmpty()){
            result.delete(result.length() -2, result.length());
        }
        return result.toString();
    }
}
