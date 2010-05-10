package edu.opencampus.lms.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import edu.opencampus.lms.dao.AulaVirtualDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGDialogo;
import edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGLaboratorio;
import edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGTest;
import edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGTrabajo;
import edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGTrabajoGrupal;
import edu.opencampus.lms.modelo.aulavirtual.reporte.ReporteDetalle;

public class PDFReport {

	protected static Log log = LogFactory.getLog(new Archivo().getClass());
	
	public static void main(String[] args) throws DAOException, IOException{
		
		AulaVirtualDAO aDAO = new AulaVirtualDAO();
		
			int idMatricula = 943;
			AulaVirtual aula = aDAO.obtenerAulaTmpParaAuditoria(idMatricula);
			ReporteDetalle reporte = aDAO.obtenerReporteDetalle(idMatricula);
			String out = "C:\\HelloWorld.pdf";
			generarReportePDF(aula,reporte,new FileOutputStream(out));

	}

	public static Document getNewDocumentPDF() throws Exception{
		Document doc = new Document(PageSize.A4,0,0,0,0);
		
		doc.addTitle("Reporte de Alumno");
		doc.addAuthor("Erick Benites");
		doc.addSubject("Reporte de alumnos del Campus Virtual");
		doc.addKeywords("opencampus Virtual, alumno, reporte, auditoria, nota");
		doc.addCreator("El Campus usa iText");
		
		return doc;
	}
	
	public static void generarReportePDF(AulaVirtual aula, ReporteDetalle reporte, OutputStream out){
		if(aula != null && reporte != null && out != null){
			try{
				Document doc = getNewDocumentPDF();
				PdfWriter writer = PdfWriter.getInstance(doc, out);
				//writer.setCloseStream(false);//Para que no me cierre el flujo del Zip
				doc.open();
				
				dibujarReportePDF(aula, reporte,doc);
								
				doc.close();
				writer.close();

				
			} catch (Exception e) {
				log.error(e.toString());
			}
		}
	}
	
