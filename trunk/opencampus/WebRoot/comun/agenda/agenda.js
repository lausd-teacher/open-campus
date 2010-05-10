Agendaopencampus = function(){
	currentDate = null;
	eventosopencampus = new Array();
	daysOfCurrentMonthCell = null;
};

var agendaopencampus = new Agendaopencampus();

function mostrarAgendaForm(){
	xGetElementById('evento_form').reset(); 
	xHideD('agenda_mAntesInput');
	xHideD('agenda_mAntesSelect');
	xMoveTo('blocker',xScrollLeft(),xScrollTop()-10);
	xResizeTo('blocker',xClientWidth(),xClientHeight()+10);
	Effect.Appear('blocker', {from: 0.0, to: 0.1 });
	xShow('agenda_form');
	xMoveTo('agenda_form',(xClientWidth()-xWidth('agenda_form'))/2,xScrollTop()+(xClientHeight()-xHeight('agenda_form'))/2);
	
	Drag.init(xGetElementById('title_agenda_form'), xGetElementById('agenda_form'));
}

function mostrarAgendaParaModificar(agenda){
	mostrarAgendaForm();
	var form = xGetElementById('evento_form');
	form.sumilla.value = agenda.sumilla;
	form.detalle.value = agenda.detalle;
	form.counter.value = 512 - agenda.detalle.length;
	form.hora.value = agenda.getHour();
	form.minuto.value = agenda.getMinute();
	if(agenda.notify == '1'){
		form.notificar.checked = true;
		
		if(agenda.minutos >= 1440 && agenda.minutos%1440 === 0){
			form.minutoAntes.value = agenda.minutos/1440;
			form.escala.value = 'd';
		}else if(agenda.minutos >= 60 && agenda.minutos%60 === 0){
			form.minutoAntes.value = agenda.minutos/60;
			form.escala.value = 'h';
		}else{
			form.minutoAntes.value = agenda.minutos;
		}
		
		xShowD('agenda_mAntesInput');
		xShowD('agenda_mAntesSelect');
	}
	form.cmd.value = agenda.fecha;
}

function dateChanged(calendar) {
	if (calendar.dateClicked) {
		calendar.dateClicked = false;
		agendaopencampus.currentDate =  calendar.date.print("%d-%m-%Y");
		xInnerHtml('agenda_curDate', 'Eventos para el '+calendar.date.print("%d de %B"));
		//xInnerHtml('agenda_curDate_title', calendar.date.print("%d"));
		
  		var myConn = new XHConn();
		var query = function (oXML) { 
			if (oXML.readyState === 4) {
				var xml = oXML.responseXML;
				agendaopencampus.eventosopencampus = new Array();
				xInnerHtml('agenda_ver_head',calendar.date.print("%A, %d de %B de %Y"));
				xInnerHtml('agenda_events','');
				xShowD('agenda_table');
				xHide('agenda_ver');
				xHide('agenda_form');
				if(oXML.responseText !== ""){
					//xShowD('agenda_events');
					if(xml.getElementsByTagName("eventos").length !== 0){
						showEvents(xml);
					}else{
						alert('Hubo un error al intentar cargar la lista de eventos,\nse sugiere actualizar la página.');
					}
				}else{
					//xHideD('agenda_events');
				}
			}
		};
			
		myConn.connect(xGetContextPath()+"/agenda/Eventos.action", "POST", 'date=' + agendaopencampus.currentDate, query);
		
	}
}

