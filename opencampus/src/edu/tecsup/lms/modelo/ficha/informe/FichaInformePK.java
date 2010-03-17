package edu.tecsup.lms.modelo.ficha.informe;

import java.io.Serializable;

public class FichaInformePK implements Serializable {

	private static final long serialVersionUID = 1088051896294296647L;
	private Integer codcurso;
	private Integer codperiodo;
	
	public FichaInformePK() {
    }

    public FichaInformePK(int codcurso, int codperiodo) {
        this.codcurso = codcurso;
        this.codperiodo = codperiodo;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof FichaInformePK) {
            final FichaInformePK otherFichaInformePK = (FichaInformePK) other;
            final boolean areEqual = 
                (otherFichaInformePK.codcurso.equals(codcurso) && otherFichaInformePK.codperiodo.equals(codperiodo));
            return areEqual;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
	
	public Integer getCodcurso() {
		return codcurso;
	}
	public void setCodcurso(Integer codcurso) {
		this.codcurso = codcurso;
	}
	
	public Integer getCodperiodo() {
		return codperiodo;
	}
	public void setCodperiodo(Integer codperiodo) {
		this.codperiodo = codperiodo;
	}
	
}
