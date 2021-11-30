package pe.gob.vuce.zee.api.tesoreria.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class Constantes {

    public static int HABILITADO = 0;
    public static int DESHABILITADO = 9;

    public static String CONTENT_TYPE_XLSX = "application/vnd.ms-excel";
    public static String CONTENT_TYPE_CSV = "text/csv";

    public final static Map<Integer, String> ESTADOS_TIPO_CAMBIO = new HashMap<Integer, String>();
    public final static Map<Integer, String> TIPO_TRAMITES = new HashMap<Integer, String>();
    public final static Map<Integer, String> TIPO_ = new HashMap<Integer, String>();

    static {
        ESTADOS_TIPO_CAMBIO.put(1, "ACTIVO");
        ESTADOS_TIPO_CAMBIO.put(2, "INACTIVO");
    }

    static {
        TIPO_TRAMITES.put(1, "Exclusivo-TUPA");
        TIPO_TRAMITES.put(2, "No inclusivo-TUSNE");
        TIPO_TRAMITES.put(3,"Concepto");
    }

    public static <K, V> K getSingleKeyFromValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
