var isPass = false;

String.prototype.trim = function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

function recommendedFn(node, prompt){
    if (confirm(prompt)) {
        window.location = node.getAttribute("href");
    }
    else {
        return false;
    }
}

function deleteFn(node, prompt){
    if (confirm(prompt)) {
        window.location = node.getAttribute("href");
    }
    else {
        return false;
    }
}

function selectComboList(comboListId, categoryValue){
    var length = document.getElementById(comboListId).options.length;
    for (var i = 0; i < length; i++) {
        if (document.getElementById(comboListId).options[i].value == categoryValue) {
            document.getElementById(comboListId).selectedIndex = i;
            document.getElementById(comboListId).options[i].selected == true;
            return;
        }
    }
}

function hideDom(id){
    $("#" + id).hide();
}

function checkLength(id, length){
    var value = $("#" + id).val();
    var varLen = value ? value.length : 0;
    if (length >= varLen) {
        $("#" + id + "WarnningTip").html("还可以输入" + (length - varLen) + "字");
        return true;
    }
    else {
        $("#" + id + "WarnningTip").html("<font style='color:#C00'>超出" + (varLen - length) + "字</font>");
        isPass = false;
        return false;
    }
}

function checkNull(id){
    var input = $("#" + id);
    if (!input.val() || input.val().length == 0) {
        $("#" + id + "WarnningTip").html("<font style='color:#C00'>不可为空</font>");
        isPass = false;
        return false;
    } else {
        $("#" + id + "WarnningTip").html("");
        return true;
    }
    return true;
}

function checkNullAndLength(id, length){
    return checkNull(id) && checkLength(id, length);
}

function initInput(id, value){
    if (value) {
        $("#" + id).val(value);
    }
    else {
        $("#" + id).val("");
    }
}

function initTextInputByLength(id, length, value){
    initInput(id, value);
    checkLength(id, length);
}

function resetPosition(){
    var target = $("#pop");
    target.css({
        position: "absolute",
        left: ($(window).width() - target.outerWidth()) / 2,
        top: $(window.parent).scrollTop() + ($(window.parent).height() - target.outerHeight()) / 2 - 90
    });
}

function showPopDiv() {
    resetPosition();
    $("#pop").show();
}

function resetPageHeight(){
 	var leftContent = window.parent.document.getElementById("colR_inner");
 	var mainContent = window.parent.document.getElementById("main");
 	if(document.body.scrollHeight<700){
 		if(leftContent!=null)leftContent.style.height = "700px";
 		if(mainContent!=null)mainContent.style.height = "700px";
	}else{
		if(leftContent!=null)leftContent.style.height = (document.body.scrollHeight)+"px";
		if(mainContent!=null)mainContent.style.height = (document.body.scrollHeight)+"px";	
	}
}