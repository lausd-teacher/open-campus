/*
 * DarkPanel.show('theDiv',{duration	: 0.0});
 * DarkPanel.hide('theDiv');
 * .open_modal{display: none;} 
 */
var DarkPanel = {
	id : 'DarkPanel',
	panels :  new Object(),
	show : function (id,options){
		if(!id)id=this.id;
		
		this.options = {
			duration	: 1.0, //0.0 sin efecto
			fn 			: false
		}
		
		Object.extend(this.options, options || {});
		
		panel = DarkPanel.panels[id];
		if(!panel){
			panel = new Capa(id,this.options);
			DarkPanel.panels[id] = panel;
		}
		if(panel)panel.show();
	},
	hide : function (id){
		if(!id)id=this.id;
		panel = DarkPanel.panels[id];
		if(panel)panel.hide();
	}
}

var Capa = Class.create();  
Object.extend(Object.extend(Capa.prototype, Abstract.prototype), {
	initialize: function(id,options) {
		this.id = id;     
		this.options = options;
		this.capa = new Element('div', { id:this.id + '_shadow' , className:'select-free shadown'});
		this.capa.setStyle({display: 'none'});
		
		if("Explorer" === xBrowser.getName() && xBrowser.getVersion() < 7){
			this.capa.setStyle({	top: document.viewport.getScrollOffsets().top+'px', left: document.viewport.getScrollOffsets().left+'px', 
									width: document.viewport.getWidth()+'px', height: document.viewport.getHeight()+'px' }); 
			this.capa.insert(new Element('iframe')); 
			Event.observe(window, 'scroll', this.scroll.bind(this)); //reemplazado por position:fixed en IE7
		}
		this.capa.observe('click', this.hide.bind(this)); //IE6 No soporta, ya que el modal cubre al shadown
		//Event.observe(document.onresize ? document : window, 'resize', this.resize.bind(this)); //reemplazado por width:100%
		$(document.body).insert(this.capa); 
		
		//Ventana modal
		if($(this.id)){
			 if(!$(this.id).hasClassName('select-free'))$(this.id).addClassName('select-free');
			 $(document.body).insert($(this.id));
		}
	},  
	resize: function() {
		this.capa.setStyle({width: document.viewport.getWidth()+'px', height: document.viewport.getHeight()+'px' }); 	
	},
	scroll: function() {  
		this.capa.setStyle({top: document.viewport.getScrollOffsets().top+'px', left: document.viewport.getScrollOffsets().left+'px'}); 
	},
	show: function(duration) {  
		this.capa.appear({ duration: this.options.duration, from: 0, to: 0.6 });
		if($(this.id)){ $(this.id).show(); $(this.id).style.display = 'block';}
	}, 
	hide: function() { 
		this.capa.fade({ duration: this.options.duration });
		if($(this.id)) $(this.id).hide();
		if(this.options.fn)this.options.fn();
	}
}); 

Event.observe(window, 'load', function() {
	$$('.open_modal').each(function(el){
		$(el).setStyle({
			/*position: 'fixed !important', 
			position: 'absolute',*/
			/*bottom: '0',*/
			/*top: '50%',
			left: '50%',*/ //mejor paso a ser estilo para que se aplique de inmediato
			marginLeft : (-1*$(el).getWidth()/2)+'px', //ver la posibilidad de pasar a estilos
			marginTop : (-1*$(el).getHeight()/2)+'px'
		});
	});
});