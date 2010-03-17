
var session_temp = "";
var session_flag = false;
var session_impresora = 0;
$(document).ready(function () {
	/**ToolTip de la anotaciones*/
	$("s_tooltip").Tooltip({delay:0, track:true});
	/**TextArea puedan cambiar su tamaño dinamicamente*/
	$("textarea.anotacion_comentario_editar").autogrow({lineHeight:16, minHeight:20});
	$("textarea.anotacion_titulo_editar").autogrow({lineHeight:16, minHeight:20});
	$("textarea.anotacion_titulo_nuevo").autogrow({lineHeight:16, minHeight:20});
	
	if ($.browser.msie === true) {
		/**Modifique el tamaño la anotacion IE*/
		$("div.anotacion_primero").resize(function () {
			$("#anotacion_borde_azul_" + $(this).attr("lang")).height($(this).height());
		});
		/**Mensajes de error IE*/
		$("#divError").css("position", "absolute");
		$(".anotacion_nuevo_eliminar").css("display", "block");
	} else {
		$(".anotacion_pre_principal").hover(function () {
			$(".anotacion_nuevo_eliminar").fadeIn();
		}, function () {
			$(".anotacion_nuevo_eliminar").fadeOut();
		});
	}
	/**Editar algun comentario */
	$("textarea.anotacion_comentario_editar").blur(function () {
		var contenido = $(this).val();
		contenido = $.trim(contenido);
		if (contenido.length === 0 && session_temp.length === 0 && session_flag) {
			$("#anotacion_comentario_" + $(this).attr("lang")).fadeOut();
			$("#anotacion_opcion_" + $(this).attr("lang")).removeClass("div_anotacion_opciones_selecionado");
		}
	});
	/**Guarda la data inicial*/
	$("textarea.anotacion_comentario_editar").focus(function () {
		if (!session_flag) {
			session_temp = $(this).val();
			session_flag = true;
		}
	});
	/**Guarda la data inicial*/
	$("textarea.anotacion_titulo_editar").focus(function () {
		if (!session_flag) {
			session_temp = $(this).val();
			session_flag = true;
		}
	});
	$("textarea.anotacion_comentario_editar").change(function () {
		var contenido = $(this).val();
		contenido = $.trim(contenido);
		var isreducido = false;
		if (contenido.length > 3000) {
			isreducido = true;
			$(this).val(contenido.substr(0, 3000));
		}
		session_flag = false;
		if (contenido.length === 0) {
			if ($("#anotacion_titulo_textarea_" + $(this).attr("lang")).val().length === 0) {
				$(this).val(session_temp);
				resizeAnotacion();
				session_temp = "";
				return;
			}
			$("#anotacion_comentario_" + $(this).attr("lang")).fadeOut();
			$("#anotacion_opcion_" + $(this).attr("lang")).removeClass("div_anotacion_opciones_selecionado");
		}
		session_temp = "";
		resizeAnotacion();
		$.ajax({type:"POST", url:request_context + "/anotacion/Actualizar.action", data:"id=" + $(this).attr("lang") + "&contenido=" + escape($(this).val()), success:function (msg) {
			var mensajeFinal = "";
			if (msg == "0") {
				mensajeFinal = nota_mensaje_txt_error;
				if (isreducido === true) {
					mensajeFinal += nota_mensaje_txt_modificado_error;
				}
				mensaje_error(mensajeFinal, 1);
			} else {
				mensajeFinal = nota_mensaje_txt_modificado;
				if (isreducido === true) {
					mensajeFinal += nota_mensaje_txt_modificado_error;
				}
				mensaje_error(mensajeFinal);
			}
		}, error:function () {
			mensaje_error(nota_mensaje_txt_error, 1);
		}});
	});
	/**Editar algun titulo */
	$("textarea.anotacion_titulo_editar").change(function () {
		var contenido = $(this).val();
		contenido = $.trim(contenido);
		var isreducido = false;
		if (contenido.length > 200) {
			isreducido = true;
			$(this).val(contenido.substr(0, 200));
		}
		session_flag = false;
		if (contenido.length === 0 && $("#anotacion_comentario_textarea_" + $(this).attr("lang")).val().length === 0) {
			$(this).val(session_temp);
			resizeAnotacion();
			session_temp = "";
			return;
		}
		session_temp = "";
		resizeAnotacion();
		$.ajax({type:"POST", url:request_context + "/anotacion/Actualizar.action", data:"id=" + $(this).attr("lang") + "&titulo=" + escape($(this).val()), success:function (msg) {
			var mensajeFinal;
			if (msg == "0") {
				mensajeFinal = nota_mensaje_txt_error;
				if (isreducido === true) {
					mensajeFinal += nota_mensaje_txt_modificado_error;
				}
				mensaje_error(mensajeFinal, 1);
			} else {
				mensajeFinal = nota_mensaje_txt_modificado;
				if (isreducido === true) {
					mensajeFinal += nota_mensaje_txt_modificado_error;
				}
				mensaje_error(mensajeFinal);
			}
		}, error:function () {
			mensaje_error(nota_mensaje_txt_error, 1);
		}});
	});
	resizeAnotacion();
});
/**Click a la opcion Comentario de cada anotacion*/
function comentario_ver_anotacion(s_this) {
	$(s_this).addClass("div_anotacion_opciones_selecionado");
	$("#anotacion_comentario_" + $(s_this).attr("lang")).fadeIn();
	$("#anotacion_comentario_textarea_" + $(s_this).attr("lang")).focus();
}
/**Click a nueva anotacion*/
function formulario_anotacion_agregar() {
	$("#anotacion_pre_nuevo").css("display", "block");
	$("#anotacion_pre_nuevo_boton").css("display", "block");
}
/**Esconder el formulario de la anotacion*/
function formulario_anotacion_esconder() {
	$("#anotacion_pre_nuevo_boton").css("display", "none");
	$("#anotacion_pre_nuevo").css("display", "none");
}
/**Crear una anotacion */
function crear_anotacion() {
	var contenido = $("#anotacion_titulo_textarea_0").val();
	contenido = $.trim(contenido);
	var isreducido = false;
	if (contenido.length > 200) {
		isreducido = true;
		$("#anotacion_titulo_textarea_0").val(contenido.substr(0, 200));
	}
	resizeAnotacion();
	$.ajax({type:"POST", url:request_context + "/anotacion/Insertar.action", data:"titulo=" + escape($("#anotacion_titulo_textarea_0").val()), success:function (msg) {
		if (msg == "0") {
			mensaje_error(nota_mensaje_txt_error, 1);
		} else {
			document.location = request_context + "/anotacion/Anotacion.action";
		}
	}, error:function () {
		mensaje_error(nota_mensaje_txt_error, 1);
	}});
}
/**Click en la imagen de eliminar*/
function eliminar_anotacion(s_this) {
	if (window.confirm(nota_mensaje_eliminar)) {
		$.ajax({type:"POST", url:request_context + "/anotacion/Eliminar.action", data:"id=" + s_this, success:function (msg) {
			if (msg == "0") {
				mensaje_error(nota_mensaje_txt_error, 1);
			} else {
				mensaje_error(nota_mensaje_txt_eliminado);
				$("#anotacion_segundo_" + s_this).css("display", "none");
				$("#anotacion_" + s_this).fadeOut();
			}
		}, error:function () {
			mensaje_error(nota_mensaje_txt_error, 1);
		}});
	}
}
/**Selecione una anotacion*/
function selecionar_anotacion(s_this) {
	mensaje_error();
	deselecionarNota();
	$(s_this).css("borderRightWidth", "4px");
	$(s_this).css("borderBottomWidth", "4px");
	$(s_this).css("borderRightStyle", "outset");
	$(s_this).css("borderBottomStyle", "outset");
	$(s_this).find(".anotacion_border_selecionar").addClass("cursor_mano");
	$(s_this).find(".anotacion_border_selecionar").addClass("anotacion_border_selecionado");
	if ($.browser.msie === true && jQuery.browser.version === "6.0") {
		$("#anotacion_borde_azul_" + $(s_this).attr("lang")).height($(s_this).height());
	}
}
function imprimir_general(s_id) {
	if (session_impresora != s_id) {
		mensaje_error(nota_imprimiendo);
		session_impresora = s_id;
		$.jPrintArea("#anotacion_" + s_id);
	}
}
/**Ajusta la ventana de acuerdo a las anotaciones*/
function resizeAnotacion() {
	if ($.browser.msie === true) {
		var totalHeight = 0;
		$("div.anotacion_primero").each(function () {
			totalHeight += $(this).height();
		});
		$("div.anotacion_segundo").each(function () {
			totalHeight += $(this).height();
		});
		$("div.anotacion_total").height(totalHeight);
		if (totalHeight < ($(document).height())) {
			$("#pop_cuerpo").height($(document).height());
		}
	}
}
/**Para quitar el estilo selecionado*/
function deselecionarNota() {
	$(".anotacion_border_selecionar").each(function () {
		$(this).removeClass("cursor_mano");
		$(this).removeClass("anotacion_border_selecionado");
	});
	$(".anotacion_primero").each(function () {
		$(this).css("borderWidth", "");
		$(this).css("borderStyle", "");
	});
}
/**El manejo del error "s_mensaje" el mensaje a mostrar "s_tipo" 
 si envia cualquier valor significa grave y saldra de color rojo*/
