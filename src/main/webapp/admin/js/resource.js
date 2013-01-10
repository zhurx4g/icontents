var check = false;

function dis(){check = true;}

var DOM = 0, MS = 0, OP = 0, b = 0;

function CheckBrowser(){
	if (b == 0){
		if (window.opera) OP = 1;
		// Moz or Netscape
		if(document.getElementById) DOM = 1;
		// Micro$oft
		if(document.all && !OP) MS = 1;
		b = 1;
	}
}

function selrow (element,checkBoxId, i){
	var checkBox = jQuery('#'+checkBoxId);
	if (i==0){
		if (checkBox.attr('checked')==true) element.className='mousechecked';
		else element.className='mousein';
	}
	
	else if (i==1){
		if (checkBox.attr('checked')==true) element.className='mousechecked';
		else element.className='mouseout';
	}
	
	else if (i==2){
		if (checkBox.attr('checked')==true) element.className='mousein';
		else element.className='mousechecked';
		if(checkBox.attr('checked')==true){
			checkBox.attr('checked','');
		}else{
			checkBox.attr('checked',true);
		}
	}
}

function filter (begriff){
	var suche = begriff.value.toLowerCase();
	var table = document.getElementById("filetable");
	var ele;
	for (var r = 1; r < table.rows.length; r++){
		ele = table.rows[r].cells[1].innerHTML.replace(/<[^>]+>/g,"");
		if (ele.toLowerCase().indexOf(suche)>=0 )
			table.rows[r].style.display = '';
		else table.rows[r].style.display = 'none';
  	}
}
	
function AllFiles(){
	for(var x=0;x < document.FileList.elements.length;x++){
		var y = document.FileList.elements[x];
		var ytr = y.parentNode.parentNode;
		var check = document.FileList.selall.checked;
		if(y.name == 'selfile' && ytr.style.display != 'none'){
			if (y.disabled != true){
				y.checked = check;
				if (y.checked == true) ytr.className = 'checked';
				else ytr.className = 'mouseout';
			}
		}
	}
}

function shortKeyHandler(_event){
	if (!_event) _event = window.event;
	if (_event.which) {
		keycode = _event.which;
	} else if (_event.keyCode) {
		keycode = _event.keyCode;
	}
	var t = document.getElementById("text_Dir");
	//z
	if (keycode == 122){
		document.getElementById("but_Zip").click();
	}
	//r, F2
	else if (keycode == 113 || keycode == 114){
		var path = prompt("Please enter new filename", "");
		if (path == null) return;
		t.value = path;
		document.getElementById("but_Ren").click();
	}
	//c
	else if (keycode == 99){
		var path = prompt("Please enter filename", "");
		if (path == null) return;
		t.value = path;
		document.getElementById("but_NFi").click();
	}
	//d
	else if (keycode == 100){
		var path = prompt("Please enter directory name", "");
		if (path == null) return;
		t.value = path;
		document.getElementById("but_NDi").click();
	}
	//m
	else if (keycode == 109){
		var path = prompt("Please enter move destination", "");
		if (path == null) return;
		t.value = path;
		document.getElementById("but_Mov").click();
	}
	//y
	else if (keycode == 121){
		var path = prompt("Please enter copy destination", "");
		if (path == null) return;
		t.value = path;
		document.getElementById("but_Cop").click();
	}
	//l
	else if (keycode == 108){
		document.getElementById("but_Lau").click();
	}
	//Del
	else if (keycode == 46){
		document.getElementById("but_Del").click();
	}
}

function popUp(URL){
	fname = document.getElementsByName("myFile")[0].value;
	if (fname != "")
		window.open(URL+"?first&uplMonitor="+encodeURIComponent(fname),"","width=400,height=150,resizable=yes,depend=yes")
}

document.onkeypress = shortKeyHandler;
