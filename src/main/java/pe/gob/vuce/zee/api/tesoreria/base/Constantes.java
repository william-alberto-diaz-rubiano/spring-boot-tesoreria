package pe.gob.vuce.zee.api.tesoreria.base;

import java.util.UUID;
import java.util.regex.Pattern;

public class Constantes {
    private Constantes() {
    }

    public final static int HABILITADO = 0;
    public final static int DESHABILITADO = 9;
    public final static UUID UID_TEST = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public final static int NO_ERROR = 0;

    public final static String RENIEC_APLICACION = "ZEE2";

    public static final Pattern DNI_REGEX = Pattern.compile("^[0-9]{8}$");
    public static final Pattern RUC_REGEX = Pattern.compile("^[0-9]{11}$");

}
