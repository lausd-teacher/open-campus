
jQuery.jPrintArea = function (el) {
	var iframe = document.createElement("IFRAME");
	var doc = null;
	$(iframe).attr("style", "position:absolute;width:0px;height:0px;left:-500px;top:-500px;");
	document.body.appendChild(iframe);
	doc = iframe.contentWindow.document;
	var titles = window.document.getElementsByTagName("title");
	for (var j = 0; j < titles.length; j = j + 1) {
		doc.write("<title>" + titles[j].innerHTML + "</title>");
	}
	var links = window.document.getElementsByTagName("link");
	for (var i = 0; i < links.length; i = i + 1) {
		if (links[i].rel.toLowerCase() == "stylesheet") {
			doc.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"" + links[i].href + "\"></link>");
		}
	}
	if (el === null) {
		el = window.document;
	}
	doc.write("<div class=\"" + $(el).attr("class") + "\" style=\"height:" + $(el).height() + "px\" >" + $(el).html() + "</div>");
	doc.close();
	iframe.contentWindow.focus();
	iframe.contentWindow.print();
	
	//wait(1);
	//alert('Printing...');
	//document.body.removeChild(iframe);
//	window.setInterval(function () {
//		document.body.removeChild(iframe);
//	}, 400);
};

