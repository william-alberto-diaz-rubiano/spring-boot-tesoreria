package pe.gob.vuce.zee.api.tesoreria.base;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class Constantes {

    public static int HABILITADO = 0;
    public static int DESHABILITADO = 9;

    public static String CONTENT_TYPE_XLSX = "application/vnd.ms-excel";
    public static String CONTENT_TYPE_CSV = "text/csv";

    public final static Map<Integer, String> ESTADOS_TIPO_CAMBIO = new HashMap<Integer, String>();

    static {
        ESTADOS_TIPO_CAMBIO.put(1, "ACTIVO");
        ESTADOS_TIPO_CAMBIO.put(1, "ACTIVO");
    }
}
