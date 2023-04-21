package TheBettersAPIServices.TheBettersAPIServices.Utilities;
import com.google.gson.Gson;

public class GsonUtils {//Metodo para convertir a Json
    public static String serializae(Object src){
        Gson gson = new Gson();
        return gson.toJson(src);
    }
    //Convertir de String Json a un Objeto, identidad , dto o clase
    public static <D> D toObject(String json,Class<D> dClass){
        Gson gson = new Gson();
        return gson.fromJson(json, dClass);
    }
    
    public static <D> D toObject(Object src,Class<D> dClass){
        Gson gson = new Gson();
        String srcJson = gson.toJson(src);
        return gson.fromJson(srcJson, dClass);
    }

}