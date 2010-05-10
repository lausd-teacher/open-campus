package edu.opencampus.lms.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.opencampus.lms.modelo.ficha.Unidad;

public class Silabo extends BaseModelo {

	private static final long serialVersionUID = 5636829738606803576L;

	private Integer idSilabo;

	private String descripcion;
	
	private String introduccion;

	private Integer idCurso;
	
	private Integer flagLaboratoriosTotal;
	
	private Integer flagDialogosTotal;
	
	private Integer flagTIndividualTotal;
	
	private Integer flagTGrupalTotal;
	
	private Integer flagDebatesTotal;
	
	private Integer flagTestTotal;
	
	private boolean texto;
	
	private boolean repaso;
	
	private boolean laboratorio;
	
	private boolean dialogo;
	
	private boolean trabajoIndividual;
	
	private boolean trabajoGrupal;
	
	private boolean test;
	
	private Collection<AulaVirtual> aulas = new ArrayList<AulaVirtual>();

	private List<Unidad> unidades = new ArrayList<Unidad>();
	
	private Curso curso;
	
	public Silabo() {
	}
	
	public boolean isTexto(){
		for (Unidad unidad : unidades) {
			if(unidad.hasTexto())texto = true;
		}
		return texto;
	}
	
	public boolean isRepaso(){
		for (Unidad unidad : unidades) {
			if(unidad.hasRepaso())repaso = true;
		}
		return repaso;
	}
	
	public boolean isLaboratorio(){
		for (Unidad unidad : unidades) {
			if(unidad.hasLaboratorio())laboratorio = true;
		}
		return laboratorio;
	}
	
	public boolean isDialogo(){
		for (Unidad unidad : unidades) {
			if(unidad.hasDialogo())dialogo = true;
		}
		return dialogo;
	}
	
	public boolean isTrabajoIndividual(){
		for (Unidad unidad : unidades) {
			if(unidad.hasTrabajoIndividual())trabajoIndividual = true;
		}
		return trabajoIndividual;
	}
	
	public boolean isTrabajoGrupal(){
		for (Unidad unidad : unidades) {
			if(unidad.hasTrabajoGrupal())trabajoGrupal = true;
		}
		return trabajoGrupal;
	}
	
	public boolean isTest(){
		for (Unidad unidad : unidades) {
			if(unidad.hasTest())test = true;
		}
		return test;
	}
	
	public Integer getFlagLaboratoriosTotal(){
		flagLaboratoriosTotal = 0;
		for (Unidad unidad : unidades) {
			flagLaboratoriosTotal += unidad.getFlagLaboratorios();
		}
		return flagLaboratoriosTotal;
	}
	
	public Integer getFlagDialogosTotal(){
		flagDialogosTotal = 0;
		for (Unidad unidad : unidades) {
			flagDialogosTotal += unidad.getFlagDialogos();
		}
		return flagDialogosTotal;
	}
	
	public Integer getFlagTIndividualTotal(){
		flagTIndividualTotal = 0;
		for (Unidad unidad : unidades) {
			flagTIndividualTotal += unidad.getFlagTIndividual();
		}
		return flagTIndividualTotal;
	}
	
	public Integer getFlagTGrupalTotal(){
		flagTGrupalTotal = 0;
		for (Unidad unidad : unidades) {
			flagTGrupalTotal += unidad.getFlagTGrupal();
		}
		return flagTGrupalTotal;
	}
	
	public Integer getFlagDebatesTotal(){
		flagDebatesTotal = 0;
		for (Unidad unidad : unidades) {
			flagDebatesTotal += unidad.getFlagDebates();
		}
		return flagDebatesTotal;
	}
	
	public Integer getFlagTestTotal(){
		flagTestTotal = 0;
		for (Unidad unidad : unidades) {
			flagTestTotal += unidad.getFlagTest();
		}
		return flagTestTotal;
	}
	
	public String getIntroduccion() {
		return introduccion;
	}

	public void setIntroduccion(String introduccion) {
		this.introduccion = introduccion;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}

	public Silabo(Integer idSilabo) {
		this.idSilabo = idSilabo;
	}
	
	public Collection<AulaVirtual> getAulas() {
		return aulas;
	}

	public void setAulas(Collection<AulaVirtual> aulas) {
		this.aulas = aulas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Integer getIdSilabo() {
		return idSilabo;
	}

	public void setIdSilabo(Integer idSilabo) {
		this.idSilabo = idSilabo;
	}

	public Integer getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean esMiUnidad(Integer idUnidad) {
		if (unidades != null && idUnidad != null) {
			for (Unidad uni : unidades) {
				if (uni.getIdUnidad().equals(idUnidad)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getNombreUnidad(Integer idUnidad) {
		if (unidades != null && idUnidad != null) {
			for (Unidad uni : unidades) {
				if (uni.getIdUnidad().equals(idUnidad)) {
					return uni.getNombreCompleto();
				}
			}
		}
		return null;
	}

	public String toString() {
		StringBuffer ret = new StringBuffer( "ID:" + idSilabo + " Descripcion:" + descripcion
				+ " idCurso:" + idCurso);
		ret.append("Unidades: ");
		for (Unidad u : unidades) {
			ret.append("\n\t"+u);
		}
		return ret.toString();
	}


}
