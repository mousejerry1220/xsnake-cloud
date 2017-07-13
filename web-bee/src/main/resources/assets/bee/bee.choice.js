(function($) { 
	$.fn
			.extend({
				beeChoice : function(option) {
					var _this = this;
					var randId = null;
					var _iframe = null;
					$(this).click(function(e){
						if(!option){
							option = {};
						}
						var defaultOption = {
							multiple:false,
							backdrop:false
						}
						var newOption = $.extend({},defaultOption, option);
						var x = 9;
						var y = 0;
						randId = "";
						for (var i = 0; i < 8; i++) {
							var rand = parseInt(Math.random() * (x - y + 1) + y);
							randId = randId + rand;
						} 
						var width = newOption.width ? newOption.width : "1000px";
						var height = newOption.height ? newOption.height : "500px";
						$(this).attr('data-toggle', "modal");
						$(this).attr('data-target', "#"+randId);
						var url = newOption.src + (newOption.src.indexOf("?") > 0 ? "&" : "?") +"callback="+randId + "&multiple="+newOption.multiple;
						var dialogDiv = $("<div id='"+randId+"' class='modal  ' tabindex='999' role='dialog'></div>");
						var modalDialog = $("<div class='modal-dialog' style='width: "+width+";height:"+height+"'></div>");
						var modalContent = $("<div class='modal-content'></div>");
						dialogDiv.append(modalDialog);
						modalDialog.append(modalContent);
						var modalHeader = $("<div class='modal-header' style='padding: 6px;'><h3 class='smaller lighter blue no-margin'>"+newOption.title+"</h3></div>");
						var topCloseBtn = $("<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>");
						modalHeader.prepend(topCloseBtn);
						modalContent.append(modalHeader);
						var modalBody = $("<div class='modal-body' style='padding: 0px;'></div>");
						var iframeContent = $("<iframe name='iframe_"+randId+"' height='405px' src='"+url+"'></iframe>")
						modalBody.append(iframeContent);
						modalContent.append(modalBody);
						var moadlFooter = $("<div class='modal-footer'>  </div>");
						var closeBtn = $("<button class='btn btn-sm btn-danger pull-right' type='button' > <i class='ace-icon fa fa-times'></i> 确定选择 </button>");
						moadlFooter.append(closeBtn);
						modalContent.append(moadlFooter);
						_this.parent().append(dialogDiv);
						//top["hiddenView"].append(dialogDiv);
						var _iframe = $("iframe")[0];
						$(closeBtn).click(function(){
							var result = _iframe.contentWindow.callback();
							if(result == null || result == undefined){
								alert("没有选择数据");
								return ;
							}
							if(newOption.multiple && result.length == 0){
								alert("没有选择数据");
								return;
							}
							//客户回调函数
							newOption.callback(result);
							$('#'+randId).modal('hide');
						})
						
						window["initSelectedListFunction_"+randId] = function(){
							if(newOption.selectedList){
								return newOption.selectedList;
							}
							return [];
						}
						
						$(dialogDiv).on("hide.bs.modal",function(){
							_iframe.contentWindow.close();
							try{
								_iframe.remove();//删除iframe
							}catch (e) {
								//IE浏览器不支持此方法
							}
							if(newOption.noBackdrop){
								console.info("hide.bs.modal");
								$(".modal-backdrop").remove();
							}
							
						})
						$(dialogDiv).on("shown.bs.modal",function(){
							if(newOption.noBackdrop){
								console.info("shown.bs.modal");
								$(".modal-backdrop").remove();
							}
						});
					})
				}
			})
})(jQuery);