function guardarEvento(form){
	if(form.sumilla.value.isEmpty()){
		alert('Debe indicar el nombre del evento.');
		return false;
	}
	/*if(form.detalle.value.isEmpty()){
		alert('Debe indicar un detalle del evento.');
		return false;
	}*/
	
	if(form.hora.value.isEmpty() || !form.hora.value.isInteger() || 
		!(0 <= parseFloat(form.hora.value) && parseFloat(form.hora.value)<24)){
		alert('Debe indicar un valor entero entre 0 y 23 para la hora.');
		return false;
	}
	
	if(form.minuto.value.isEmpty() || !form.minuto.value.isInteger() || 
		!(0 <= parseFloat(form.minuto.value) && parseFloat(form.minuto.value)<60)){
		alert('Debe indicar un valor entero entre 0 y 59 para el minuto.');
		return false;
	}
	
	if(form.notificar.checked){
		if(form.minutoAntes.value.isEmpty() || !form.minutoAntes.value.isInteger()){
			alert('Debe indicar un valor entero para los minutos.');
			return false;
		}
		var scale = form.escala.options[form.escala.selectedIndex].value;
		if(scale === 'h' && form.minutoAntes.value > 144){
			alert('El tiempo de anticipación máximo es de 144 horas.');
			return false;
		}
		if(scale === 'd' && form.minutoAntes.value > 6){
			alert('El tiempo de anticipación máximo es de 6 días.');
			return false;
		}
	}
	
	var cmd = form.cmd.value;
	var sumilla = form.sumilla.value.stripWithSpace();
	var detalle = form.detalle.value.stripWithSpace().substring(0,512);
	var hora = (form.hora.value < 10) ? ("0" + parseFloat(form.hora.value)) : form.hora.value;
	var minuto = (form.minuto.value < 10) ? ("0" + parseFloat(form.minuto.value)) : form.minuto.value;
	var date = agendaopencampus.currentDate+' '+hora+':'+minuto;
	
	//Validar si la fecha ya esta en uso
	for(var i=0; i<agendaopencampus.eventosopencampus.length; i=i+1) {
		if(agendaopencampus.eventosopencampus[i].fecha == date){
			if(cmd == '0'){
				alert('Ya cuenta con un evento en la misma hora y minuto.');
				return false;
			}else if(agendaopencampus.eventosopencampus[i].fecha != cmd){
				alert('Ya cuenta con un evento en la misma hora y minuto.');
				return false;
			}
		}	
	}
	
	var cadena = 'cmd='+cmd+'&sumilla='+escape(sumilla)+'&detalle='+escape(detalle)+'&date='+date;
	
	var minutoAntes = 0;
	var notify = form.notificar.checked;
	if(notify){
		minutoAntes = parseFloat(form.minutoAntes.value);
		scale = form.escala.options[form.escala.selectedIndex].value;
		if(scale === 'h'){
			minutoAntes = minutoAntes*60;
		}else if(scale === 'd'){
			minutoAntes = minutoAntes*60*24;
		}
		cadena += '&notificado=1&minutoAntes='+minutoAntes;
	}
	
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText === "OK"){
				
				if(cmd == '0'){
					var agenda = new Agenda();
					agenda.sumilla = sumilla;
					agenda.detalle = detalle;
					agenda.fecha = date;
					agenda.notify = (notify) ? 1 : 0;
					agenda.minutos = minutoAntes;
					if(agendaopencampus.eventosopencampus.length === 0){
						var curdate = this.ahorita ? new Date(this.ahorita) : new Date();
						var dateTmp = new Date();
						dateTmp.setDate(1);
						dateTmp.setFullYear(agenda.getYear());
						dateTmp.setMonth(agenda.getMonth()-1);
						dateTmp.setDate(agenda.getDay());
						
						if(dateTmp.equalsTo(curdate)){
							getCellofDay(parseFloat(agenda.getDay())).className = "day specialdayToday";
						}else if(curdate.moreTo(dateTmp)){
							getCellofDay(parseFloat(agenda.getDay())).className = "day specialdayOld";
						}else{
							getCellofDay(parseFloat(agenda.getDay())).className = "day specialday";
						}
					}
					agendaopencampus.eventosopencampus.push(agenda);
					paintEvents(xGetElementById('agenda_events'), agenda);
				}else{
					for(var i=0; i<agendaopencampus.eventosopencampus.length; i=i+1) {
						if(agendaopencampus.eventosopencampus[i].fecha === cmd){
							var listTitle = xGetElementById('list_title_'+agendaopencampus.eventosopencampus[i].getHourID());
							var listBody  = xGetElementById('list_body_'+agendaopencampus.eventosopencampus[i].getHourID());
							
							agendaopencampus.eventosopencampus[i].fecha = date;
							agendaopencampus.eventosopencampus[i].sumilla = sumilla;
							agendaopencampus.eventosopencampus[i].detalle = detalle;
							agendaopencampus.eventosopencampus[i].notify = (notify) ? 1 : 0;
							agendaopencampus.eventosopencampus[i].minutos = minutoAntes;
							
							listTitle.innerHTML = agendaopencampus.eventosopencampus[i].getTime() + ' hs';
							listBody.innerHTML = agendaopencampus.eventosopencampus[i].sumilla;
							listTitle.id = 'list_title_' + agendaopencampus.eventosopencampus[i].getHourID();
							listBody.id = 'list_body_' + agendaopencampus.eventosopencampus[i].getHourID();
							break;
						}
					}
				}
				
				xGetElementById('evento_form').reset(); 
				xHide('agenda_form');
				xHide('agenda_ver');
				xHideD('blocker');
			}else{
				alert('Hubo un error al intentar cargar la lista de eventos,\nse sugiere actualizar la página.');
			}
		}
	};
		
	myConn.connect(xGetContextPath()+"/agenda/GuardarEvento.action", "POST", cadena, query);
	
	return false;
}

