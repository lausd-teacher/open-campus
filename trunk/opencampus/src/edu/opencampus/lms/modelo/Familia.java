package edu.opencampus.lms.modelo;

public class Familia extends BaseModelo {

	private static final long serialVersionUID = -6503978876310434902L;

	private int codigo;

	private String nombreCompleto;

	private String nombreCorto;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public Familia(int codigo, String nombreCompleto, String nombreCorto) {
		super();
		this.codigo = codigo;
		this.nombreCompleto = nombreCompleto;
		this.nombreCorto = nombreCorto;
	}

}
