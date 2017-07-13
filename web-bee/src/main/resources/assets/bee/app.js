function ajaxPost(options){
	var form = options.form;
	var _success = options.success;
	var _error = options.error;
	var ajaxResult = new AjaxResult(_success,_error);
	$(form).ajaxSubmit({
		type: "post",
		data: options.data,
	    url: $(form).attr('action'),
	    success : ajaxResult.myajaxSuccess,
	    error: ajaxResult.myajaxError
	});
}
function AjaxResult(_success,_error){
	this.myajaxSuccess = function(result){ //以return 方式出来的信息或者异常
		if(result.status === "error" && typeof(_error)=="function"){
			_error(result.message);
		}
		if(result.status === "success" && typeof(_success)=="function"){
			_success(result.message);
		}
	},
	this.myajaxError = function(error){ //以throw 方式出来的异常
		if(error.status == 400){
			alert("检查spring值注入问题");
		}else if(error.status == 401){
			//未登陆系统跳转登陆
			var data = JSON.parse(error.responseText);
			window.location.href=ctx+data.message;
		}else if(error.status == 404 || error.status == 405){
			alert("请求地址没有找到 或者 请检查网络连接是否正常 ");
		}else if(error.status<500){//客户端异常
			alert("请检查网络连接是否正常");
		}else if(error.responseText){//服务端异常
			var data = JSON.parse(error.responseText);
			if(typeof(_error)=="function"){
				_error(data.message);
	    	}else{
	    		alert(data.message);
	    	}
		}else{
			alert("未知错误!");
		}
		
	}
}

(function($) {
	$.fn.extend({
		beeSubmit : function(option) {
			$(this).click(
					function(fe) {
						var submit = false;
						//点击带有submit标示的控件则提交
						$.each(fe.target.classList, function(i, cls) {
							if (cls == 'submit' && !$(fe.target.parentNode).attr("disabled")) {
								submit = true;
							}
						})
						//如果点到的控件父节点带有submit标示则提交
						if (!submit && fe.target.parentNode != null
								&& fe.target.parentNode.type == 'button') {
							$.each(fe.target.parentNode.classList, function(i,
									cls) {
								if (cls == 'submit' && !$(fe.target.parentNode).attr("disabled")) {
									submit = true;
								}
							})
						}
						if (!submit) {
							return;
						}
						//执行提交前方法，如果返回false则不执行
						if (typeof (option.before) == 'function') {
							var result = option.before();
							if (result == false) {
								return;
							}
						}
						//查找所有的待验证项
						var inputs = $("#" +this.id+ " input");
						var textareas = $("#" +this.id+ " textarea");
						var selects = $("#" +this.id+ " select");
						var validator = {rules:[],messages:[]};
						inputs.push.apply(inputs, textareas);
						inputs.push.apply(inputs, selects);
						$.each(inputs,function(i,e){
			                var rule = {};
			                var message={};
			                if($(e).attr("valid-required") != undefined){
			                    rule.required = true;
			                    message.required = $(e).attr("valid-required");
			                }
			                if($(e).attr("valid-number") !=undefined){
			                    rule.number = true;
			                    message.number =  $(e).attr("valid-number");
			                }
			                validator.rules[e.name] = rule;
			                validator.messages[e.name] = message;
			            })
			            validator.errorPlacement = function(error, element) {
			                error.insertAfter(element.parent());
			            }
						
						validator.submitHandler = function(form){
							$(fe.target.parentNode).attr("disabled",true);
							$(fe.target).attr("disabled",true);
		                    ajaxPost({
		                        form : form,
		                      	data : option.data,
		                        success : function(result) {
		                            $(fe.target.parentNode).removeAttr("disabled");
									$(fe.target).removeAttr("disabled");
		                            if(typeof(option.success) == 'function'){
		                                option.success(result);
		                            }
		                        },
		                        error : function(result) {
		                        	$(fe.target.parentNode).removeAttr("disabled");
									$(fe.target).removeAttr("disabled");
		                            if(typeof(option.error) == 'function' ){
		                            	option.error(result);
		                            }else{
		                                alert(result);
		                            }
		                        }
		                    });
		                    return false;
		                };
		                $(this).validate(validator);
		                $(this).submit();
					})
		}
	})
})(jQuery);