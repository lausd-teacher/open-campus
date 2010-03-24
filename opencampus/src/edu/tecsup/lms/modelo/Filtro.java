package edu.tecsup.lms.modelo;

import java.io.Serializable;

public abstract class Filtro implements Serializable{

	private static final long serialVersionUID = 8603617840786783366L;

	private Integer total;

	private Integer paginas;
	
	private Integer pagina;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPaginas() {
		return paginas;
	}

	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	
}
