function abrirReporte(context, id) {
	var pop_reporte;
	var ancho = screen.availWidth - 100;
	var alto = screen.availHeight - 200;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	pop_reporte = window.open(context + "/aulavirtual/ReporteDetalle.action?idMatricula=" + id , "AulaVirtualReporte", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	pop_reporte.focus();
	return true;
}
function abrirAuditoria(context, id) {
	var pop_reporte;
	var ancho = screen.availWidth - 100;
	var alto = screen.availHeight - 200;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	pop_reporte = window.open(context + "/aulavirtual/Auditoria.action?idMatricula=" + id , "AulaVirtualReporte", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	pop_reporte.focus();
	return true;
}