function cuentaCaracteres(txtArea){
	var limit = 512;
	if(txtArea.value.length > limit){
		//alert("Llegó al límite de número de caracteres.");
		txtArea.value = txtArea.value.substring(0,limit);
	}
	xGetElementById('counter').value = limit-txtArea.value.length;
}

function incrementar(eID, limite){
	var value = xGetElementById(eID).value;
	if(value.isEmpty() || !value.isInteger()){
		xGetElementById(eID).value = '00';
	}else{
		value = parseFloat(value)+1;
		if(value>=limite){
			xGetElementById(eID).value = '00';
		}else{
			xGetElementById(eID).value = (value < 10) ? ("0" + value) : value;
		}
	}
}

function decrementar(eID, limite){
	var value = xGetElementById(eID).value;
	if(value.isEmpty() || !value.isInteger()){
		xGetElementById(eID).value = '00';
	}else{
		value = parseFloat(value)-1;
		limite = limite -1;
		if(value<=0){
			xGetElementById(eID).value = (limite < 10) ? ("0" + limite) : limite;
		}else{
			xGetElementById(eID).value = (value < 10) ? ("0" + value) : value;
		}
	}
}

function changeNotify(check){
	if(check.checked){
		xShowD('agenda_mAntesInput');
		xShowD('agenda_mAntesSelect');
	}else{
		xHideD('agenda_mAntesInput');
		xHideD('agenda_mAntesSelect');
	}
}

function getCellofDay(day){
	for(var i=0; i<agendaopencampus.daysOfCurrentMonthCell.length; i=i+1) {
		if(agendaopencampus.daysOfCurrentMonthCell[i].innerHTML == day){
			return agendaopencampus.daysOfCurrentMonthCell[i];
		}
	}
}

function eliminarEvento(agenda,divEvento){
	
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText === "OK"){
				for(var i=agendaopencampus.eventosopencampus.length-1; i>=0; i=i-1) {
					if(agendaopencampus.eventosopencampus[i] === agenda){
						agendaopencampus.eventosopencampus.splice(i,1);
					}
				}
				if(agendaopencampus.eventosopencampus.length === 0){
					getCellofDay(parseFloat(agenda.getDay())).className = "day";
				}
				divEvento.parentNode.removeChild(divEvento);
				xHide('agenda_form');
				xHide('agenda_ver');
			}else{
				alert('Hubo un error al intentar realizar la acción,\nse sugiere actualizar la página.');
			}
		}
	};
		
	myConn.connect(xGetContextPath()+"/agenda/EliminarEvento.action", "POST", 'date=' + agenda.fecha, query);
	
}

