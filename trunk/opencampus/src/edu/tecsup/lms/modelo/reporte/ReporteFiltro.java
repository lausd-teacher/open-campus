package edu.tecsup.lms.modelo.reporte;

import java.util.Collection;

import edu.tecsup.lms.modelo.Sede;

public class ReporteFiltro {

	private String busquedaGrupo;

	private String busquedaUsuarioNombre;

	private String busquedaUsuario;

	private String busquedaEmpresa;

	private String busquedaContacto;

	private String busquedaCurso;

	private String busquedaExacta;

	private String busquedaFormacion;

	private String busquedaSede;

	private String busquedaPeriodo;

	private String busquedaFecha1;

	private String busquedaFecha2;

	private String busquedaCiclo;

	private String posicionPagina;

	private String totalRegistro;

	private String cantidadRegistro;

	private String busquedaDepartamento;

	private String registroPrimero;

	private String registroUltimo;

	private String busquedaIngreso;
	
	private Collection<Sede> permisos;

	public ReporteFiltro() {
		super();
	}

	public Collection<Sede> getPermisos() {
		return permisos;
	}

	public void setPermisos(Collection<Sede> permisos) {
		this.permisos = permisos;
	}

	public String getBusquedaIngreso() {
		return busquedaIngreso;
	}

	public void setBusquedaIngreso(String busquedaIngreso) {
		this.busquedaIngreso = busquedaIngreso;
	}

	public String getRegistroPrimero() {
		return registroPrimero;
	}

	public String getBusquedaGrupo() {
		return busquedaGrupo;
	}

	public void setBusquedaGrupo(String busquedaGrupo) {
		this.busquedaGrupo = busquedaGrupo;
	}

	public String getBusquedaUsuarioNombre() {
		return busquedaUsuarioNombre;
	}

	public void setBusquedaUsuarioNombre(String busquedaUsuarioNombre) {
		this.busquedaUsuarioNombre = busquedaUsuarioNombre;
	}

	public void setRegistroPrimero(String registroPrimero) {
		this.registroPrimero = registroPrimero;
	}

	public String getRegistroUltimo() {
		return registroUltimo;
	}

	public void setRegistroUltimo(String registroUltimo) {
		this.registroUltimo = registroUltimo;
	}

	public String getBusquedaCiclo() {
		return busquedaCiclo;
	}

	public void setBusquedaCiclo(String busquedaCiclo) {
		this.busquedaCiclo = busquedaCiclo;
	}

	public String getBusquedaContacto() {
		return busquedaContacto;
	}

	public void setBusquedaContacto(String busquedaContacto) {
		this.busquedaContacto = busquedaContacto;
	}

	public String getBusquedaCurso() {
		return busquedaCurso;
	}

	public void setBusquedaCurso(String busquedaCurso) {
		this.busquedaCurso = busquedaCurso;
	}

	public String getBusquedaDepartamento() {
		return busquedaDepartamento;
	}

	public void setBusquedaDepartamento(String busquedaDepartamento) {
		this.busquedaDepartamento = busquedaDepartamento;
	}

	public String getBusquedaEmpresa() {
		return busquedaEmpresa;
	}

	public void setBusquedaEmpresa(String busquedaEmpresa) {
		this.busquedaEmpresa = busquedaEmpresa;
	}

	public String getBusquedaExacta() {
		return busquedaExacta;
	}

	public void setBusquedaExacta(String busquedaExacta) {
		this.busquedaExacta = busquedaExacta;
	}

	public String getBusquedaFecha1() {
		return busquedaFecha1;
	}

	public void setBusquedaFecha1(String busquedaFecha1) {
		this.busquedaFecha1 = busquedaFecha1;
	}

	public String getBusquedaFecha2() {
		return busquedaFecha2;
	}

	public void setBusquedaFecha2(String busquedaFecha2) {
		this.busquedaFecha2 = busquedaFecha2;
	}

	public String getBusquedaFormacion() {
		return busquedaFormacion;
	}

	public void setBusquedaFormacion(String busquedaFormacion) {
		this.busquedaFormacion = busquedaFormacion;
	}

	public String getBusquedaPeriodo() {
		return busquedaPeriodo;
	}

	public void setBusquedaPeriodo(String busquedaPeriodo) {
		this.busquedaPeriodo = busquedaPeriodo;
	}

	public String getBusquedaSede() {
		return busquedaSede;
	}

	public void setBusquedaSede(String busquedaSede) {
		this.busquedaSede = busquedaSede;
	}

	public String getBusquedaUsuario() {
		return busquedaUsuario;
	}

	public void setBusquedaUsuario(String busquedaUsuario) {
		this.busquedaUsuario = busquedaUsuario;
	}

	public String getCantidadRegistro() {
		return cantidadRegistro;
	}

	public void setCantidadRegistro(String cantidadRegistro) {
		this.cantidadRegistro = cantidadRegistro;
	}

	public String getPosicionPagina() {
		return posicionPagina;
	}

	public void setPosicionPagina(String posicionPagina) {
		this.posicionPagina = posicionPagina;
	}

	public String getTotalRegistro() {
		return totalRegistro;
	}

	public void setTotalRegistro(String totalRegistro) {
		this.totalRegistro = totalRegistro;
	}

}
