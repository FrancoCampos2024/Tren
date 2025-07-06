package com.example.tren.Utils;

import com.example.tren.Entidades.Boleta;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import jakarta.servlet.ServletOutputStream;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BoletaPDFGenerator {

    public static void generar(ServletOutputStream out, Boleta boleta,int tiempoLlegada,int tiempoViaje) throws Exception {
        PdfWriter writer = new PdfWriter(out);
                PdfDocument pdf = new PdfDocument(writer);
                Document doc = new Document(pdf);

                // Logo corporativo en cabecera
                String imagePath = "src/main/resources/static/img/Logo.jpg";
                ImageData imageData = ImageDataFactory.create(imagePath);
                Image cabecera = new Image(imageData)
                        .setWidth(UnitValue.createPercentValue(35))
                        .setHorizontalAlignment(HorizontalAlignment.LEFT)
                        .setMarginBottom(10)
                        .setMarginTop(8);
                doc.add(cabecera);

                // Título del ticket
                Paragraph titulo = new Paragraph("BOLETA DE VIAJE")
                        .setFontSize(18)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontColor(ColorConstants.BLUE)
                        .setMarginBottom(3);
                doc.add(titulo);

                doc.add(new LineSeparator(new DottedLine()).setMarginBottom(12));

                // Información principal
                Table infoCliente = new Table(new float[]{1.3f, 2f});
                infoCliente.setWidth(UnitValue.createPercentValue(90));
                infoCliente.setHorizontalAlignment(HorizontalAlignment.CENTER);
                infoCliente.setBorder(Border.NO_BORDER);

                infoCliente.addCell(cellLabel("Cliente:", true));
                infoCliente.addCell(cellData(boleta.getNombrec().toUpperCase()));

                infoCliente.addCell(cellLabel("Salida:", true));
                infoCliente.addCell(cellData(boleta.getEstacioninicio().getNombre_estacion()));

                infoCliente.addCell(cellLabel("Destino:", true));
                infoCliente.addCell(cellData(boleta.getEstaciondestino().getNombre_estacion()));

                String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                infoCliente.addCell(cellLabel("Hora de compra:", true));
                infoCliente.addCell(cellData(horaActual));

                infoCliente.addCell(cellLabel("Fecha de compra:", true));
                infoCliente.addCell(cellData(fechaActual));

                infoCliente.addCell(cellLabel("Zona Turística:", true));
                infoCliente.addCell(cellData(boleta.getZonaturistica().getNombre()));

                infoCliente.addCell(cellLabel("Tiempo hasta abordar:", true));
                infoCliente.addCell(cellData(tiempoLlegada + " seg."));

                infoCliente.addCell(cellLabel("Tiempo de viaje:", true));
                infoCliente.addCell(cellData(tiempoViaje + " seg."));

                doc.add(infoCliente);

                // Línea divisoria elegante
                doc.add(new LineSeparator(new SolidLine()).setMarginTop(10).setMarginBottom(6));

                // Descripción de la zona
                Paragraph descripcion = new Paragraph("Descripción de la zona:")
                        .setBold()
                        .setFontColor(ColorConstants.BLUE)
                        .setFontSize(11)
                        .setMarginBottom(2)
                        .setTextAlignment(TextAlignment.LEFT);
                doc.add(descripcion);

                Paragraph detalle = new Paragraph(boleta.getZonaturistica().getDescripcion())
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setFontSize(10)
                        .setMarginBottom(8)
                        .setMarginTop(0);
                doc.add(detalle);

                // Imagen de la zona turística (centrada, tamaño elegante)
                String imagezPath = "src/main/resources/static/img/" + normalizarNombre(boleta.getZonaturistica().getNombre()) + ".jpg";
                ImageData imagezData = ImageDataFactory.create(imagezPath);
                Image zona = new Image(imagezData)
                        .setWidth(UnitValue.createPercentValue(38))
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .setMarginTop(5)
                        .setMarginBottom(14);
                doc.add(zona);

                // Tabla de resumen de compra
                Table tabla = new Table(new float[]{2, 2});
                tabla.setWidth(UnitValue.createPercentValue(80));
                tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);

                tabla.addCell(cellLabel("Cantidad de pasajeros", false));
                tabla.addCell(cellData(String.valueOf(boleta.getCantidadpasajeros())));

                tabla.addCell(cellLabel("Precio unitario", false));
                tabla.addCell(cellData("S/ 10"));

                tabla.addCell(cellLabel("Precio total", false));
                tabla.addCell(cellData("S/ " + boleta.getMonto()));

                doc.add(tabla);

                doc.add(new Paragraph("\n"));
                doc.add(new LineSeparator(new DottedLine()).setMarginBottom(2));

                // Mensaje de agradecimiento (más elegante)
                Paragraph gracias = new Paragraph("¡Gracias por confiar en nosotros!\nQue tenga un excelente viaje 🚆")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold()
                        .setFontColor(ColorConstants.BLUE)
                        .setFontSize(12)
                        .setMarginTop(15)
                        .setMarginBottom(7);
                doc.add(gracias);

                doc.close();
    }

    private static Cell cellLabel(String txt, boolean resaltado) {
        Cell c = new Cell().add(new Paragraph(txt));
        c.setBorder(Border.NO_BORDER);
        c.setFontSize(11);
        c.setPadding(2f);
        c.setPaddingLeft(1.5f);
        if (resaltado) c.setBold().setFontColor(ColorConstants.BLUE);
        else c.setBold();
        return c;
    }

    private static Cell cellData(String txt) {
        return new Cell()
                .add(new Paragraph(txt))
                .setBorder(Border.NO_BORDER)
                .setFontSize(11)
                .setPadding(2.2f)
                .setPaddingLeft(1.5f)
                .setTextAlignment(TextAlignment.LEFT);
    }

    public static String normalizarNombre(String texto) {
        if (texto == null) return "";
        // Quitar tildes
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        // Quitar caracteres especiales
        normalizado = normalizado.replaceAll("[^\\w\\s]", "");
        // Reducir espacios múltiples a uno solo
        normalizado = normalizado.replaceAll("\\s+", " ");
        // Quitar espacios al inicio y final
        return normalizado.trim();
    }
}
