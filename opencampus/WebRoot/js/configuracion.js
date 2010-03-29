Event.observe(window, 'load', function() { 
	loadServices();
});

function anadirServicio(check, id) {
		var valors = (check.checked==true)?'1':'0';
		new Ajax.Request(xGetContextPath() +"/portal/GrabarPortalEliminado.action",{parameters:"stringServicio="+id+"&estadoServicio="+valors});
}

function reiniciarServicio(){
	if(window.confirm('¿Desea reestablecer los servicios por defecto?')){
		document.location = xGetContextPath() +'/ConfiguracionPortalDefault.action';
	}
}




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
