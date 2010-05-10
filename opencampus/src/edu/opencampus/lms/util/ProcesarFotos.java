package edu.opencampus.lms.util;

import java.io.File;

public class ProcesarFotos {
	// Crear fotos
	public static void main (String[] name) {
		File d = new File("c:\\fotos");
		File e = new File("c:\\fotos_final");
		String[] x = d.list();
		for (int i = 0; i < x.length; i++) {
			if (x[i].contains("_")) {
				int u = x[i].indexOf("_");
				String primer = x[i].substring(u+1, u+2);
				String segundo = x[i].substring(0, u);

				String idusuario = primer+segundo;
				File w = new File(d, x[i]);
				w.renameTo(new File(e,idusuario+".jpg"));
//				System.out.println(idusuario);
				//break;
			}
		}
	}
}