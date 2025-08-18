package com.example.demo.reportes;

import com.example.demo.Paciente.service.ReportePacienteService;
import com.example.demo.Medico.service.ReporteMedicoService;
import com.example.demo.Paciente.repository.PacienteReporteDTO;
import com.example.demo.Hospital.repository.ReporteHospitalDTO;
import com.example.demo.Hospital.service.ReporteHospitalService;
import com.example.demo.Informe.repository.ReporteInformeDTO;
import com.example.demo.Informe.service.ReporteInformeService;
import com.example.demo.Medico.repository.MedicoReporteDTO;
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
public class ReporteController {

    @Autowired
    private ReportePacienteService reportePacienteService;
    private ReporteMedicoService reporteMedicoService;
    private ReporteHospitalService reporteHospitalService;
    private ReporteInformeService reporteInformeService;

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
        LinkedHashMap::new, 
        Collectors.toList()
    ));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Encabezado centrado 
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Listado de Pacientes", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Fecha del reporte centrada 
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

                // Tabla de Pacientes
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
                document.add(new Paragraph(" ")); 
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

    @GetMapping("/medicos")
    public ResponseEntity<byte[]> generarReporteMedicos(
            @RequestParam(required = false) Integer hospitalcod,
            @RequestParam(required = false) Integer departamentocod,
            @RequestParam(required = false) Integer unidadcod) {

        List<MedicoReporteDTO> medicos = reporteMedicoService
                .obtenerMedicosParaReporte(hospitalcod, departamentocod, unidadcod);

        // Agrupar por hospital, departamento y unidad
        Map<String, List<MedicoReporteDTO>> grupos = medicos.stream()
    .collect(Collectors.groupingBy(
        p -> p.getNombreHospital() + "||" + p.getNombreDepartamento() + "||" + p.getNombreUnidad(),
        LinkedHashMap::new, 
        Collectors.toList()
    ));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Encabezado centrado
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Listado de Medicos", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Fecha del reporte centrada
            Font fontFecha = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            String fechaReporte = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
            Paragraph fecha = new Paragraph("Fecha de reporte: " + fechaReporte, fontFecha);
            fecha.setAlignment(Element.ALIGN_CENTER);
            document.add(fecha);

            document.add(new Paragraph(" "));

            for (Map.Entry<String, List<MedicoReporteDTO>> entry : grupos.entrySet()) {
                String[] encabezados = entry.getKey().split("\\|\\|");
                String nombreHospital = encabezados[0];
                String nombreDepartamento = encabezados[1];
                String nombreUnidad = encabezados[2];

                // Encabezado de grupo
                document.add(new Paragraph("Hospital: " + nombreHospital));
                document.add(new Paragraph("Departamento: " + nombreDepartamento));
                document.add(new Paragraph("Unidad: " + nombreUnidad));
                document.add(new Paragraph(" "));

                // Tabla de Medicos
                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100);
                table.addCell("Nombre Completo");
                table.addCell("Numero de Licencia");
                table.addCell("Especialidad");
                table.addCell("Telefono");
                table.addCell("Años de Experiencia");
                table.addCell("Contacto");

                for (MedicoReporteDTO m : entry.getValue()) {
                    table.addCell(m.getNombreCompleto());
                    table.addCell(m.getNumLicencia());
                    table.addCell(m.getEspecialidad());
                    table.addCell(m.getTelefono());
                    table.addCell(String.valueOf(m.getAnosExperiencia()));
                    table.addCell(m.getContacto());
                }

                document.add(table);
                document.add(new Paragraph(" ")); 
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
    
    @GetMapping("/hospitales")
    public ResponseEntity<byte[]> generarReporteHospitales() {

        List<ReporteHospitalDTO> medicos = reporteHospitalService
                .obtenerHospitalesParaReporte();


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Encabezado centrado
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Listado de Hospitales", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Fecha del reporte centrada
            Font fontFecha = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            String fechaReporte = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
            Paragraph fecha = new Paragraph("Fecha de reporte: " + fechaReporte, fontFecha);
            fecha.setAlignment(Element.ALIGN_CENTER);
            document.add(fecha);

            document.add(new Paragraph(" "));

            // Tabla de Hospitales
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.addCell("Codigo del Hospital");
                table.addCell("Nombre");
                table.addCell("Cant Departamento");
                table.addCell("Cant Unudades");
                table.addCell("Cant Pacientes");


            for (ReporteHospitalDTO m : medicos) {
                table.addCell(String.valueOf(m.getId()));
                table.addCell(m.getNombre());
                table.addCell(String.valueOf(m.getCantidadDepartamentos()));
                table.addCell(String.valueOf(m.getCantidadUnidades()));
                table.addCell(String.valueOf(m.getCantidadPacientes()));      
            }

            document.add(table);
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

    @GetMapping("/hospitales/top")
    public ResponseEntity<byte[]> generarReporteHospitalesRankeados() {

        List<ReporteHospitalDTO> medicos = reporteHospitalService
                .obtenerHospitalesTopParaReporte();


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Encabezado centrado
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Listado de Hospitales con más Pacientes", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Fecha del reporte centrada
            Font fontFecha = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            String fechaReporte = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
            Paragraph fecha = new Paragraph("Fecha de reporte: " + fechaReporte, fontFecha);
            fecha.setAlignment(Element.ALIGN_CENTER);
            document.add(fecha);

            document.add(new Paragraph(" "));

            // Tabla de Hospitales
                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);
                table.addCell("Codigo del Hospital");
                table.addCell("Nombre");
                table.addCell("Cant Pacientes");


            for (ReporteHospitalDTO m : medicos) {
                table.addCell(String.valueOf(m.getId()));
                table.addCell(m.getNombre());
                table.addCell(String.valueOf(m.getCantidadPacientes()));      
            }

            document.add(table);
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

    @GetMapping("/informes")
    public ResponseEntity<byte[]> generarReporteInformes(
        @RequestParam(required = false) Integer hospitalcod,
        @RequestParam(required = false) Integer departamentocod,
        @RequestParam(required = false) Integer unidadcod) {

    List<ReporteInformeDTO> informes = reporteInformeService
            .obtenerHospitalesParaReporte(hospitalcod, departamentocod, unidadcod);

    // Agrupar por hospital, departamento y unidad
    Map<String, List<ReporteInformeDTO>> grupos = informes.stream()
        .collect(Collectors.groupingBy(
            i -> i.getHospital() + "||" + i.getDepartamento() + "||" + i.getUnidad(),
            LinkedHashMap::new,
            Collectors.toList()
        ));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Encabezado centrado
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("Informe de Consultas", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        // Fecha del reporte centrada
        Font fontFecha = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
        String fechaReporte = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
        Paragraph fecha = new Paragraph("Fecha de reporte: " + fechaReporte, fontFecha);
        fecha.setAlignment(Element.ALIGN_CENTER);
        document.add(fecha);

        document.add(new Paragraph(" "));

        for (Map.Entry<String, List<ReporteInformeDTO>> entry : grupos.entrySet()) {
            String[] encabezados = entry.getKey().split("\\|\\|");
            String nombreHospital = encabezados[0];
            String nombreDepartamento = encabezados[1];
            String nombreUnidad = encabezados[2];

            // Encabezado de grupo
            document.add(new Paragraph("Hospital: " + nombreHospital));
            document.add(new Paragraph("Departamento: " + nombreDepartamento));
            document.add(new Paragraph("Unidad: " + nombreUnidad));
            document.add(new Paragraph(" "));

            // Tabla de Informes
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.addCell("N° Turno");
            table.addCell("Hora Informe");
            table.addCell("N° Informe");
            table.addCell("Pacientes al Inicio");
            table.addCell("Admitidos");
            table.addCell("Altas");
            table.addCell("Atendidos desde Anterior");
            table.addCell("Atendidos Día");

            for (ReporteInformeDTO i : entry.getValue()) {
                table.addCell(String.valueOf(i.getNumeroTurno()));
                table.addCell(i.getHoraInforme().toString());
                table.addCell(String.valueOf(i.getNumeroInforme()));
                table.addCell(String.valueOf(i.getPacientesAlInicio()));
                table.addCell(String.valueOf(i.getPacientesAdmitidos()));
                table.addCell(String.valueOf(i.getPacientesDadosDeAlta()));
                table.addCell(String.valueOf(i.getPacientesAtendidosDesdeAnterior()));
                table.addCell(String.valueOf(i.getPacientesAtendidosDia()));
            }

            document.add(table);
            document.add(new Paragraph(" "));
        }

        document.close();

        byte[] pdfBytes = outputStream.toByteArray();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=informes.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

}