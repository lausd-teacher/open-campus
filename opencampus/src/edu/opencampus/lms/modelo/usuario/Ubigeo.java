package edu.opencampus.lms.modelo.usuario;

import java.io.Serializable;

public class Ubigeo implements Serializable {

	private static final long serialVersionUID = -347117575035507519L;

	private String idDepartamento;
	
	private String departamento;

	private String idDistrito;
	
	private String distrito;

	private String idPais;
	
	private String pais;

	private String idProvincia;
	
	private String provincia;

	private String nombreCompleto;

	public String getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(String idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(String idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getNombreCompleto() {
		setNombreCompleto(this.pais+" - "+this.departamento+" - "+this.provincia+" - "+this.distrito);
		return nombreCompleto;
	}

	private void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Ubigeo(String codigo, String provincia, String distrito,
			String departamento, String pais) {
		super();
		this.departamento = departamento;
		this.distrito = distrito;
		this.pais = pais;
		this.provincia = provincia;

	}

	public Ubigeo() {
		super();
	}

}