var yearTmp = "";
function ourDateStatusFunc(calendar) {
	//xHideD('agenda_table');
	xHide('agenda_form');
	xHide('agenda_ver');
	
	agendaopencampus.daysOfCurrentMonthCell = calendar.daysOfCurrentMonth;
	
	if(yearTmp != calendar.date.print("%Y")){
		yearTmp = calendar.date.print("%Y");
		
		var myConn = new XHConn();
		var query = function (oXML) { 
			if (oXML.readyState === 4) {
				if(oXML.responseText !== ""){
					var xml = oXML.responseXML;
					//Recorrer el Arreglo
					var curdate = calendar.dateStr ? new Date(calendar.dateStr) : new Date();
					var dateTmp = new Date();
					for (var x = 0; x < xml.getElementsByTagName("fecha").length; x = x + 1) {
						var fecha = xml.getElementsByTagName("fecha")[x].childNodes[0].nodeValue;
						calendar.specialDay.push(fecha);
						var fechaList = fecha.split("-");
						for(var i=0; i<calendar.daysOfCurrentMonth.length; i=i+1) {
							dateTmp.setDate(1);
							dateTmp.setFullYear(yearTmp);
							dateTmp.setMonth(parseFloat(calendar.date.getMonth()));
							dateTmp.setDate(parseFloat(calendar.daysOfCurrentMonth[i].innerHTML));
							
							if(fechaList[1] == (parseFloat(calendar.date.getMonth())+1) && 
								calendar.daysOfCurrentMonth[i].innerHTML == parseFloat(fechaList[0])){
								
								if(dateTmp.equalsTo(curdate)){
									calendar.daysOfCurrentMonth[i].className = "day specialdayToday";
								}else if(curdate.moreTo(dateTmp)){
									calendar.daysOfCurrentMonth[i].className = "day specialdayOld";
								}else{
									calendar.daysOfCurrentMonth[i].className = "day specialday";
								}
								
								break;
							}
						}
					}
				}
			}
		};
			
		myConn.connect(xGetContextPath()+"/agenda/EventosAnual.action", "POST", 'date='+yearTmp, query);
		
	}
	
	//Listar eventos del dia actual al cambiar de mes y de año
	this.dateClicked = true;
	dateChanged(this);
}

Agenda = function () {
	
	this.usuario = null;
	this.fecha = null;
	this.sumilla = null;
	this.detalle = null;
	this.notify = null;
	this.minutos = null;
	
	
};

Agenda.prototype.getTime = function () {
	var dateArray =  this.fecha.split(" ");
	return dateArray[1];
};

Agenda.prototype.getHour = function () {
	var dateArray =  this.fecha.split(" ");
	return dateArray[1].split(":")[0];
};

Agenda.prototype.getMinute = function () {
	var dateArray =  this.fecha.split(" ");
	return dateArray[1].split(":")[1];
};

Agenda.prototype.getDate = function () {
	var dateArray =  this.fecha.split(" ");
	return dateArray[0];
};

Agenda.prototype.getDay = function () {
	var dateArray =  this.fecha.split(" ");
	var date = dateArray[0].split("-");
	return date[0];
};

Agenda.prototype.getMonth = function () {
	var dateArray =  this.fecha.split(" ");
	var date = dateArray[0].split("-");
	return date[1];
};

Agenda.prototype.getYear = function () {
	var dateArray =  this.fecha.split(" ");
	var date = dateArray[0].split("-");
	return date[2];
};

Agenda.prototype.getMonthToString = function () {
	var dateArray =  this.fecha.split(" ");
	var date = dateArray[0].split("-");
	return Calendar._MN[parseFloat(date[1])-1];
};

Agenda.prototype.getDateToString = function () {
	var dateArray =  this.fecha.split(" ");
	var date = dateArray[0].split("-");
	alert(parseInt(date[1])-1);
	return date[0] + " de " + Calendar._MN[parseFloat(date[1])-1] + " de " + date[2] + " - " + dateArray[1];
};

Agenda.prototype.getHourID = function () {
	var dateArray =  this.fecha.split(" ");
	return dateArray[1].replace(':','_');
};

