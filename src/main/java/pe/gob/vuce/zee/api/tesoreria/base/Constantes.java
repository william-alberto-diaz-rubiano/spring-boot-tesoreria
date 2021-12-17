package pe.gob.vuce.zee.api.tesoreria.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Constantes {

    public static int HABILITADO = 0;
    public static int DESHABILITADO = 9;

    public static String CONTENT_TYPE_XLSX = "application/vnd.ms-excel";
    public static String CONTENT_TYPE_CSV = "text/csv";

    public final static Map<Integer, String> TIPO_TRAMITES = new HashMap<Integer, String>();
    public final static Map<Integer, String> OPERACIONES = new HashMap<Integer, String>();
    public final static Map<String, String> ESTADOS_UIT = new HashMap<String, String>();
    public final static Map<String, String> ESTADOS_CONCEPTOS_PAGO = new HashMap<String, String>();
    public final static Map<String, String> CRITERIOS_CONCEPTOS_PAGO = new HashMap<String, String>();
    public final static Map<String, String> MONEDA = new HashMap<String, String>();
    public final static Map<String, String> ESTADOS_REGISTRO_COMPROBANTES = new HashMap<String, String>();


    static {
        ESTADOS_REGISTRO_COMPROBANTES.put("6fefbe72-627b-4b22-aea2-5ffe41c2ce92", "ACTIVO");
        ESTADOS_REGISTRO_COMPROBANTES.put("b4900b9f-8b8a-bd9f-2e76-5bbb777d1c79", "INACTIVO");
    }

    static {
        ESTADOS_CONCEPTOS_PAGO.put("c49340d3-1eea-8e3d-7c46-b1f599b24041", "ACTIVO");
        ESTADOS_CONCEPTOS_PAGO.put("b84ecadd-ab97-63f3-84f7-31d72cc3824b", "INACTIVO");
    }
    static {
        CRITERIOS_CONCEPTOS_PAGO.put("1e41b7a7-860f-6ebf-1507-1c659556373f", "UNIDAD");
        CRITERIOS_CONCEPTOS_PAGO.put("d670cbe6-5ef3-a67f-0389-51b4062ea59e", "DIA");
        CRITERIOS_CONCEPTOS_PAGO.put("ec5f6d48-8f0d-bb06-e56f-c3f1ea448f94", "MES");
        CRITERIOS_CONCEPTOS_PAGO.put("4615788f-9be1-997f-85a9-663266b85131", "AÑO");
        CRITERIOS_CONCEPTOS_PAGO.put("25b9157e-5444-5174-67d7-558be927ecfe", "HORA");
    }
    static {
        MONEDA.put("52363780-6174-e0c2-8bf6-c88aae514579", "SOLES");
        MONEDA.put("730434e3-999b-c8a7-b65e-89aa59adf10d", "USD");
    }

    static {
        ESTADOS_UIT.put("dd996bf3-7a6b-765f-51b2-977de6c25994", "ACTIVO");
        ESTADOS_UIT.put("ecb19b25-e43d-e945-4186-a3282a2e566b", "INACTIVO");
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

    public static <K, V> K getSingleKeyFromValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
