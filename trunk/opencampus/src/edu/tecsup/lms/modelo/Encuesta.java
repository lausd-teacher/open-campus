package edu.tecsup.lms.modelo;

import java.util.GregorianCalendar;
@Deprecated 
public class Encuesta extends BaseModelo {

	private static final long serialVersionUID = 6007597970136723353L;
	
	private int idMatricula;
	
	private int p1A;
	
	private int p1B;
	
	private int p2A;
	
	private int p2B;
	
	private int p3A;
	
	private int p3B;
	
	private int p3C;
	
	private int p4A;
	
	private int p5A;
	
	private int p6A;
	
	private int p6B;
	
	private int p6C;
	
	private String p7A;
	
	private GregorianCalendar fecha;
	
	public int getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(int idMatricula) {
		this.idMatricula = idMatricula;
	}

	public int getP1A() {
		return p1A;
	}

	public void setP1A(int p1a) {
		p1A = p1a;
	}

	public int getP1B() {
		return p1B;
	}

	public void setP1B(int p1b) {
		p1B = p1b;
	}

	public int getP2A() {
		return p2A;
	}

	public void setP2A(int p2a) {
		p2A = p2a;
	}

	public int getP2B() {
		return p2B;
	}

	public void setP2B(int p2b) {
		p2B = p2b;
	}

	public int getP3A() {
		return p3A;
	}

	public void setP3A(int p3a) {
		p3A = p3a;
	}

	public int getP3B() {
		return p3B;
	}

	public void setP3B(int p3b) {
		p3B = p3b;
	}

	public int getP3C() {
		return p3C;
	}

	public void setP3C(int p3c) {
		p3C = p3c;
	}

	public int getP4A() {
		return p4A;
	}

	public void setP4A(int p4a) {
		p4A = p4a;
	}

	public int getP5A() {
		return p5A;
	}

	public void setP5A(int p5a) {
		p5A = p5a;
	}

	public int getP6A() {
		return p6A;
	}

	public void setP6A(int p6a) {
		p6A = p6a;
	}

	public int getP6B() {
		return p6B;
	}

	public void setP6B(int p6b) {
		p6B = p6b;
	}

	public int getP6C() {
		return p6C;
	}

	public void setP6C(int p6c) {
		p6C = p6c;
	}

	public GregorianCalendar getFecha() {
		return fecha;
	}

	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	public String getP7A() {
		return p7A;
	}

	public void setP7A(String p7a) {
		p7A = p7a;
	}
	

}
