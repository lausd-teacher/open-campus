package edu.opencampus.lms.modelo.usuario;

import java.util.ArrayList;
import java.util.Collection;
@Deprecated
public class UsuarioPermiso {

	public class SedeAlgo {

		public static final int TIPO_DEPARTAMENTO = 1;

		public static final int TIPO_FORMACION = 2;

		private String id;

		private String nombre;

		private String sede;

		private int tipo;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSede() {
			return sede;
		}

		public void setSede(String sede) {
			this.sede = sede;
		}

		public int getTipo() {
			return tipo;
		}

		public void setTipo(int tipo) {
			this.tipo = tipo;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public SedeAlgo(String id, String sede, String nombre, int tipo) {
			super();
			this.id = id;
			this.sede = sede;
			this.tipo = tipo;
			this.nombre = nombre;
		}
		
		public SedeAlgo() {
			super();
		}

	}

	private String idUsuario;

	private Collection<SedeAlgo> sedeAlgo = new ArrayList<SedeAlgo>();

	private String nombreCompleto;

	public UsuarioPermiso() {
		super();
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Collection<SedeAlgo> getSedeAlgo() {
		return sedeAlgo;
	}

	public void setSedeAlgo(Collection<SedeAlgo> sedeAlgo) {
		this.sedeAlgo = sedeAlgo;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

}
