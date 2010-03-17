package edu.tecsup.lms.modelo.aulavirtual;

import java.util.Comparator;

import edu.tecsup.lms.modelo.AulaVirtual;

public class OraganizationComparator implements Comparator<AulaVirtual> {

	@Override
	public int compare(AulaVirtual a1, AulaVirtual a2) {
		
		return a1.getCurso().getJerarquia().getPatriarca().getNombre().compareToIgnoreCase(a2.getCurso().getJerarquia().getPatriarca().getNombre());
		
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

}