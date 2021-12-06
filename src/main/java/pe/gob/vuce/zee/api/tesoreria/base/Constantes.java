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

    public final static Map<String, String> ESTADOS_TIPO_CAMBIO = new HashMap<String, String>();
    public final static Map<Integer, String> TIPO_TRAMITES = new HashMap<Integer, String>();
    public final static Map<Integer, String> OPERACIONES = new HashMap<Integer, String>();
    public final static Map<String, String> ESTADOS_CONFIGURADOR = new HashMap<String, String>();
    public final static Map<Integer, String> ESTADOS_TRAMITE_PAGO = new HashMap<Integer, String>();

    static {
        ESTADOS_TIPO_CAMBIO.put("a2749d39-f219-8110-15ea-5749cbbab39a", "ACTIVO");
        ESTADOS_TIPO_CAMBIO.put("c5781785-66b3-2012-0178-02178be96a46", "INACTIVO");
    }
    static {
        ESTADOS_CONFIGURADOR.put("fb34b1dd-3af5-205b-3517-5720338bb33f", "ACTIVO");
        ESTADOS_CONFIGURADOR.put("789c5d18-cac5-681e-9d65-9280e3065340", "INACTIVO");
    }

    static {
        TIPO_TRAMITES.put(1, "EXCLUSIVO-TUPA");
        TIPO_TRAMITES.put(2, "NO INCLUSIVO-TUSNE");
        TIPO_TRAMITES.put(3,"CONCEPTO");
    }
    static {
        OPERACIONES.put(1, "NO GRAVADA");
        OPERACIONES.put(2, "GRAVADA");
        OPERACIONES.put(3,"OPCIONAL");
    }
    static {
        ESTADOS_TRAMITE_PAGO.put(1, "ACTIVO");
        ESTADOS_TRAMITE_PAGO.put(2, "INACTIVO");
        ESTADOS_TRAMITE_PAGO.put(3,"GUARDADO");
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
