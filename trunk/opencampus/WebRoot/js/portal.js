
if (typeof CampusVirtual == "undefined") {
	CampusVirtual = {};
}
Builder.dump();
CampusVirtual.Widget = Class.create();
CampusVirtual.Widget.lastId = 0;
Object.extend(CampusVirtual.Widget.prototype, {initialize:function (id) {
	this._div = document.getElementById(id);
	this._div.widget = this;
	this._div2 = document.getElementById("tabla_"+id);
	this._div2.widget = this;
	return this;
}, getElement:function () {
	return $(this._div);
}, getElement1:function () {
	return $(this._div2);
}});
CampusVirtual.Portal = Class.create();
Object.extend(CampusVirtual.Portal.prototype, {lastEvent:null, widgets:null, columns:null, initialize:function (columns, options) {
	this.options = Object.extend({url:xGetContextPath()+"/GrabarPortal.action", onOverWidget:null, onOutWidget:null, onChange:null, onUpdate:null, removeEffect:Element.remove, accept:null}, options);
	this._columns = (typeof columns == "string") ? $$(columns) : columns;
	this._widgets = new Array();
	this._columns.each(function (element) {
		Droppables.add(element, {onHover:this.onHover.bind(this), overlap:"vertical", accept:this.options.accept});
	}.bind(this));
	this._outTimer = null;
	this._columns.invoke("undoPositioned");
	this._currentOverWidget = null;
	this._widgetMouseOver = this.widgetMouseOver.bindAsEventListener(this);
	this._widgetMouseOut = this.widgetMouseOut.bindAsEventListener(this);
	Draggables.addObserver({onEnd:this.endDrag.bind(this), onStart:this.startDrag.bind(this)});
}, add:function (widget, columnIndex, draggable) {
	draggable = typeof draggable == "undefined" ? true : draggable;
	this._widgets.push(widget);
	if (this.options.accept) {
		widget.getElement().addClassName(this.options.accept);
	}
	this._columns[columnIndex].appendChild(widget.getElement());
	if (draggable) {
		widget.draggable = new Draggable(widget.getElement(), {handle:widget._div2, revert:false});
	}
	this._updateColumnsHeight();
	if (this.options.onOverWidget) {
		widget.getElement().immediateDescendants().invoke("observe", "mouseover", this._widgetMouseOver);
	}
	if (this.options.onOutWidget) {
		widget.getElement().immediateDescendants().invoke("observe", "mouseout", this._widgetMouseOut);
	}
	xShowD(widget.getElement());
}, remove:function (widget) {
	this._widgets.reject(function (w) {
		return w == widget;
	});
	if (this.options.onOverWidget) {
		widget.getElement().immediateDescendants().invoke("stopObserving", "mouseover", this._widgetMouseOver);
	}
	if (this.options.onOutWidget) {
		widget.getElement().immediateDescendants().invoke("stopObserving", "mouseout", this._widgetMouseOut);
	}
	if (widget.draggable) {
		widget.draggable.destroy();
	}
	this.options.removeEffect(widget.getElement());
	this._updateColumnsHeight();
}, serialize:function () {
	parameters = "";
	this._columns.each(function (column) {
		var p = column.immediateDescendants().collect(function (element) {
			return column.id + "[]=" + element.id;
		}).join("&");
		parameters += p + "&";
	});
	return parameters;
}, serialize2:function () {
	parameters = "";
	this._columns.each(function (column) {
		var p = column.immediateDescendants().collect(function (element) {
			return column.id + "=" + element.id+"&";
		}).join("&");
		parameters += p;
	});
	return parameters;
}, widgetMouseOver:function (event) {	
	this._clearTimer();
	var element = Event.element(event).up(".widget");
	if (this._currentOverWidget == null || this._currentOverWidget != element) {
		if (this._currentOverWidget && this._currentOverWidget != element) {
			this.options.onOutWidget(this, this._currentOverWidget.widget);
		}
		this._currentOverWidget = element;
		try {			
			this.options.onOverWidget(this, element.widget);
		}
		catch (e) {
		}
	}
}, widgetMouseOut:function (event) {
	this._clearTimer();
	var element = Event.element(event).up(".widget");
	this._outTimer = setTimeout(this._doWidgetMouseOut.bind(this, element), 100);
}, _doWidgetMouseOut:function (element) {
	this._currentOverWidget = null;
	try {
		this.options.onOutWidget(this, element.widget);
	}
	catch (e) {
	}
}, startDrag:function (eventName, draggable) {
	var widget = draggable.element;
	if (!this._widgets.find(function (w) {
		return w == widget.widget;
	})) {
		return;
	}	
	var column = widget.parentNode;
	var ghost = DIV({className:"widget_ghost"}, "");
	$(ghost).setStyle({height:widget.getHeight() + "px"});
	column.insertBefore(ghost, widget);
	widget.setStyle({width:widget.getWidth() + "px"});
	Position.absolutize(widget);
	document.body.appendChild(widget);
	draggable.element.ghost = ghost;
	this._savePosition = this.serialize();
}, endDrag:function (eventName, draggable) {
	var widget = draggable.element;
	if (!this._widgets.find(function (w) {
		return w == widget.widget;
	})) {
		return;
	}
	var column = widget.ghost.parentNode;
	column.insertBefore(draggable.element, widget.ghost);
	widget.ghost.remove();
	if (Prototype.Browser.Opera) {
		widget.setStyle({top:0, left:0, width:"100%", height:widget._originalHeight, zIndex:null, opacity:null, position:"relative"});
	} else {
		widget.setStyle({top:null, left:null, width:null, height:widget._originalHeight, zIndex:null, opacity:null, position:"relative"});
	}
	widget.ghost = null;
	this._updateColumnsHeight();
	if (this._savePosition != this.serialize()) {
		if (this.options.url) {
			new Ajax.Request(this.options.url, {parameters:this.serialize2()});			
		}
		if (this.options.onUpdate) {
			this.options.onUpdate(this);
		}
	}
}, onHover:function (dragWidget, dropon, overlap) {
	var offset = Position.cumulativeOffset(dropon);
	var x = offset[0] + 10;
	var y = offset[1] + (1 - overlap) * dropon.getHeight();
	if (Position.within(dragWidget.ghost, x, y)) {
		return;
	}
	var found = false;
	var moved = false;
	for (var index = 0, len = this._widgets.length; index < len; ++index) {
		var w = this._widgets[index].getElement();
		if (w == dragWidget || w.parentNode != dropon) {
			continue;
		}
		if (Position.within(w, x, y)) {
			var overlap = Position.overlap("vertical", w);
			if (overlap < 0.5) {
				if (w.next() != dragWidget.ghost) {
					w.parentNode.insertBefore(dragWidget.ghost, w.next());
					moved = true;
				}
			} else {
				if (w.previous() != dragWidget.ghost) {
					w.parentNode.insertBefore(dragWidget.ghost, w);
					moved = true;
				}
			}
			found = true;
			break;
		}
	}
	if (!found) {
		if (dragWidget.ghost.parentNode != dropon) {
			var last = dropon.immediateDescendants().last();
			var yLast = last ? Position.cumulativeOffset(last)[1] + last.getHeight() : 0;
			if (y > yLast && last != dragWidget.ghost) {
				dropon.appendChild(dragWidget.ghost);
				moved = true;
			}
		}
	}
	if (moved && this.options.onChange) {
		this.options.onChange(this);
	}
	this._updateColumnsHeight();
}, _updateColumnsHeight:function () {
	var h = 0;
	this._columns.each(function (col) {
		h = Math.max(h, col.immediateDescendants().inject(0, function (sum, element) {
			return sum + element.getHeight();
		}));
	});
	h = (h < 350) ? 350 : h;
	this._columns.invoke("setStyle", {height:h + "px"});
}, _clearTimer:function () {
	if (this._outTimer) {
		clearTimeout(this._outTimer);
		this._outTimer = null;
	}
}});
function onOverWidget(portal, widget) {
}
function onOutWidget(portal, widget) {
}
function removeWidget(element) {
}
function onChange() {
}

