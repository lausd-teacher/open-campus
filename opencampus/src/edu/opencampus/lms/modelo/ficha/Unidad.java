package edu.opencampus.lms.modelo.ficha;

import java.util.ArrayList;
import java.util.List;

import edu.opencampus.lms.modelo.BaseModelo;
import edu.opencampus.lms.modelo.Jerarquia;
import edu.opencampus.lms.util.Constante;

public class Unidad extends BaseModelo {

	private static final long serialVersionUID = -1968314847106561478L;

	private Integer idUnidad;

	private String nombreCompleto;

	private Jerarquia jerarquia;

	private int flagLaboratorios;

	private int flagDialogos;

	private int flagTIndividual;

	private int flagTGrupal;
	
	private int flagDebates;

	private int flagTest;
	
	private int cantidadTest;
	
	private int indice;

	private int estado;

	private List<Recurso> recursos = new ArrayList<Recurso>();

	public boolean hasTexto(){
		int index = recursos.indexOf(new Recurso(Constante.RECURSO_ID_TEXTO));
		if(index != -1)
			//Si existe Texto compruebo su estado 
			if(recursos.get(index).getEstado() == Constante.ESTADO_ACTIVO) return true;
		return false;
	}
	
	public boolean hasRepaso(){
		int index = recursos.indexOf(new Recurso(Constante.RECURSO_ID_REPASO));
		if(index != -1)
			if(recursos.get(index).getEstado() == Constante.ESTADO_ACTIVO) return true;
		return false;
	}
	
	public boolean hasLaboratorio(){
		int index = recursos.indexOf(new Recurso(Constante.RECURSO_ID_LABORATORIO));
		if(index != -1)
			if(recursos.get(index).getEstado() == Constante.ESTADO_ACTIVO) return true;
		return false;
	}
	
	public boolean hasDialogo(){
		int index = recursos.indexOf(new Recurso(Constante.RECURSO_ID_DIALOGO));
		if(index != -1)
			if(recursos.get(index).getEstado() == Constante.ESTADO_ACTIVO) return true;
		return false;
	}
	
	public boolean hasTrabajoIndividual(){
		int index = recursos.indexOf(new Recurso(Constante.RECURSO_ID_TRABAJO_INDIVIDUAL));
		if(index != -1)
			if(recursos.get(index).getEstado() == Constante.ESTADO_ACTIVO) return true;
		return false;
	}
	
	public boolean hasTrabajoGrupal(){
		int index = recursos.indexOf(new Recurso(Constante.RECURSO_ID_TRABAJO_GRUPAL));
		if(index != -1)
			if(recursos.get(index).getEstado() == Constante.ESTADO_ACTIVO) return true;
		return false;
	}
	
	public boolean hasTest(){
		int index = recursos.indexOf(new Recurso(Constante.RECURSO_ID_TEST));
		if(index != -1)
			if(recursos.get(index).getEstado() == Constante.ESTADO_ACTIVO) return true;
		return false;
	}
	
	public int getCantidadTest() {
		return cantidadTest;
	}

	public void setCantidadTest(int cantidadTest) {
		this.cantidadTest = cantidadTest;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public int getEstado() {
		return estado;
	}

	public int getFlagLaboratorios() {
		return flagLaboratorios;
	}

	public void setFlagLaboratorios(int flagLaboratorios) {
		this.flagLaboratorios = flagLaboratorios;
	}

	public int getFlagDialogos() {
		return flagDialogos;
	}

	public void setFlagDialogos(int flagDialogos) {
		this.flagDialogos = flagDialogos;
	}

	public int getFlagTIndividual() {
		return flagTIndividual;
	}

	public void setFlagTIndividual(int flagTIndividual) {
		this.flagTIndividual = flagTIndividual;
	}

	public int getFlagTGrupal() {
		return flagTGrupal;
	}

	public void setFlagTGrupal(int flagTGrupal) {
		this.flagTGrupal = flagTGrupal;
	}

	public int getFlagDebates() {
		return flagDebates;
	}

	public void setFlagDebates(int flagDebates) {
		this.flagDebates = flagDebates;
	}

	public int getFlagTest() {
		return flagTest;
	}

	public void setFlagTest(int flagTest) {
		this.flagTest = flagTest;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recurso) {
		this.recursos = recurso;
	}

	public Jerarquia getJerarquia() {
		return jerarquia;
	}

	public void setJerarquia(Jerarquia jerarquia) {
		this.jerarquia = jerarquia;
	}

	public Unidad() {
		super();
	}

	@Override
	public String toString() {
		return "idUnidad: "+this.idUnidad+" nombreCompleto: "+this.nombreCompleto;
	}
	
	
}
