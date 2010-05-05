// -----------------------------------------------------------------------------------
//
//	Anatips v0.1
//	by Christophe Gimenez - http://www.anaema.com
//	Last Modification: 10/2/2008
//
//	Licensed under the Creative Commons Attribution 2.5 License - http://creativecommons.org/licenses/by/2.5/
//      - Free for use in both personal and commercial projects
//      - Attribution requires leaving author name, author link, and the license info intact.
//	
// -----------------------------------------------------------------------------------

if(!ANAEMA) var ANAEMA = {};

ANAEMA.Anatips = Class.create({
	objTip:   undefined,
	tipsArray:  [],

	// Constructor runs on completion of the DOM loading. 
	initialize: function() {	
		this.tipsArray = [];		
		objBody = $$('body')[0];
		this.objTip = objBody.appendChild(Builder.node('div',{id:'anatips_tip'}));
		this.setupTooltips();		
	},	
	
	// Scans the DOM to find all elements with an 'anatips' class attribute and bind them to the 3 events handlers
	setupTooltips: function() {
		me = this;
		cnt = 0;
		$$('.anatips').each(function(el){				
			if(el.title && el.title.length > 0) {				
				el.observe('mouseover', (function(event) { event.stop; me.handleMouseOver(event) }).bind(this));
				el.observe('mousemove', (function(event) { event.stop; me.handleMouseMove(event) }).bind(this));
				el.observe('mouseout',  (function(event) { event.stop; me.handleMouseOut(event) }).bind(this));
				me.tipsArray[cnt] = el.title;
				el.tip_num = cnt++;
				el.removeAttribute('title');
			}
		})		
	},		
	
	// Called when mouse enters the target : setup and show container
	handleMouseOver: function(event){
		target = event.findElement('.anatips');
		if (target) {
			this.objTip.innerHTML = this.tipsArray[target.tip_num];
			this.objTip.style.visibility = "visible";
			image = event.findElement('.anatips img'); // Disable inner images's alt attribute (prevents IE auto alt tooltips)
			if(image) image.alt = '';
		}
	},
	
	// Called when mouse moves over the target : move the tip container
	handleMouseMove: function(event){
		target = event.findElement('.anatips');
		if (target) {
			this.objTip.style.left = (event.pointerX() + 15) + 'px';
			this.objTip.style.top = (event.pointerY() + 15) + 'px';
		}
	},
	
	// Called when mouse leaves the target : hide the tip container
	handleMouseOut: function(event){
		this.objTip.style.visibility = "hidden";
	}
	
});

document.observe('dom:loaded', function () { ANAEMA.tips = new ANAEMA.Anatips(); });