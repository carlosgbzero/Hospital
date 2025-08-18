package com.example.demo.Paciente.controller;

import com.example.demo.Paciente.service.ReportePacienteService;
import com.example.demo.Paciente.repository.PacienteReporteDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/reportes")
public class ReportePacienteController {

    @Autowired
    private ReportePacienteService reportePacienteService;

    @GetMapping("/pacientes")
    public ResponseEntity<byte[]> generarReportePacientes(
            @RequestParam(required = false) Integer hospitalcod,
            @RequestParam(required = false) Integer departamentocod,
            @RequestParam(required = false) Integer unidadcod) {

        List<PacienteReporteDTO> pacientes = reportePacienteService
                .obtenerPacientesParaReporte(hospitalcod, departamentocod, unidadcod);

        // Agrupar por hospital, departamento y unidad
        Map<String, List<PacienteReporteDTO>> grupos = pacientes.stream()
    .collect(Collectors.groupingBy(
        p -> p.getNombreHospital() + "||" + p.getNombreDepartamento() + "||" + p.getNombreUnidad(),
        LinkedHashMap::new, // <-- Esto mantiene el orden
        Collectors.toList()
    ));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

	    // Encabezado

            // Encabezado centrado y con fuente grande
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Listado de Pacientes", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Fecha del reporte centrada y con fuente más pequeña
            Font fontFecha = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            String fechaReporte = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
            Paragraph fecha = new Paragraph("Fecha de reporte: " + fechaReporte, fontFecha);
            fecha.setAlignment(Element.ALIGN_CENTER);
            document.add(fecha);

            document.add(new Paragraph(" "));

            for (Map.Entry<String, List<PacienteReporteDTO>> entry : grupos.entrySet()) {
                String[] encabezados = entry.getKey().split("\\|\\|");
                String nombreHospital = encabezados[0];
                String nombreDepartamento = encabezados[1];
                String nombreUnidad = encabezados[2];

                // Encabezado de grupo
                document.add(new Paragraph("Hospital: " + nombreHospital));
                document.add(new Paragraph("Departamento: " + nombreDepartamento));
                document.add(new Paragraph("Unidad: " + nombreUnidad));
                document.add(new Paragraph(" "));

                // Tabla de pacientes
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.addCell("Historia Clínica");
                table.addCell("Nombre Completo");
                table.addCell("Fecha de Nacimiento");
                table.addCell("Dirección");

                for (PacienteReporteDTO p : entry.getValue()) {
                    table.addCell(String.valueOf(p.getHistoriaclinicanum()));
                    table.addCell(p.getNombreCompleto());
                    table.addCell(p.getFechaNacimiento());
                    table.addCell(p.getDireccion());
                }

                document.add(table);
                document.add(new Paragraph(" ")); // Espacio entre tablas
            }

            document.close();

            byte[] pdfBytes = outputStream.toByteArray();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pacientes.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}