function esconderServicio(s_div) {
	var s_tr = document.getElementById(s_div + "_tr");
	var s_estado=0;
	if (s_tr.style.display == "none") {
		s_tr.style.display = "";
		s_tr.style.visibility = "visible";
		s_estado=1;
	} else {
		s_tr.style.display = "none";
		s_tr.style.visibility = "hidden";
	}
	portal._updateColumnsHeight();
	new Ajax.Request(xGetContextPath() +"/GrabarPortalVisible.action",{parameters:"stringServicio="+s_div+"&estadoServicio="+s_estado});
}

function eliminarServicio(s_div) {
	var s_tr = document.getElementById(s_div);
	s_tr.innerHTML="&nbsp;";
	s_tr.style.display = "none";
	s_tr.style.visibility = "hidden";
	portal._updateColumnsHeight();
	new Ajax.Request(xGetContextPath() +"/GrabarPortalEliminado.action",{parameters:"stringServicio="+s_div+"&estadoServicio=0"});
}

function eliminarAula(){
	var myConn = new XHConn();
	myConn.connect(xGetContextPath()+"/aulavirtual/SalirDelAula.action", "POST", null, function (oXML) {});
}

/* Carga Servicios */
var msg_error = '<strong>Problemas con el servicio</strong>';
function cargar_servicio_curso(){
	var myConn = new XHConn();
	var query = function (oXML) {
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_curso_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_curso_descripcion',xInnerHtml('servicio_curso_descripcion_origen'));
				eliminarAula();//Corrige el error de al regresar a portal no se elimina el aula session
			}else{
				xInnerHtml('servicio_curso_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/ficha/CargarPortada.action", "POST", null, query);
}
function cargar_servicio_buzon(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_buzon_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_buzon_descripcion',xInnerHtml('servicio_buzon_descripcion_origen'));
			}else{
				xInnerHtml('servicio_buzon_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/comun/buzon/CargarPortada.action", "POST", null, query);
}
function cargar_servicio_noticia(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_noticia_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_noticia_descripcion',xInnerHtml('servicio_noticia_descripcion_origen'));
			}else{
				xInnerHtml('servicio_noticia_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/noticia/CargarPortada.action", "POST", null, query);
}
function cargar_servicio_foros(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_foros_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_foros_descripcion',xInnerHtml('servicio_foros_descripcion_origen'));
			}else{
				xInnerHtml('servicio_foros_td',msg_error)
			}
		}
	};
	//myConn.connect(xGetContextPath()+"/foro/CargarPortada.action", "POST", null, query);
}
function cargar_servicio_chat(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== "" && oXML.responseText.indexOf("<!--Chat-->") != -1){
				xInnerHtml('servicio_chat_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_chat_descripcion',xInnerHtml('servicio_chat_descripcion_origen'));
			}else{
				xInnerHtml('servicio_chat_td',msg_error)
			}
			setTimeout("cargar_servicio_chat()",5000);
		}
	};
	myConn.connect(xGetContextPath()+"/usuario/CargarConectadosEnChat.action", "POST", null, query);
}
function cargar_servicio_enlaces(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_enlaces_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_enlaces_descripcion',xInnerHtml('servicio_enlaces_descripcion_origen'));
			}else{
				xInnerHtml('servicio_enlaces_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/comun/portal/servicio_enlaces.jsp", "POST", null, query);
}
function cargar_servicio_glosario(){
	xInnerHtml('servicio_glosario_td','');
}
function cargar_servicio_biblioteca(){
	xInnerHtml('servicio_biblioteca_td','');
}
function cargar_servicio_apuntes(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_apuntes_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_apuntes_descripcion',xInnerHtml('servicio_apuntes_descripcion_origen'));
			}else{
				xInnerHtml('servicio_apuntes_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/anotacion/CargarPortada.action", "POST", null, query);
}
function cargar_servicio_cumpleanos(){
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			if(oXML.responseText !== ""){
				xInnerHtml('servicio_cumpleanos_td',oXML.responseText);
				portal._updateColumnsHeight();
				xInnerHtml('servicio_cumpleanos_descripcion',xInnerHtml('servicio_cumpleanos_descripcion_origen'));
			}else{
				xInnerHtml('servicio_cumpleanos_td',msg_error)
			}
		}
	};
	myConn.connect(xGetContextPath()+"/usuario/CargarPortada.action", "POST", null, query);
}
var intervalCalendar = null;
function cargar_servicio_agenda(){
	importarCSS(xGetContextPath()+'/js/jscalendar/campus.css');
	importar(xGetContextPath()+'/js/jscalendar/calendar.js');
	//importar(xGetContextPath()+'/js/jscalendar/calendar-es.js');
	//importar(xGetContextPath()+'/js/jscalendar/calendar-setup.js');
				
	var myConn = new XHConn();
	var query = function (oXML) { 
		if (oXML.readyState === 4) {
			
				this.intervalCalendar = setInterval("startCalendar('"+oXML.responseText+"')",100);
				xInnerHtml('servicio_agenda_descripcion','<strong><span class="text_rojo">'+ (oXML.responseText.split('/').length-1) +'</span> evento(s)<strong>');
				
		}
	};
	myConn.connect(xGetContextPath()+"/agenda/CargarPortada.action", "POST", null, query);
}
var startCalendar = function(days){
	try{
		if(Calendar){
			importar(xGetContextPath()+'/js/jscalendar/calendar-setup.js');
			importar(xGetContextPath()+'/js/jscalendar/calendar-es.js');
			xInnerHtml('servicio_agenda_td','<div id="cv_agenda" style="width:99%;"></div>');
			Calendar.setup(
			    {
			      flat         : "cv_agenda",
			      weekNumbers  : false,
			      showOthers   : true,
			      date		   : ahorita,
			      step		   : 1,
			      //showsTime	   : true,
			      showRowHead  : true,
			      showRowNavegator : false,
			      showToolTips : false, 
			      range        : [2000, 2020],
			      changeFirstDay  : false,
			      disabled	   : true,
			      specialDay   : days
			    }
			);
			portal._updateColumnsHeight();
			clearInterval(this.intervalCalendar);
		}
	}catch(e){
		
	}
}

function cambiarPagina(url) {
	window.document.location = url;
}

function importar(rutaJS) { 
    var scr=document.createElement("SCRIPT"); 
    scr.setAttribute("src", rutaJS); 
    scr.setAttribute("type", "text/javascript"); 
    document.body.appendChild(scr); 
}

function importarCSS(rutaCSS) { 
    var scr=document.createElement("LINK"); 
    scr.setAttribute("href", rutaCSS); 
    scr.setAttribute("rel", "stylesheet"); 
    scr.setAttribute("type", "text/css"); 
    document.body.appendChild(scr); 
}  

function resize() {
	var blockerDiv = xGetElementById("blocker");
	if (blockerDiv && blockerDiv.style.display != "none") {
		xMoveTo("blocker", -(xPageX("cuerpo") + 1), -xPageY("cuerpo"));
		xResizeTo("blocker", xClientWidth(), xClientHeight());
	}
}
