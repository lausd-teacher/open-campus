package edu.tecsup.lms.action.tindividual;

import java.io.File;

import edu.tecsup.lms.action.BaseAction;

public class SubirTrabajo extends BaseAction {

	private static final long serialVersionUID = -919199951320501235L;

	public File file;

	public String contentType;

	public String filename;

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setFileFileName(String filename) {
		this.filename = filename;
	}

	public String upload() throws Exception {
		log.info("upload()");
		
		// Guardar Archivo

		getRequest().setAttribute("FILENAME", filename);
		getRequest().setAttribute("FILESIZE", file.length());

		return SUCCESS;

	}

	public String cargar() {
		return SUCCESS;
	}

}
