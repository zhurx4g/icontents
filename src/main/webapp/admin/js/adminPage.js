var totalCount = 0;

var componentCount_0 = 0;
var componentCount_1 = 0;
var componentCount_2 = 0;
var componentCount_3 = 0;
var componentCount_4 = 0;

var headerSeqence = 0;
var leftSeqence = 0;
var rightSeqence = 0;
var bodySeqence = 0;
var footerSeqence = 0;

var validator;

/*
tinyMCE.init({
	theme : "advanced",
	plugins : "fullscreen",
    theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
    theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,search,replace,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,forecolor,backcolor",
    theme_advanced_buttons3 : "hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,fullscreen",

	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_statusbar_location : "bottom",
	force_p_newlines : false
});
*/

function caculateOrder(){
	totalCount = 0;
    	    
    headerSeqence = 0;
    bodySeqence = 0;
    footerSeqence = 0;
    
    var componentHeaderList = jQuery("#componentList0").find("table[name='fieldsTable']");
    componentHeaderList.each(function(){
    	var componentTypeObj = jQuery(this).find("input[id*='componentType']");
    	var componentSequenceObj = jQuery(this).find("input[id*='sequence']");
    	var componentPositionObj = jQuery(this).find("input[id*='position']");
    	
    	componentTypeObj.attr('name','componentType'+totalCount);
    	componentSequenceObj.attr('name','sequence'+totalCount);
    	componentPositionObj.attr('name','position'+totalCount);

    	componentSequenceObj.val(headerSeqence);
    	
        var componentType = componentTypeObj.val();
        if(componentType=='LogoComponent'){
			var componentImageObj = jQuery(this).find("input[id*='image']");
			
			componentImageObj.attr('name','image'+totalCount);
        }else if(componentType=='NavComponent'){
        }else if(componentType=='HtmlComponent'){
		    var componentTitleObj = jQuery(this).find("input[id*='title']");
		    componentTitleObj.attr('name','title'+totalCount);
		    
			var componentContentObj = jQuery(this).find("textarea[id*='content']");
			componentContentObj.attr('name','content'+totalCount);
			
			if(componentContentObj.css('display')=='none'){
				componentContentObj.text(KE.html(componentContentObj.attr('id')));
			}
        }else if(componentType=='TextComponent'){
        	var componentContentObj = jQuery(this).find("textarea[id*='content']");
        	componentContentObj.attr('name','content'+totalCount);
        	
        	if(componentContentObj.css('display')=='none'){
        		componentContentObj.text(KE.html(componentContentObj.attr('id')));
        	}
        }else{
            alert('['+componentType+']组件不应该出现在这个区域');
        }
        headerSeqence=headerSeqence+1;
        totalCount=totalCount+1;
    });
    
    var componentLeftList = jQuery("#componentList1").find("table[name='fieldsTable']");
    componentLeftList.each(function(){
        var componentTypeObj = jQuery(this).find("input[id*='componentType']");
        var componentSequenceObj = jQuery(this).find("input[id*='sequence']");
        var positionObj = jQuery(this).find("input[id*='position']");
        
        componentTypeObj.attr('name','componentType'+totalCount);
        componentSequenceObj.attr('name','sequence'+totalCount);
        positionObj.attr('name','position'+totalCount);

        componentSequenceObj.val(leftSeqence);
        
        var componentType = jQuery(this).find("input[id*='componentType']").val();
        if(componentType=='CatalogComponent'){
        	var componentTitleObj = jQuery(this).find("input[id*='title']");
        	componentTitleObj.attr('name','title'+totalCount);
        	
        	var componentContentObj = jQuery(this).find("input[id*='content']");
        	componentContentObj.attr('name','content'+totalCount);
        	
        	var componentWidthObj = jQuery(this).find("input[id*='width']");
        	componentWidthObj.attr('name','width'+totalCount);
        	
        	var componentHeightObj = jQuery(this).find("input[id*='height']");
        	componentHeightObj.attr('name','height'+totalCount);
        }else if(componentType=='MapComponent'){
        	updateMap(this,totalCount);
        }else if(componentType=='ArticleListComponent'){
		    var componentTitleObj = jQuery(this).find("input[id*='title']");
		    componentTitleObj.attr('name','title'+totalCount);
			
			var componentContentObj = jQuery(this).find("input[id*='content']");
			componentContentObj.attr('name','content'+totalCount);
        }else if(componentType=='HtmlComponent'){
			var componentContentObj = jQuery(this).find("textarea[id*='content']");
			componentContentObj.attr('name','content'+totalCount);
			
		    var componentTitleObj = jQuery(this).find("input[id*='title']");
		    componentTitleObj.attr('name','title'+totalCount);
		    
			if(componentContentObj.css('display')=='none'){
				componentContentObj.text(KE.html(componentContentObj.attr('id')));
			}
        }else if(componentType=='TextComponent'){
        	var componentContentObj = jQuery(this).find("textarea[id*='content']");
        	componentContentObj.attr('name','content'+totalCount);
        	
        	if(componentContentObj.css('display')=='none'){
        		componentContentObj.text(KE.html(componentContentObj.attr('id')));
        	}
        }else{
            alert('['+componentType+']组件不应该出现在这个区域');
        }
        leftSeqence=leftSeqence+1;
        totalCount=totalCount+1;
    });
	
    var componentRightList = jQuery("#componentList2").find("table[name='fieldsTable']");
    componentRightList.each(function(){
        var componentTypeObj = jQuery(this).find("input[id*='componentType']");
        var componentSequenceObj = jQuery(this).find("input[id*='sequence']");
        var positionObj = jQuery(this).find("input[id*='position']");
        
        componentTypeObj.attr('name','componentType'+totalCount);
        componentSequenceObj.attr('name','sequence'+totalCount);
        positionObj.attr('name','position'+totalCount);

        componentSequenceObj.val(rightSeqence);
        
        var componentType = jQuery(this).find("input[id*='componentType']").val();
        if(componentType=='CatalogComponent'){
        }else if(componentType=='MapComponent'){
        	updateMap(this,totalCount);
        }else if(componentType=='ArticleListComponent'){
		    var componentTitleObj = jQuery(this).find("input[id*='title']");
		    componentTitleObj.attr('name','title'+totalCount);
			
			var componentContentObj = jQuery(this).find("input[id*='content']");
			componentContentObj.attr('name','content'+totalCount);
        }else if(componentType=='HtmlComponent'){
		    var componentTitleObj = jQuery(this).find("input[id*='title']");
		    componentTitleObj.attr('name','title'+totalCount);
		    
			var componentContentObj = jQuery(this).find("textarea[id*='content']");
			componentContentObj.attr('name','content'+totalCount);
			
			if(componentContentObj.css('display')=='none'){
				componentContentObj.text(KE.html(componentContentObj.attr('id')));
			}
        }else if(componentType=='TextComponent'){
        	var componentContentObj = jQuery(this).find("textarea[id*='content']");
        	componentContentObj.attr('name','content'+totalCount);
        	
        	if(componentContentObj.css('display')=='none'){
        		componentContentObj.text(KE.html(componentContentObj.attr('id')));
        	}
        }else{
            alert('['+componentType+']组件不应该出现在这个区域');
        }
        rightSeqence=rightSeqence+1;
        totalCount=totalCount+1;
    });
	
    var componentBodyList = jQuery("#componentList4").find("table[name='fieldsTable']");
    componentBodyList.each(function(){
        var componentTypeObj = jQuery(this).find("input[id*='componentType']");
        var componentSequenceObj = jQuery(this).find("input[id*='sequence']");
        var positionObj = jQuery(this).find("input[id*='position']");
        
        componentTypeObj.attr('name','componentType'+totalCount);
        componentSequenceObj.attr('name','sequence'+totalCount);
        positionObj.attr('name','position'+totalCount);

        componentSequenceObj.val(bodySeqence);
        
        var componentType = jQuery(this).find("input[id*='componentType']").val();
        if(componentType=='FlashComponent'){
        	updateFlash(this,totalCount);
        }else if(componentType=='MapComponent'){
        	updateMap(this,totalCount);
        }else if(componentType=='SiteMapComponent'){
        }else if(componentType=='TabsComponent'){
        }else if(componentType=='LicenseComponent'){
        }else if(componentType=='CatalogArticleListComponent'){
        	var componentCatalogObj = jQuery(this).find("select[id*='catalogId']");
        	componentCatalogObj.attr('name','catalogId'+totalCount);
        }else if(componentType=='HtmlComponent'){
		    var componentTitleObj = jQuery(this).find("input[id*='title']");
		    componentTitleObj.attr('name','title'+totalCount);
		    
			var componentContentObj = jQuery(this).find("textarea[id*='content']");
			componentContentObj.attr('name','content'+totalCount);
			
			if(componentContentObj.css('display')=='none'){
				componentContentObj.text(KE.html(componentContentObj.attr('id')));
			}
        }else{
            alert('['+componentType+']组件不应该出现在这个区域');
        }
        bodySeqence=bodySeqence+1;
        totalCount=totalCount+1;
    });
    
    var componentFooterList = jQuery("#componentList3").find("table[name='fieldsTable']");
    componentFooterList.each(function(){
        var componentTypeObj = jQuery(this).find("input[id*='componentType']");
        var componentSequenceObj = jQuery(this).find("input[id*='sequence']");
        var positionObj = jQuery(this).find("input[id*='position']");
        
        componentTypeObj.attr('name','componentType'+totalCount);
        componentSequenceObj.attr('name','sequence'+totalCount);
        positionObj.attr('name','position'+totalCount);

        componentSequenceObj.val(footerSeqence);
    
        var componentType = jQuery(this).find("input[id*='componentType']").val();
        if(componentType=='FriendLinkComponent'){
        }else if(componentType=='CopyRightComponent'){
		}else if(componentType=='AboutComponent'){
        }else if(componentType=='ArticleListComponent'){
		    var componentTitleObj = jQuery(this).find("input[id*='title']");
		    componentTitleObj.attr('name','title'+totalCount);
			
			var componentContentObj = jQuery(this).find("input[id*='content']");
			componentContentObj.attr('name','content'+totalCount);
        }else if(componentType=='QuickNavComponent'){
        	var componentTitleObj = jQuery(this).find("input[id*='title']");
        	componentTitleObj.attr('name','title'+totalCount);
        	
        	var componentContentObj = jQuery(this).find("input[id*='content']");
        	componentContentObj.attr('name','content'+totalCount);
        }else if(componentType=='HtmlComponent'){
		    var componentTitleObj = jQuery(this).find("input[id*='title']");
		    componentTitleObj.attr('name','title'+totalCount);
		    
			var componentContentObj = jQuery(this).find("textarea[id*='content']");
			componentContentObj.attr('name','content'+totalCount);
			
			if(componentContentObj.css('display')=='none'){
				componentContentObj.text(KE.html(componentContentObj.attr('id')));
			}
        }else{
            alert('['+componentType+']组件不应该出现在这个区域');
        }
        footerSeqence=footerSeqence+1;
        totalCount=totalCount+1;
    });
    jQuery('#componentListLength').val(totalCount); 
}

