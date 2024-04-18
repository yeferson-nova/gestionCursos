package com.ynova.gestionCursos.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynova.gestionCursos.entity.Curso;
import com.ynova.gestionCursos.reports.CursoExporterExcel;
import com.ynova.gestionCursos.reports.CursoExporterPDF;
import com.ynova.gestionCursos.repository.CursoRespository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CursoController {

    @Autowired
    private CursoRespository respository;

    @GetMapping("/")
    public String home() {

        return "redirect:/cursos";
    }

    @GetMapping("/cursos")
    public String listarCurso(Model model) {

        List<Curso> cursos = respository.findAll();
        cursos = respository.findAll();
        model.addAttribute("cursos", cursos);
        return "cursos";
    }

    @GetMapping("/cursos/nuevo")
    public String agregarCurso(Model model) {

        Curso curso = new Curso();

        curso.setPublicado(true);

        model.addAttribute("curso", curso);
        model.addAttribute("pageTitle", "Nuevo Curso");
        return "NuevoCursoForm";
    }

    @PostMapping("/cursos/save")
    public String guardarCurso(Curso curso, RedirectAttributes redirectAttributes) {
        try {
            respository.save(curso);
            redirectAttributes.addFlashAttribute("message", "El curso se guardo correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cursos";
    }

    @GetMapping("/cursos/{id}")
    public String editarCurso(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Curso curso = respository.findById(id).get();
            model.addAttribute("curso", curso);
            model.addAttribute("pageTitle", "Actualizar Curso: " + "ID: " + curso.getId() + " - " + curso.getTitulo());
            return "NuevoCursoForm";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cursos";
    }

    @GetMapping("/cursos/delete/{id}")
    public String eliminarCurso(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            respository.deleteById(id);
            model.addAttribute("pageTitle", "Eliminar Curso: " + "ID: " + id);
            redirectAttributes.addFlashAttribute("message", "El curso se actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cursos";
    }

    @GetMapping("/export/pdf")
    public void generarReportePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cursos" + " - " + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Curso> cursos = respository.findAll();

        CursoExporterPDF exporterPDF = new CursoExporterPDF(cursos);
        exporterPDF.exportPDF(response);

    }

    @GetMapping("/export/excel")
    public void generarReporteExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cursos" + " - " + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Curso> cursos = respository.findAll();

        CursoExporterExcel exporterExcel = new CursoExporterExcel(cursos);
        exporterExcel.exportExcel(response);

    }

}
