package edu.tecsup.lms.action.encuesta;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.Encuesta;
import edu.tecsup.lms.service.EncuestaService;
import edu.tecsup.lms.util.Constante;
@Deprecated 
public class EncuestaAction extends BaseAction {

	private static final long serialVersionUID = 1923396950597321165L;
	
	private EncuestaService encuestaService;

	public void setEncuestaService(EncuestaService encuestaService) {
		this.encuestaService = encuestaService;
	}
	
	public String p1_p1;
	public String p1_p2;
	public String p2_p1;
	public String p2_p2;
	public String p3_p1;
	public String p3_p2;
	public String p3_p3;
	public String p4_p1;
	public String p5_p1;
	public String p6_p1;
	public String p6_p2;
	public String p6_p3;
	public String p7_p1;
	
	public void setP1_p1(String p1_p1) {
		this.p1_p1 = p1_p1;
	}

	public void setP1_p2(String p1_p2) {
		this.p1_p2 = p1_p2;
	}

	public void setP2_p1(String p2_p1) {
		this.p2_p1 = p2_p1;
	}

	public void setP2_p2(String p2_p2) {
		this.p2_p2 = p2_p2;
	}

	public void setP3_p1(String p3_p1) {
		this.p3_p1 = p3_p1;
	}

	public void setP3_p2(String p3_p2) {
		this.p3_p2 = p3_p2;
	}

	public void setP3_p3(String p3_p3) {
		this.p3_p3 = p3_p3;
	}

	public void setP4_p1(String p4_p1) {
		this.p4_p1 = p4_p1;
	}

	public void setP5_p1(String p5_p1) {
		this.p5_p1 = p5_p1;
	}

	public void setP6_p1(String p6_p1) {
		this.p6_p1 = p6_p1;
	}

	public void setP6_p2(String p6_p2) {
		this.p6_p2 = p6_p2;
	}

	public void setP6_p3(String p6_p3) {
		this.p6_p3 = p6_p3;
	}
	
	public void setP7_p1(String p7_p1) {
		this.p7_p1 = p7_p1;
	}
	
	public String idMatricula;
		
	public String getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(String idMatricula) {
		this.idMatricula = idMatricula;
	}

	public String guardar() {
		log.info("guardar()");
		try {

			AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
			
			if(aula != null && validarItems()){
				log.info("validarItems() - id="+aula.getIdMatricula());
				Encuesta encuesta = new Encuesta();
				
				encuesta.setIdMatricula(aula.getIdMatricula());
				encuesta.setP1A(Integer.parseInt(p1_p1));
				encuesta.setP1B(Integer.parseInt(p1_p2));
				encuesta.setP2A(Integer.parseInt(p2_p1));
				encuesta.setP2B(Integer.parseInt(p2_p2));
				encuesta.setP3A(Integer.parseInt(p3_p1));
				encuesta.setP3B(Integer.parseInt(p3_p2));
				encuesta.setP3C(Integer.parseInt(p3_p3));
				encuesta.setP4A(Integer.parseInt(p4_p1));
				encuesta.setP5A(Integer.parseInt(p5_p1));
				encuesta.setP6A(Integer.parseInt(p6_p1));
				encuesta.setP6B(Integer.parseInt(p6_p2));
				encuesta.setP6C(Integer.parseInt(p6_p3));
				encuesta.setP7A(p7_p1);
				
				encuestaService.insertarEncuesta(encuesta);
				
				setIdMatricula(String.valueOf(aula.getIdFicha()));
			}
						
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e){
			log.error(e.toString());
		} catch (Exception e){
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public boolean validarItems() {
		if(p1_p1 == null && p1_p1.trim().length()>0 && p2_p1 == null && p2_p1.trim().length()>0 && 
				p2_p2 == null && p2_p2.trim().length()>0 && p3_p1 == null && p3_p1.trim().length()>0 && 
				p3_p2 == null && p3_p2.trim().length()>0 && p3_p3 == null && p3_p3.trim().length()>0 && 
				p4_p1 == null && p4_p1.trim().length()>0 && p5_p1 == null && p5_p1.trim().length()>0 && 
				p6_p1 == null && p6_p1.trim().length()>0 && p6_p2 == null && p6_p2.trim().length()>0 && 
				p6_p3 == null && p6_p3.trim().length()>0 && p1_p2 == null && p1_p2.trim().length()>0){
			return false;
		}
		if(p7_p1 != null && p7_p1.trim().length()>1024)p7_p1 = p7_p1.substring(0, 1024);
		return true;
	}
	
}