	public static void dibujarReportePDF(AulaVirtual aula, ReporteDetalle reporte,Document doc) throws Exception{
		if(aula != null && reporte != null && doc != null){
			// Encabezado
			float[] widths = {400,175};
			PdfPTable table = new PdfPTable(widths);
			table.setWidthPercentage(100);
			
			PdfPCell cell = new PdfPCell(new Phrase(" "));
			cell.setBackgroundColor(new Color(0x00, 0xA2, 0xE3));
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			Image jpg = Image.getInstance(Constante.HOME_JASPER + Constante.SLASH + "logo_auditoria.jpg");
			jpg.scaleAbsolute(175,33);
			cell = new PdfPCell(jpg);
			cell.setBorder(PdfPCell.NO_BORDER);
			table.addCell(cell);
			
			doc.add(table);
			// Fin Encabezado
			
			//Titulo Reporte
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setPaddingTop(12);
			table.getDefaultCell().setPaddingBottom(3);
			table.getDefaultCell().setPaddingLeft(30);
			table.getDefaultCell().setPaddingRight(30);
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			table.addCell(new Phrase("REPORTE DE ALUMNO", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
			
			//Tabla Datos
			float[] widths1 = {80,220,80,220};
			PdfPTable subTable = new PdfPTable(widths1);
			subTable.setWidthPercentage(100);
			subTable.getDefaultCell().setPadding(3);
			subTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			subTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			subTable.getDefaultCell().setBorderColor(new Color(0x7E, 0xAA, 0xD1));
			subTable.getDefaultCell().setBorderWidth(1);
			subTable.getDefaultCell().setMinimumHeight(16);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("Datos Generales", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new Color(0x00, 0xA2, 0xE3))));
			cell.setColspan(4);
			cell.setBorder(Rectangle.BOX);
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			subTable.addCell(cell);
			//**
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("Nombre :", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
			cell.setBorder(Rectangle.LEFT);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(reporte.getNombreCompleto(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
			cell.setBorder(Rectangle.LEFT);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("Fecha Inicio :", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
			cell.setBorder(Rectangle.LEFT);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(reporte.getStringFecha1(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
			cell.setBorder(12);
			subTable.addCell(cell);
			//**
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("Curso :", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			cell.setBorder(Rectangle.LEFT);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(reporte.getNombreCurso(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			cell.setBorder(Rectangle.LEFT);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("Fecha Fin :", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			cell.setBorder(Rectangle.LEFT);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(reporte.getStringFecha2(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			cell.setBorder(12);
			subTable.addCell(cell);
			//**
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("Programa :", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
			cell.setBorder(Rectangle.LEFT);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(reporte.getNombreEspecialidad()+ reporte.getFormacionSeccion(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
			cell.setBorder(Rectangle.LEFT);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("Ingresos al Curso :", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
			cell.setBorder(Rectangle.LEFT);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(String.valueOf(reporte.getCantidadIngreso()), FontFactory.getFont(FontFactory.HELVETICA, 7)));
			cell.setBorder(12);
			subTable.addCell(cell);
			//**
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("Fecha Reporte :", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			cell.setBorder(6);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(Formato.calendarToString(new GregorianCalendar(),Constante.FECHA_DIA_MES_ANO_HORA_MI), FontFactory.getFont(FontFactory.HELVETICA, 7)));
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			cell.setBorder(6);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			cell.setBorder(6);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7)));
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			cell.setBorder(14);
			subTable.addCell(cell);
			
			table.addCell(subTable);
			//FIN Tabla Datos
			
			//EVALUACION
			float[] widthsUnidades = new float[reporte.getCantidadUnidades()+4];
			for (int i = 1; i < widthsUnidades.length; i++) {
				if(reporte.getCantidadUnidades()>=i)
					widthsUnidades[i] = (float)0.60/reporte.getCantidadUnidades();
				else
					widthsUnidades[i] = 0.08f;
			}
			widthsUnidades[0]=0.16f;
			
			if(aula.getCantidadTest()>0){
				subTable = new PdfPTable(widthsUnidades);
				subTable.setWidthPercentage(100);
				subTable.getDefaultCell().setPadding(3);
				subTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				subTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				subTable.getDefaultCell().setBorderColor(new Color(0x7E, 0xAA, 0xD1));
				subTable.getDefaultCell().setBorderWidth(1);
				subTable.getDefaultCell().setMinimumHeight(16);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Evaluación", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				for (int i = 1; i <= reporte.getCantidadUnidades(); i++) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(String.valueOf(i), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.BOX);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
				}
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Pr. Pa.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Peso", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Pr. Ac.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				//***
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Nota Mín.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				subTable.addCell(cell);
				
				for (MatriculaGTest test : reporte.getRecursoTest()) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(test.getNotaMinima(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					subTable.addCell(cell);
				}
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(12);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				//***
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Nota Máx.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				for (MatriculaGTest test : reporte.getRecursoTest()) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(test.getNotaMaxima(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
				}
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(12);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				//***
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Promedio", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				subTable.addCell(cell);
				
				for (MatriculaGTest test : reporte.getRecursoTest()) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(test.getNotaPromedio(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					subTable.addCell(cell);
				}
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaTest().getNota(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaTest().getPeso()+"%", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaTest().getPromedioParcial(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(12);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				//***
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Veces", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(6);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				for (MatriculaGTest test : reporte.getRecursoTest()) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(String.valueOf(test.getVeces()), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(6);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
				}
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(6);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(6);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(14);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				
				table.addCell(subTable);
			}
			//FIN Evaluacion
			
			//Laboratorio
			
			if(aula.getCantidadLaboratoriosCalificados()>0){
				subTable = new PdfPTable(widthsUnidades);
				subTable.setWidthPercentage(100);
				subTable.getDefaultCell().setPadding(3);
				subTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				subTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				subTable.getDefaultCell().setBorderColor(new Color(0x7E, 0xAA, 0xD1));
				subTable.getDefaultCell().setBorderWidth(1);
				subTable.getDefaultCell().setMinimumHeight(16);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Laboratorio", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				for (int i = 1; i <= reporte.getCantidadUnidades(); i++) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(String.valueOf(i), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.BOX);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
				}
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Pr. Pa.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Peso", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Pr. Ac.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				//***
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Estado", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				subTable.addCell(cell);
				
				for (MatriculaGLaboratorio lab : reporte.getRecursoLaboratorio()) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(lab.getEstadoString(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					subTable.addCell(cell);
				}
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(12);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				//***
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Nota", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(6);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				for (MatriculaGLaboratorio lab : reporte.getRecursoLaboratorio()) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(lab.getNota(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(6);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
				}
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaLaboratorio().getNota(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(6);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaLaboratorio().getPeso()+"%", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(6);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaLaboratorio().getPromedioParcial(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(14);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				
				table.addCell(subTable);
			}
			//FIN Laboratorio
			
			//Dialogo
			
			if(aula.getCantidadDialogos()>0){
				subTable = new PdfPTable(widthsUnidades);
				subTable.setWidthPercentage(100);
				subTable.getDefaultCell().setPadding(3);
				subTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				subTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				subTable.getDefaultCell().setBorderColor(new Color(0x7E, 0xAA, 0xD1));
				subTable.getDefaultCell().setBorderWidth(1);
				subTable.getDefaultCell().setMinimumHeight(16);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Diálogo", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				for (int i = 1; i <= reporte.getCantidadUnidades(); i++) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(String.valueOf(i), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.BOX);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
				}
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				cell.setColspan(3);
				subTable.addCell(cell);
				//***
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Cantidad", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(6);
				subTable.addCell(cell);
				
				for (MatriculaGDialogo dia : reporte.getRecursoDialogo()) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(dia.getIntervencion()+"/"+dia.getIntervencionTotal(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(6);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					subTable.addCell(cell);
				}
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(14);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(3);
				subTable.addCell(cell);
							
				table.addCell(subTable);
			}
			//FIN Dialogo
			
			//Trabajo
			
			if(aula.getCantidadTrabajos()>0){
				float[] widthsVertical = {0.56f,0.1f,0.1f,0.08f,0.08f,0.08f};
				subTable = new PdfPTable(widthsVertical);
				subTable.setWidthPercentage(100);
				subTable.getDefaultCell().setPadding(3);
				subTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				subTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				subTable.getDefaultCell().setBorderColor(new Color(0x7E, 0xAA, 0xD1));
				subTable.getDefaultCell().setBorderWidth(1);
				subTable.getDefaultCell().setMinimumHeight(16);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Trabajo", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Nota", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Estado", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Pr. Pa.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Peso", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Pr. Ac.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				
				int i = 1;
				for (MatriculaGTrabajo job : reporte.getRecursoTrabajo()) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase("Trabajo N°"+i, FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.LEFT);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(job.getNota(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(job.getEstadoString(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
					cell.setBorder(12);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
					subTable.addCell(cell);
					i++;
				}
				//**
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7)));
				cell.setBorder(Rectangle.BOX);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaTrabajo().getNota(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaTrabajo().getPeso()+"%", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaTrabajo().getPromedioParcial(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				
							
				table.addCell(subTable);
			}
			//FIN Trabajo
			
			//Grupal
			
			if(aula.getCantidadGrupales()>0){
				float[] widthsVertical = {0.46f,0.1f,0.1f,0.1f,0.08f,0.08f,0.08f};
				subTable = new PdfPTable(widthsVertical);
				subTable.setWidthPercentage(100);
				subTable.getDefaultCell().setPadding(3);
				subTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				subTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				subTable.getDefaultCell().setBorderColor(new Color(0x7E, 0xAA, 0xD1));
				subTable.getDefaultCell().setBorderWidth(1);
				subTable.getDefaultCell().setMinimumHeight(16);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Trabajo Grupal", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Debates", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Nota", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Estado", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Pr. Pa.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Peso", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("Pr. Ac.", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				
				int i = 1;
				for (MatriculaGTrabajoGrupal job : reporte.getRecursoTrabajoGrupal()) {
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase("Trabajo N°"+i, FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.LEFT);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(job.getIntervencion()+"/"+job.getIntervencionTotal(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(job.getNota(), FontFactory.getFont(FontFactory.HELVETICA, 7)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase(job.getEstadoString(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(i%2==0)
						cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
					subTable.addCell(cell);
					
					cell = new  PdfPCell(subTable.getDefaultCell());
					cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
					cell.setBorder(12);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
					subTable.addCell(cell);
					i++;
				}
				//**
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7)));
				cell.setBorder(Rectangle.BOX);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaGrupal().getNota(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaGrupal().getPeso()+"%", FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
				subTable.addCell(cell);
				
				cell = new  PdfPCell(subTable.getDefaultCell());
				cell.setPhrase(new Phrase(reporte.getNotaGrupal().getPromedioParcial(), FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
				cell.setBorder(Rectangle.BOX);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xF8, 0xF8, 0xF8));
				subTable.addCell(cell);
				
							
				table.addCell(subTable);
			}
			//FIN Grupal
			
			//Promedio Final
			
			float[] widthsVertical = {0.70f,0.14f,0.16f};
			subTable = new PdfPTable(widthsVertical);
			subTable.setWidthPercentage(100);
			subTable.getDefaultCell().setPadding(3);
			subTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			subTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			subTable.getDefaultCell().setBorderColor(new Color(0x7E, 0xAA, 0xD1));
			subTable.getDefaultCell().setBorderWidth(1);
			subTable.getDefaultCell().setMinimumHeight(16);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(""));
			cell.setBorder(5);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("Nota Final:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(5);
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(reporte.getNotaFinal(), FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(13);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(""));
			cell.setBorder(6);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("Estado Final:", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(6);
			cell.setBackgroundColor(new Color(0xE5, 0xEF, 0xF8));
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase((Float.parseFloat(reporte.getNotaFinal())>=10.5)?"Apto":"No Apto", FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD,(Float.parseFloat(reporte.getNotaFinal())>=10.5)?new Color(0x00, 0x00, 0xFF):new Color(0xFF, 0x00, 0x00))));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(14);
			subTable.addCell(cell);
			
			table.addCell(subTable);
			
			//Fin Promedio Final
			
			//Pie
			
			float[] widthsPie = {0.3f,0.4f,0.3f};
			subTable = new PdfPTable(widthsPie);
			subTable.setWidthPercentage(100);
			subTable.getDefaultCell().setPadding(3);
			subTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			subTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("opencampus Virtual", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(""));
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("www.opencampus.edu.pe", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("T: (51-1) 317-3900 anexo 4005", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase(""));
			subTable.addCell(cell);
			
			cell = new  PdfPCell(subTable.getDefaultCell());
			cell.setPhrase(new Phrase("soporte@opencampus.edu.pe", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			subTable.addCell(cell);
			
			table.addCell(subTable);
			
			//Fin PIE
				
			doc.add(table);
			
			doc.newPage();
		}
	}

}
