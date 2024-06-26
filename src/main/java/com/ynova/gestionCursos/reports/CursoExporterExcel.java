package com.ynova.gestionCursos.reports;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.ynova.gestionCursos.entity.Curso;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class CursoExporterExcel {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Curso> cursos;

    public CursoExporterExcel(List<Curso> cursos) {
        this.cursos = cursos;
        workbook = new XSSFWorkbook();

    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Cursos");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "TITULO", style);
        createCell(row, 2, "DESCRIPCIÓN", style);
        createCell(row, 3, "NIVEL", style);
        createCell(row, 4, "ESTADO PUBLICACIÓN", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        font.setFontHeight(14);
        style.setFont(font);

        for (Curso curso : cursos) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, curso.getId(), style);
            createCell(row, columnCount++, curso.getTitulo(), style);
            createCell(row, columnCount++, curso.getDescripcion(), style);
            createCell(row, columnCount++, curso.getNivel(), style);
            createCell(row, columnCount++, curso.isPublicado(), style);
        }
    }

    public void exportExcel(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
