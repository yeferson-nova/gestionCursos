package com.ynova.gestionCursos.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ynova.gestionCursos.entity.Curso;

import jakarta.servlet.http.HttpServletResponse;

public class CursoExporterPDF {

    private List<Curso> listaCurso;

    Font font = FontFactory.getFont(FontFactory.HELVETICA);

    public CursoExporterPDF(List<Curso> listaCurso) {
        this.listaCurso = listaCurso;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("TITULO", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DESCRIPCIÓN", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("NIVEL", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("PUBLICADO", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Curso curso : listaCurso) {
            table.addCell(String.valueOf(curso.getId()));
            table.addCell(curso.getTitulo());
            table.addCell(curso.getDescripcion());
            table.addCell(String.valueOf(curso.getNivel()));
            table.addCell(String.valueOf(curso.isPublicado()));
        }
    }

    public void exportPDF(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista de cursos", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100F);
        table.setWidths(new float[] { 1.3f, 3.5f, 3.5f, 2.0f, 1.5f });
        table.setSpacingBefore(10f);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }

}