
function abrirDetalle(context, id) {
	document.location = context + "/aulavirtual/ReporteDetalle.action?idMatricula=" + id + "&tipo=" + id;
	
}

function abrirAuditoria(context, id) {
	document.location = context + "/aulavirtual/Auditoria.action?idMatricula=" + id + "&tipo=" + id;
	
}

function seleccionRegistro(Input, valor) {
	if (valor) {
		Input.style.background = "#FFFBE8";
		Input.style.borderColor = "#EFDA45";
	} else {
		Input.style.background = "#FFFFFF";
		Input.style.borderColor = "#AAABAF";
	}
}