function showEvents(xml){
	//alert(oXML.responseText);
	var div = xGetElementById('agenda_events');
	
	//Recorrer el Arreglo
	for (var x = 0; x < xml.getElementsByTagName("evento").length; x = x + 1) {
		//Extraer valiables
		var evento = xml.getElementsByTagName("evento")[x];
		
		var agenda = new Agenda();
		
		agenda.fecha = evento.getElementsByTagName("fecha")[0].childNodes[0].nodeValue;
		agenda.sumilla = evento.getElementsByTagName("sumilla")[0].childNodes[0].nodeValue;
		agenda.detalle = evento.getElementsByTagName("detalle")[0].childNodes[0].nodeValue;
		agenda.notify = evento.getElementsByTagName("notify")[0].childNodes[0].nodeValue;
		agenda.minutos = evento.getElementsByTagName("minutos")[0].childNodes[0].nodeValue;
		
		agendaopencampus.eventosopencampus.push(agenda);
		
		paintEvents(div,agenda);
		
	}

}

function paintEvents(div, agenda){
	
	var divEvento = xCreate('div');
	
	//Encabezado
	var table = xCreate('table');
	table.className = 'calendario titleEvent';
	var tbody = xCreate("tbody");
	
	var tr = xCreate('tr');
	
	var td = xCreate('td');
	td.style.width = '12px';
	td.innerHTML = '<img src="'+xGetContextPath()+'/img/agenda/evento.gif" alt="Evento" />';
	tr.appendChild(td);
	
	td = xCreate('td');
	td.id = 'list_title_'+agenda.getHourID();
	td.innerHTML = agenda.getTime() + ' hs';
	tr.appendChild(td);
	
	td = xCreate('td');
	td.className = 'calendario options';
	td.innerHTML = '<img src="'+xGetContextPath()+'/img/icon_nota.gif" alt="Modificar" />';
	td.onclick = function(){
		mostrarAgendaParaModificar(agenda);
	};
	tr.appendChild(td);
	
	td = xCreate('td');
	td.className = 'calendario options';
	td.innerHTML = '<img src="'+xGetContextPath()+'/img/agenda_icon_trash.gif" alt="Eliminar" />';
	td.onclick = function(){
		if(confirm('¿Seguro que quieres eliminar dicho evento?')){
			eliminarEvento(agenda,this.parentNode.parentNode.parentNode.parentNode);
		}
	};
	tr.appendChild(td);
	
	/*if("Explorer" === xBrowser.getName()){
		td = xCreate('td');
		td.className = 'calendario options';
		tr.appendChild(td);
	}*/
	
	tbody.appendChild(tr);
	
	table.appendChild(tbody);
	divEvento.appendChild(table);
	
	//Cuerpo
	table = xCreate('table');
	table.cellPadding="0";
	table.className = 'calendario bodyEvent';
	table.onmouseover = function(){
		this.style.background = "#EAEBED";
	};
	table.onmouseout = function(){
		this.style.background = "";
	};
	table.onclick = function(){
		xMoveTo('agenda_ver',(xClientWidth()-xWidth('agenda_ver'))/2,xScrollTop()+(xClientHeight()-xHeight('agenda_ver'))/2);
		xMoveTo('blocker',xScrollLeft(),xScrollTop()-10);
		xResizeTo('blocker',xClientWidth(),xClientHeight()+10);
		Effect.Appear('blocker', {from: 0.0, to: 0.1 });
		xShow('agenda_ver');
		xInnerHtml('agenda_ver_head_hour',agenda.getTime() + ' hs');
		xInnerHtml('agenda_ver_title',agenda.sumilla);
		xInnerHtml('agenda_ver_body',agenda.detalle);
		
		Drag.init(xGetElementById('title_agenda_ver'), xGetElementById('agenda_ver'));
	};
	tbody = xCreate("tbody");
	
	tr = xCreate('tr');
	
	td = xCreate('td');
	td.id = 'list_body_'+agenda.getHourID();
	td.innerHTML = agenda.sumilla;
	tr.appendChild(td);
		
	tbody.appendChild(tr);
	
	table.appendChild(tbody);
	divEvento.appendChild(table);
	
	div.appendChild(divEvento);
	
}

function resize() {
	xResizeTo('blocker',xClientWidth(),xClientHeight()+10);
}

function scrolling() {
	xMoveTo('blocker', xScrollLeft(),xScrollTop()-10);
}
