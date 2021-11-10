package pe.gob.vuce.zee.api.maestros.base;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class Constantes {
    public static int TIPO_ETAPA = 1;
    public static int TIPO_MANZANA = 2;
    public static int TIPO_BLOQUE = 3;

    public static int HABILITADO = 0;
    public static int DESHABILITADO = 9;

    public static String CONTENT_TYPE_XLSX = "application/vnd.ms-excel";
    public static String CONTENT_TYPE_CSV = "text/csv";

    public static UUID UID_TEST = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static int NO_ERROR = 0;

    public static String RENIEC_APLICACION = "ZEE2";

    public static final Pattern DNI_REGEX = Pattern.compile("^[0-9]{8}$");
    public static final Pattern RUC_REGEX = Pattern.compile("^[0-9]{11}$");
}
