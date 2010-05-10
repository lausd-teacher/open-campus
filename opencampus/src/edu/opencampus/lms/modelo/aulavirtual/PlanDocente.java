package edu.opencampus.lms.modelo.aulavirtual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.opencampus.lms.modelo.CursoResultado;
import edu.opencampus.lms.modelo.ficha.FichaRecurso;
import edu.opencampus.lms.modelo.ficha.Recurso;
import edu.opencampus.lms.util.Constante;

public class PlanDocente {

	private String objetivo;

	private String introduccion;

	private String bibliografia;
	
	private String metodologia;
	
	private String tipoEvaluacion;
	
	private String objetivoGeneral;

	private Collection<String> unidades = new ArrayList<String>();

	private Map<String, Recurso> pesos = new HashMap<String, Recurso>(Constante.RECURSOS_TOTAL);
	
	private Collection<FichaRecurso> fichasRecursos =  new ArrayList<FichaRecurso>();
	
	private Collection<CursoResultado> cursoResultados = new ArrayList<CursoResultado>();

	public PlanDocente() {
		super();
	}

	public String getMetodologia() {
		return metodologia;
	}

	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}

	public String getObjetivoGeneral() {
		return objetivoGeneral;
	}

	public void setObjetivoGeneral(String objetivoGeneral) {
		this.objetivoGeneral = objetivoGeneral;
	}

	public String getTipoEvaluacion() {
		return tipoEvaluacion;
	}

	public void setTipoEvaluacion(String tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}

	public String getIntroduccion() {
		return introduccion;
	}

	public void setIntroduccion(String introduccion) {
		this.introduccion = introduccion;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public Collection<String> getUnidades() {
		return unidades;
	}

	public void setUnidades(Collection<String> unidades) {
		this.unidades = unidades;
	}

	public String getBibliografia() {
		return bibliografia;
	}

	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}

	public Map<String, Recurso> getPesos() {
		return pesos;
	}

	public void setPesos(Map<String, Recurso> pesos) {
		this.pesos = pesos;
	}

	public Collection<FichaRecurso> getFichasRecursos() {
		return fichasRecursos;
	}

	public void setFichasRecursos(Collection<FichaRecurso> fichasRecursos) {
		this.fichasRecursos = fichasRecursos;
	}

	public Collection<CursoResultado> getCursoResultados() {
		return cursoResultados;
	}

	public void setCursoResultados(Collection<CursoResultado> cursoResultados) {
		this.cursoResultados = cursoResultados;
	}

}