function initTool() {
	//eval('(' + myJSONtext + ')');
    var data = eval('(' + jQuery(this).attr('data') + ')');
    data['totalCount']=totalCount;
    
    var template = jQuery(this).attr('template');
    var layout = data['position'];
    
    totalCount=totalCount+1;
    if(layout=="0"){    	
    	// attach the template
    	jQuery("#emptyComponent_0_"+componentCount_0).setTemplateElement("template" + template);
    	jQuery("#emptyComponent_0_"+componentCount_0).processTemplate(data);
    	
    	componentCount_0=componentCount_0+1;
    	jQuery('<div id="emptyComponent_0_'+componentCount_0+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList0');
    }else if(layout=="1"){
    	// attach the template
        jQuery("#emptyComponent_1_"+componentCount_1).setTemplateElement("template" + template);
        jQuery("#emptyComponent_1_"+componentCount_1).processTemplate(data);
        
        componentCount_1=componentCount_1+1;
        jQuery('<div id="emptyComponent_1_'+componentCount_1+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList1');
    }else if(layout=="2"){
        jQuery("#emptyComponent_2_"+componentCount_2).setTemplateElement("template" + template);
        jQuery("#emptyComponent_2_"+componentCount_2).processTemplate(data);
		
        componentCount_2=componentCount_2+1;
        jQuery('<div id="emptyComponent_2_'+componentCount_2+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList2');
    }else if(layout=="3"){
    }else if(layout=="4"){
    	jQuery("#emptyComponent_4_"+componentCount_4).setTemplateElement("template" + template);
    	jQuery("#emptyComponent_4_"+componentCount_4).processTemplate(data);
    	
    	componentCount_4=componentCount_4+1;
    	jQuery('<div id="emptyComponent_4_'+componentCount_4+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList4');
    }
	resetPageHeight();
}
function initAction(){
    
    <!--顶部工具栏-->
    jQuery("#btnHeaderLogo").click(initTool);
    jQuery("#btnHeaderNav").click(initTool);
    jQuery("#btnHeaderHtml").click(initTool);
    jQuery("#btnHeaderTextarea").click(initTool);

        
    <!--左侧工具栏-->
    jQuery("#btnLeftCatalog").click(function(){
	    var data = {position:1};
        jQuery("#emptyComponent_1_"+componentCount_1).setTemplateElement("templateCatalogComponent");
        jQuery("#emptyComponent_1_"+componentCount_1).processTemplate(data);
        componentCount_1=componentCount_1+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_1_'+componentCount_1+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList1');
		resetPageHeight();
	});
    jQuery("#btnLeftHtml").click(function(){
	    var data = {position:1,content:''};
		data['totalCount']=totalCount;
		
        jQuery("#emptyComponent_1_"+componentCount_1).setTemplateElement("templateHtmlComponent");
        jQuery("#emptyComponent_1_"+componentCount_1).processTemplate(data);
		
        componentCount_1=componentCount_1+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_1_'+componentCount_1+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList1');
		resetPageHeight();
	});
    jQuery("#btnLeftArticleList").click(function(){
		var data = {position:1,sequence:0,content:''};
        jQuery("#emptyComponent_1_"+componentCount_1).setTemplateElement("templateArticleListComponent");
        jQuery("#emptyComponent_1_"+componentCount_1).processTemplate(data);
        componentCount_1=componentCount_1+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_1_'+componentCount_1+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList1');
		resetPageHeight();
	});
    jQuery("#btnLeftMap").click(function(){
		var data = {position:1,sequence:0,content:'北京',title:'公司地址',width:235,height:235};
        jQuery("#emptyComponent_1_"+componentCount_1).setTemplateElement("templateMapComponent");
        jQuery("#emptyComponent_1_"+componentCount_1).processTemplate(data);
        componentCount_1=componentCount_1+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_1_'+componentCount_1+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList1');
		resetPageHeight();
	});
    <!--右侧工具栏-->
    jQuery("#btnRightCatalog").click(function(){
	    var data = {position:2};
        jQuery("#emptyComponent_2_"+componentCount_2).setTemplateElement("templateCatalogComponent");
        jQuery("#emptyComponent_2_"+componentCount_2).processTemplate(data);
        componentCount_2=componentCount_2+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_2_'+componentCount_2+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList2');
		resetPageHeight();
	});
    jQuery("#btnRightHtml").click(function(){
	    var data = {position:2,content:''};
		data['totalCount']=totalCount;
		
        jQuery("#emptyComponent_2_"+componentCount_2).setTemplateElement("templateHtmlComponent");
        jQuery("#emptyComponent_2_"+componentCount_2).processTemplate(data);
		
        componentCount_2=componentCount_2+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_2_'+componentCount_2+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList2');
		resetPageHeight();
	});
    jQuery("#btnRightArticleList").click(function(){
		var data = {position:2,sequence:0,content:''};
        jQuery("#emptyComponent_2_"+componentCount_2).setTemplateElement("templateArticleListComponent");
        jQuery("#emptyComponent_2_"+componentCount_2).processTemplate(data);
        componentCount_2=componentCount_2+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_2_'+componentCount_2+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList2');
		resetPageHeight();
	});
    jQuery("#btnRightMap").click(function(){
		var data = {position:2,sequence:0,content:'北京',title:'公司地址',width:235,height:235};
        jQuery("#emptyComponent_2_"+componentCount_2).setTemplateElement("templateMapComponent");
        jQuery("#emptyComponent_2_"+componentCount_2).processTemplate(data);
        componentCount_2=componentCount_2+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_2_'+componentCount_2+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList2');
		resetPageHeight();
	});
    <!--中间工具栏-->
    jQuery("#btnBodyFlash").click(function(){
	    var data = {position:4,images:'5个图片地址,逗号分隔',content:'5个链接地址,逗号分隔'};
	    data['totalCount']=totalCount;
        jQuery("#emptyComponent_4_"+componentCount_4).setTemplateElement("templateFlashComponent");
        jQuery("#emptyComponent_4_"+componentCount_4).processTemplate(data);
        componentCount_4=componentCount_4+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_4_'+componentCount_4+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList4');
		resetPageHeight();
	});
    jQuery("#btnBodyHtml").click(function(){
	    var data = {position:4,content:''};
		
		data['totalCount']=totalCount;
        
		jQuery("#emptyComponent_4_"+componentCount_4).setTemplateElement("templateHtmlComponent");
        jQuery("#emptyComponent_4_"+componentCount_4).processTemplate(data);
		
        componentCount_4=componentCount_4+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_4_'+componentCount_4+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList4');
		resetPageHeight();
	});
    jQuery("#btnBodyMap").click(function(){
        var data = {position:4,sequence:0,content:'北京',title:'公司地址',width:710,height:710};
		
		data['totalCount']=totalCount;
        
		jQuery("#emptyComponent_4_"+componentCount_4).setTemplateElement("templateMapComponent");
        jQuery("#emptyComponent_4_"+componentCount_4).processTemplate(data);
		
        componentCount_4=componentCount_4+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_4_'+componentCount_4+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList4');
		resetPageHeight();
	});
    jQuery("#btnBodySiteMap").click(function(){
        var data = {position:4,sequence:0};
		
		data['totalCount']=totalCount;
        
		jQuery("#emptyComponent_4_"+componentCount_4).setTemplateElement("templateSiteMapComponent");
        jQuery("#emptyComponent_4_"+componentCount_4).processTemplate(data);
		
        componentCount_4=componentCount_4+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_4_'+componentCount_4+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList4');
		resetPageHeight();
	});
    jQuery("#btnBodyTabs").click(function(){
        var data = {position:4,sequence:0};
		
		data['totalCount']=totalCount;
        
		jQuery("#emptyComponent_4_"+componentCount_4).setTemplateElement("templateTabsComponent");
        jQuery("#emptyComponent_4_"+componentCount_4).processTemplate(data);
		
        componentCount_4=componentCount_4+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_4_'+componentCount_4+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList4');
		resetPageHeight();
	});
    jQuery("#btnBodyLicense").click(function(){
        var data = {position:4,sequence:0};
		
		data['totalCount']=totalCount;
        
		jQuery("#emptyComponent_4_"+componentCount_4).setTemplateElement("templateLicenseComponent");
        jQuery("#emptyComponent_4_"+componentCount_4).processTemplate(data);
		
        componentCount_4=componentCount_4+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_4_'+componentCount_4+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList4');
		resetPageHeight();
	});
    jQuery("#btnBodyCatalogArticleList").click(initTool);
    <!--底部工具栏-->
    //底部友情链接
    jQuery("#btnFooterFriendLink").click(function(){
		var data = {};
        jQuery("#emptyComponent_3_"+componentCount_3).setTemplateElement("templateFriendLinkComponent");
        jQuery("#emptyComponent_3_"+componentCount_3).processTemplate(data);
        componentCount_3=componentCount_3+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_3_'+componentCount_3+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList3');
		resetPageHeight();
    });
    //底部公司版权
    jQuery("#btnFooterCopyRight").click(function(){
		var data = {};
        jQuery("#emptyComponent_3_"+componentCount_3).setTemplateElement("templateCopyRightComponent");
        jQuery("#emptyComponent_3_"+componentCount_3).processTemplate(data);
        componentCount_3=componentCount_3+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_3_'+componentCount_3+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList3');
		resetPageHeight();
    });
    //底部公司版权
    jQuery("#btnFooterAbout").click(function(){
		var data = {};
        jQuery("#emptyComponent_3_"+componentCount_3).setTemplateElement("templateAboutComponent");
        jQuery("#emptyComponent_3_"+componentCount_3).processTemplate(data);
        componentCount_3=componentCount_3+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_3_'+componentCount_3+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList3');
		resetPageHeight();
    });
    jQuery("#btnFooterHtml").click(function(){
		var data = {position:3,content:''};
		
		data['totalCount']=totalCount;
        
		jQuery("#emptyComponent_3_"+componentCount_3).setTemplateElement("templateHtmlComponent");
        jQuery("#emptyComponent_3_"+componentCount_3).processTemplate(data);
		
        componentCount_3=componentCount_3+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_3_'+componentCount_3+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList3');
		resetPageHeight();
    });
    jQuery("#btnFooterArticleList").click(function(){
		var data = {position:3,sequence:0,content:''};
        jQuery("#emptyComponent_3_"+componentCount_3).setTemplateElement("templateArticleListComponent");
        jQuery("#emptyComponent_3_"+componentCount_3).processTemplate(data);
        componentCount_3=componentCount_3+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_3_'+componentCount_3+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList3');
		resetPageHeight();
    });
    jQuery("#btnFooterQuickNav").click(function(){
		var data = {position:3,sequence:0,content:''};
        jQuery("#emptyComponent_3_"+componentCount_3).setTemplateElement("templateQuickNavComponent");
        jQuery("#emptyComponent_3_"+componentCount_3).processTemplate(data);
        componentCount_3=componentCount_3+1;
        totalCount=totalCount+1;
        jQuery('<div id="emptyComponent_3_'+componentCount_3+'" style="border: 0px;width:100%;"></div>').appendTo('#componentList3');
		resetPageHeight();
    });
}
function removeHTMLArea(){
	jQuery("textarea[id*='content']").each(
			function (){
				/*if (tinyMCE.getInstanceById(jQuery(this).attr('id')))
				{
					tinyMCE.execCommand('mceFocus', false, jQuery(this).attr('id'));
					tinyMCE.execCommand('mceRemoveControl', false,jQuery(this).attr('id'));
				}*/
				try 
				{ 
					KE.remove(jQuery(this).attr('id'));
				}catch (e) 
				{ 
				
				}
			}
	);	
}
function onTextareafocus(element){
	removeHTMLArea();
	//tinyMCE.execCommand('mceAddControl', false, element.id);
	
	var editor_id = element.id;
	KE.init({'id': editor_id,afterCreate : function(id) {
				resetPageHeight();
				KE.util.focus(id);
			}
		}
	);

	KE.create(editor_id);
	resetPageHeight();
}

function onTextareaBlur(element){
	//tinyMCE.execCommand('mceFocus', false, element.id);
	//tinyMCE.execCommand('mceRemoveControl', false,element.id);
	//resetPageHeight();
}

function updateFlash(element,totalCount){
	var componentImagesObj = jQuery(element).find("input[id*='images']");
	componentImagesObj.attr('name','images'+totalCount);
	
	var componentContentObj = jQuery(element).find("input[id*='content']");
	componentContentObj.attr('name','content'+totalCount);
}
function updateMap(element,totalCount){
	var componentTitleObj = jQuery(element).find("input[id*='title']");
	componentTitleObj.attr('name','title'+totalCount);
	
	var componentContentObj = jQuery(element).find("input[id*='content']");
	componentContentObj.attr('name','content'+totalCount);
	
	var componentWidthObj = jQuery(element).find("input[id*='width']");
	componentWidthObj.attr('name','width'+totalCount);
	
	var componentHeightObj = jQuery(element).find("input[id*='height']");
	componentHeightObj.attr('name','height'+totalCount);
}