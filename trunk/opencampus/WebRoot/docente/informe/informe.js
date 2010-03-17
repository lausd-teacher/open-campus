try{
wFORMS.sameNameClone= true;
wf.arrMsg[0] = "Wi";
wf.arrMsg[2] = "Wi";
}catch(e){}
function pulsar(obj) {

	var lines = obj.value.split('\n') || [];
	var newRows = lines.length; // +1 si hay problemas
	var oldRows = obj.rows;
	
	if(!xGetElementById('autogrowTMP')){
		var span = xCreate('div');
		span.id = 'autogrowTMP';
		span.style.top  = "-20px";
		span.style.left  = "-99999px";
		span.style.whiteSpace = 'nowrap';
		span.style.position = "absolute";
		span.style.fontFamily = obj.style.fontFamily;
		span.style.fontSize = obj.style.fontSize;
		span.style.padding = span.style.padding;
		span.style.visibility = "hidden";
		document.body.appendChild(span);
	}
	
	for (var i = 0; i < lines.length; i++){ 
		var line = lines[i];
		
		xInnerHtml('autogrowTMP',line.replace(/\s/g,'&nbsp;'));
		if(xWidth('autogrowTMP') >= (xWidth(obj)-5)){
			
			/*
			var phrase = xInnerHtml('autogrowTMP').split('&nbsp;');
			xInnerHtml('autogrowTMP','');
			var strFill = '';
			for(var index=0; index<phrase.length; index++) {
				xInnerHtml('autogrowTMP',xInnerHtml('autogrowTMP')+phrase[index]+'&nbsp;');
				if(xWidth('autogrowTMP') >= (xWidth(obj)-5)){
					strFill += phrase[index];
					xInnerHtml('autogrowTMP','');
				}
			}
			xInnerHtml('autogrowTMP',line.replace(/\s/g,'&nbsp;')+strFill);
			*/
			
			newRows += Math.floor(xWidth('autogrowTMP') / (xWidth(obj)-5));
		}
		
		//if (line.length >= obj.cols) newRows += Math.floor(line.length / obj.cols);
	}
	if (newRows > obj.rows) obj.rows = newRows;
	if (newRows < obj.rows) obj.rows = Math.max(1, newRows);

}

function expandir(){
	var areas = document.getElementsByTagName('textarea');
	for(var i=0; i<areas.length; i++) {
		pulsar(areas[i]); 
	}	
}
function resize() {
	if(xGetElementById('wysiwygobservacion')){
		xWidth('wysiwygobservacion',xClientWidth()-74);
	}
	if(xGetElementById('toolbarWY1')){
		xWidth('toolbarWY1',xClientWidth()-70);
	}
}
