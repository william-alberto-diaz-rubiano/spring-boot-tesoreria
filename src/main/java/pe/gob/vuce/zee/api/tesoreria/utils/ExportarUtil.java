package pe.gob.vuce.zee.api.tesoreria.utils;

import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
public class ExportarUtil {
    public static void crearCSV(List<String[]> lista, String[] columnas, PrintWriter out) throws IOException {
        try (
                CSVWriter csvWriter = new CSVWriter(out,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.DEFAULT_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ){
            csvWriter.writeNext(columnas);
            lista.forEach(csvWriter::writeNext);
            log.info("CSV generado exitosamente");
        }catch (Exception e) {
            log.error("Error al generar CSV", e);
        }
    }
    public static void crearExcel(List<String[]> lista, String titulo, String nombreHoja, String[] columnas, OutputStream out){
        var wb = new XSSFWorkbook();

        // Crear hoja
        Sheet hoja = wb.createSheet(nombreHoja);

        // Crear fila 1
        Row filaTitulo = hoja.createRow(0);
        Cell celda = filaTitulo.createCell(0);
        celda.setCellValue(titulo);
        hoja.addMergedRegion(new CellRangeAddress(0,1,0,columnas.length -1));

        // Agrega estilos
        CellStyle estilo = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        estilo.setFont(font);

        filaTitulo.getCell(0).setCellStyle(estilo);

        celda.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
        celda.getCellStyle().setVerticalAlignment(VerticalAlignment.CENTER);

        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);

        // Crear 2 fila
        Row filaData = hoja.createRow(2);

        // Llenar columnas cabeceras en fila 2
        for(int i=0; i<columnas.length;i++){
            celda = filaData.createCell(i);
            celda.setCellValue(columnas[i]);
            celda.setCellStyle(estilo);
        }

        // Continúa en la fila 3, para registrar los datos de la lista
        int numFila = 3;
        int posCelda = 0;

        // Llenar filas cuerpo
        for (var obj : lista) {
            filaData = hoja.createRow(numFila);
            for(var valor : obj){
                filaData.createCell(posCelda).setCellValue(String.valueOf(valor));
                posCelda++;
            }
            posCelda = 0;
            numFila++;
        }

        //AutoSize Columnas
        for(int i=0; i<columnas.length;i++){
            hoja.autoSizeColumn(i);
        }

        // Exportar excel
        try {
            wb.write(out);
            wb.close();
            out.close();
            log.info("XLS generado existosamente");
        } catch (IOException e) {
            log.error("Error al generar XLS", e);
        }
    }

}