function mensaje_error(s_mensaje, s_tipo) {
	try {
		if (null != s_mensaje && 0 < s_mensaje.length) {
			$("#txtError").html(s_mensaje);
			$("#div_txtError").center({vertical:false, horizontal:true});
			$("#div_txtError").fadeIn("fast", function () {
				$("#div_txtError").fadeOut(3000);
			});
			$("#div_txtError").css("position", "fixed");
			if (null != s_tipo) {
				$("#txtError").css("color", "#FFFFFF");
				$("#td_txtError11").css("backgroundImage", "url('" + request_context + "/img/punto_rojo.png')");
				$("#td_txtError12").css("backgroundColor", "#CC0000");
				$("#td_txtError13").css("backgroundImage", "url('" + request_context + "/img/punto_rojo.png')");
				$("#td_txtError21").css("backgroundColor", "#CC0000");
				$("#td_txtError22").css("backgroundColor", "#CC0000");
				$("#td_txtError23").css("backgroundColor", "#CC0000");
				$("#td_txtError31").css("backgroundImage", "url('" + request_context + "/img/punto_rojo.png')");
				$("#td_txtError32").css("backgroundColor", "#CC0000");
				$("#td_txtError33").css("backgroundImage", "url('" + request_context + "/img/punto_rojo.png')");
			} else {
				$("#txtError").css("color", "#000000");
				$("#td_txtError11").css("backgroundImage", "url('" + request_context + "/img/punto_amarillo.png')");
				$("#td_txtError12").css("backgroundColor", "#FFF1A8");
				$("#td_txtError13").css("backgroundImage", "url('" + request_context + "/img/punto_amarillo.png')");
				$("#td_txtError21").css("backgroundColor", "#FFF1A8");
				$("#td_txtError22").css("backgroundColor", "#FFF1A8");
				$("#td_txtError23").css("backgroundColor", "#FFF1A8");
				$("#td_txtError31").css("backgroundImage", "url('" + request_context + "/img/punto_amarillo.png')");
				$("#td_txtError32").css("backgroundColor", "#FFF1A8");
				$("#td_txtError33").css("backgroundImage", "url('" + request_context + "/img/punto_amarillo.png')");
			}
			return;
		} else {
			$("#div_txtError").fadeOut("slow");
			return;
		}
	}
	catch (e) {
	}
}
function stripNewLine(str) {
	var entra = new Array("\n", "\r");
	var sale = new Array("", "");
	var oc = new String(str), nt = new String(""), nc;
	for (var q = 0; q < oc.length; q++) {
		nc = oc.charAt(q);
		for (var w = 0; w < entra.length; w++) {
			if (oc.charAt(q) == entra[w]) {
				nc = sale[w];
				break;
			}
		}
		nt += nc;
	}
	delete oc, nt;
	return nt;
}

