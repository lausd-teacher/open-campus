 var tables = new Array();
function arraySort(tableName, column, lineNumber, columnNumber) {
	var aTable = tables[tableName];
	var arrayToSort;
	var array;
	var reverse = 0;
	if (aTable) {
		array = aTable[0];
		arrayToSort = new Array(lineNumber);
		for (i=0;i<lineNumber;i++) {
			arrayToSort[i] = new Array(2);
			arrayToSort[i][0] = array[i][column];
			arrayToSort[i][1] = i;				
		}
		reverse = 1 - aTable[1];
		aTable[1] = reverse;
	} else {
		array = new Array(lineNumber);
		arrayToSort = new Array(lineNumber);
		for (i=0;i<lineNumber;i++) {	
			array[i] = new Array(columnNumber);
			for (j=0;j<columnNumber;j++) {			
				obj = document.getElementById("t" + tableName + "l" + (i+1) +"c" + j);				
				array[i][j] = obj.innerHTML;
			}
			array[i][columnNumber] = obj.parentNode.parentNode.onmouseover;
			array[i][columnNumber+1] = obj.parentNode.parentNode.onmouseout;			
			
			arrayToSort[i] = new Array(2);
			arrayToSort[i][0] = array[i][column];
			arrayToSort[i][1] = i;		
	
			aTable = new Array(2);
			aTable[0] = array;
			aTable[1] = 0;
			tables[tableName] = aTable;
		}
	}
	arrayToSort.sort(arrayCompare);
	if (reverse) {
		arrayToSort.reverse();
	}

	for (i=0;i<lineNumber;i++) {
		goodLine = arrayToSort[i][1];
		for (j=0;j<columnNumber;j++) {
			document.getElementById("t" + tableName + "l" + (i+1) +"c" + j).innerHTML = array[goodLine][j];
		}
		document.getElementById("t" + tableName + "l" + (i+1) +"c" + 0).parentNode.parentNode.onmouseover = array[goodLine][columnNumber];
		document.getElementById("t" + tableName + "l" + (i+1) +"c" + 0).parentNode.parentNode.onmouseout = array[goodLine][columnNumber+1];
	}
}

// sort functions
function arrayCompare(e1,e2) {
	return e1[0] < e2[0] ? -1 : (e1[0] == e2[0] ? 0 : 1);

}