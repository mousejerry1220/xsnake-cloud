function BeeGrid(option) {
	var editRowId = null;
	var grid_selector = option.grid;
	var pager_selector = option.pager;
	var _this = this;
	var selectedList = [];
	var selected = null;
	
	var beforeDataMap = {};
	var changeList = [];
	var deleteList = [];
	var addList = [];
	var currentCell = null;
	
	this.getSelectedList = function(){
		return selectedList;
	}

	this.setSelectedList = function(initSelectedList){
		selectedList = initSelectedList;
	}
	
	this.getSelected = function(){
		return selected;
	}
	
//	this.submitChange = function(){
//		var json = JSON.stringify(this.getChangeList());
//		$.ajax({
//			url:'#',
//			data:json,
//			type:'post',
//			success : myajaxSuccess,
//			error: myajaxError
//		})
//		beforeDataMap = {};
//		changeList = [];
//		deleteList = [];
//		addList = [];
//		currentCell = null;
//	}
	
	this.getChangeList = function(){
		var result = [];
		var list = changeList.unique3();
		$.each(list,function(i,e){
			var item = $(grid_selector).jqGrid('getRowData',e);
			if(item.id){
				result.push(item);
			}
		})
		$.each(addList,function(i,e){
			var item = $(grid_selector).jqGrid('getRowData',e);
			if(item.id){
				result.push(item);
			}
		})
		$.each(deleteList,function(i,e){
			var item = {id:e,_act:'delete'};
			result.push(item);
		})
		return result;
	}

	this.delRowData = function(){
		var _selectedList = selectedList;
		selectedList = [];
		$.each(_selectedList,function(i,e){
			var rowid = e.id;
			var item = $(grid_selector).jqGrid('getRowData',rowid);
			$(grid_selector).jqGrid('delRowData',rowid);
			if(item['_act'] != "new"){
				item['_act'] = "delete";
				$(grid_selector).jqGrid('setRowData',rowid,item);
				deleteList.push(rowid);
			}
		})
		$(grid_selector).trigger("reloadGrid");
	}
	
	this.addRowData = function(){
		 var x = 9;       
	     var y = 0;
	     var randId = "";
	     for(var i=0;i<8;i++){
		     var rand = parseInt(Math.random() * (x - y + 1) + y);
		     randId = randId + rand;
	     }
		$(grid_selector).jqGrid('addRowData',randId,{_act:'new',id:randId},0,0);
		var item = $(grid_selector).jqGrid('getRowData',randId);
		item['_act'] = 'new';
		$(grid_selector).jqGrid('setRowData',randId,item);
		addList.push(randId);
	}
	
	this.init = function(){
		var parent_column = $(grid_selector).closest('[class*="col-"]');
		//resize to fit page size
		$(window).on('resize.jqGrid', function () {
			$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
			$("#"+gridId+"_title").html("<span style='margin-right: 20px'>"+option.caption+"</span>");
        	var captionElememt = $("<p style='margin-top: -4px; padding-left: 15px;'></p>"); 
    		if(newOption.buttons && newOption.buttons.length > 0){
    			$.each(newOption.buttons,function(i,e){
    			var btn = $("<button type='button' id='"+e.id+"' style='margin-left: 2px; margin-right: 2px;' class='btn btn-info btn-xs btn-bold'> <i class='ace-icon fa fa-floppy-o bigger-120 blue'></i> "+e.name+" </button>")
    				btn.click(function(){
    					$("#load_"+gridId).show();
    					$(grid_selector).trigger('blur');
    					e.handler();
    					$("#load_"+gridId).hide();
    				});
    				$("#"+gridId+"_title").append(btn);
    			});
    		}
	    })
	    
		//resize on sidebar collapse/expand
		$(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
			if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
				//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
				setTimeout(function() {
					$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
				}, 20);
			}
	    })
	    
	    var defaultOption = {
				datatype: "local",
				height: 250,
				viewrecords : true,
				rowNum:20,
				rowList:[10,20,30,40,50,100],
				pager : pager_selector,
				multiselect: true,
		        multiboxonly: false,
		        cellEdit:true,
				loadComplete : function() {
					var table = this;
					setTimeout(function(){
						updatePagerIcons(table);
					}, 0);
				},
				loadtext:"数据加载中，请稍后……",
	            pgtext:"当前第{0}页，共{1}页",
	            jsonReader : {
	                root : "message.list",
	                page : "message.pageCurrent",
	                total : "message.pageCount",
	                records : "message.count",
	                repeatitems : true
	            },
	            cellsubmit:"clientArray",
	            beforeEditCell:function(rowid,cellname,value,iRow,iCol){
	            	currentCell = {'rowid':rowid,'cellname':cellname,'value':value,'iRow':iRow};
	            	var cn = gridId + "_" + cellname;
	            	if(!beforeDataMap[rowid+"_"+cellname+"_change"] ){
	            		var a= $(grid_selector).jqGrid('getRowData',rowid);
	            		beforeDataMap[rowid+"_"+cellname+"_change"] = false;
	            		beforeDataMap[rowid+"_"+cellname] = value;
	            	}
	            },
	            afterSaveCell:function(rowid,cellname,value,iRow,iCol){
	            	debugger;
	            	currentCell == null;
	            	var cn = gridId+"_" + cellname;
	            	var oldValue = beforeDataMap[rowid+"_"+cellname];
	            	var item= $(grid_selector).jqGrid('getRowData',rowid);
	            	if(item['_act'] != "new"){
	            		item['_act'] = "update";
	            		changeList.push(rowid);
	            	}
	            	$(grid_selector).jqGrid('setRowData',rowid,item);
	            	if(oldValue == value){
	            		$(grid_selector).find("#"+rowid).find("[aria-describedby='"+cn+"']").css("background-color","");
	            		beforeDataMap[rowid+"_"+cellname+"_change"] = false;
	            	}else{
	            		$(grid_selector).find("#"+rowid).find("[aria-describedby='"+cn+"']").css("background-color","rgb(167, 236, 178)");
		            	beforeDataMap[rowid+"_"+cellname+"_change"] = true;
	            	}
	            },
	            ondblClickRow : function(rowid,iRow,iCol,e){
	             	
	            },
	    	    onSelectRow : function(rowid,status){
	                selected = $(grid_selector).jqGrid('getRowData',rowid);
	                if(status){
	                    var flag = true;
	                    $.each(selectedList,function(i,item){
	                        if(item && selected.id === item.id){
	                        	$.extend(item, selected);
	                            flag = false;
	                            return;
	                        }
	                    });
	                    if(flag){
	                        selectedList.push(selected);
	                    }
	                }else{
	                    $.each(selectedList,function(i,item){
	                        if(item && selected.id === item.id){
	                            selectedList.splice(i,1);
	                        }
	                    });
	                }
	            },gridComplete : function(){
	            	 $.each(selectedList,function(i,item){
	            		 $(grid_selector).jqGrid('setSelection',item.id);
	            	 })
	            }
			}
		
		var gridId = $(grid_selector)[0].id;
		//合并默认值和客户值
	    var newOption = $.extend({},defaultOption, option );
	    newOption.multiboxonly = false, //必须为false
		newOption.caption = "<div id='"+gridId+"_title' class='col-sm-12'></div>";
		newOption.colModel.push({name:'_act',hidden:true});
		$.each(newOption.colModel,function(i,e){
			if(e.edittype=="checkbox"){
				e.unformat = aceSwitch;
			}
			else if(_this.edittype[e.edittype]!= undefined){
				e.editoptions = $.extend({},e.editoptions ,_this.edittype[e.edittype] );
				e.edittype = "custom";
			}
		})
		
		$(grid_selector).jqGrid(newOption);
		$(window).triggerHandler('resize.jqGrid');//trigger window resize to make the grid get the correct size
		//任何位置被点击后判断点击位置是不是grid内部，如果不是内部则失去焦点，触发保存动作
		$(document.body).on('click',function(event){
            var target =event.srcElement ? event.srcElement :event.target;
            if($(grid_selector).find(target).length == 0){
            	if($(target).hasClass("edit-cell")|| $(target).is("select") 
                	|| ($(target).is("input")&&$(target).closest("td").attr("role")=="gridcell")){
                    return;
                }else{
                    $(grid_selector).trigger('blur')
                }
            }
        });
		 
		//失去焦点则保存单元格，这里不是真正的保存，当触发editCell后会触发afterSaveCell事件，是在afterSaveCell中做保存
		 $(grid_selector).blur(function(){
			 if(currentCell!=null){
				try{
					$(grid_selector).jqGrid('editCell',currentCell.iRow,currentCell.cellname);
				}catch(e){
					//$(grid_selector).jqGrid('editCell',currentCell.rowid,currentCell.cellname);
				};
			}
		 });
		
		//switch element when editing inline
		function aceSwitch(cellvalue, options, cell) {
			setTimeout(function() {
				$(cell).find('input[type=checkbox]').addClass(
						'ace ace-switch ace-switch-5').after(
						'<span class="lbl"></span>');
			}, 0);
		}

		//navButtons
		$(grid_selector).jqGrid('navGrid', pager_selector, { //navbar options
			edit : false,
			add : false,
			del : false,
			search : false,
			refresh : false,
			view : false,
		});
		
		//replace icons with FontAwesome icons like above
		function updatePagerIcons(table) {
			var replacement = {
				'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
				'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
				'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
				'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
			};
			$(
				'.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon')
				.each(
						function() {
							var icon = $(this);
							var $class = $.trim(icon.attr('class')
									.replace('ui-icon', ''));
							if ($class in replacement)
								icon.attr('class', 'ui-icon '
										+ replacement[$class]);
						})
		}

		$(document).one('ajaxloadstart.page', function(e) {
			$.jgrid.gridDestroy(grid_selector);
			$('.ui-jqdialog').remove();
		});
		$("#cb_"+gridId).click(function(){
			var status = $("#cb_"+gridId).prop("checked");
				var ids = $(grid_selector).jqGrid('getDataIDs');
				$.each(ids,function(i,e){
					var se = $(grid_selector).jqGrid('getRowData',e);
					if(status){
	                    var flag = true;
	                    $.each(selectedList,function(i,item){
	                        if(item && se.id === item.id){
	                            flag = false;
	                            return;
	                        }
	                    });
	                    if(flag){
	                        selectedList.push(se);
	                    }
	                }else{
	                    $.each(selectedList,function(i,item){
	                        if(item && se.id === item.id){
	                            selectedList.splice(i,1);
	                        }
	                    });
	                }
				});
		});
	}
	this.edittype = {
		date : {
			custom_element : function(value, options) {
				var el = $("<div></div>");
				var e = $("<input type='text' readonly='readonly' class='datepicker' />");
				e.val(value);
				$(e).datepicker({
					showOtherMonths : true,
					selectOtherMonths : true,
					dateFormat : 'yy-mm-dd',
					autoclose : true
				});
				el.html(e);
				return el;
			},
			custom_value : function(elem, operation, value) {
				if (operation === 'get') {
					var result = $(elem).find('input').val();
					return result;
				} else if (operation === 'set') {
					$(elem).find('input').val(value);
				}
			}
		},
		choice : {
			custom_element : function(value, options) {
				var el = $("<div></div>");
				var e= $("<button type='button' class='btn btn-white btn-primary' ><span>"+value+"</span> &nbsp;<b class='fa fa-reply'></b></button>");
				el.html('');
				el.append(e);
				options["_this"] = this;
				var idValue =  $(options["_this"]).getCell(options.rowId,options.idKey);
				$(el).beeChoice({
					title:"标题",
					src:"select",
					callback:function(r){
						$(e).html("<span>"+r.name+"</span> &nbsp;<b class='fa fa-reply'></b>");
						debugger;
						$(options["_this"]).setCell(options.rowId,options.idKey,r.id)
					},
					height:"300px",
					multiple:false,
					selectedList:[{id:idValue}],
					noBackdrop:true
				});
				return el;
			},
			custom_value : function(elem, operation, value) {
				var result = $(elem).find('span').html();
				return result;
			}
		}
	}
}

Array.prototype.unique3 = function(){
	 var res = [];
	 var json = {};
	 for(var i = 0; i < this.length; i++){
	  if(!json[this[i]]){
	   res.push(this[i]);
	   json[this[i]] = 1;
	  }
	 }
	 return res;
	}
