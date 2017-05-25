(function($) {
	$.fn.extend({
		postForm : function(options) {
			$(this).click(function(fe){
				var submit = false; 
				var clickTarget = null;
				$.each(fe.target.classList,function(i,cls){
					if(cls == 'submit'){
						submit = true;
						clickTarget = fe.target;
					}
				})
				if(!submit && fe.target.parentNode !=null && fe.target.parentNode.type == 'button'){
					$.each(fe.target.parentNode.classList,function(i,cls){
						if(cls == 'submit'){
							submit = true;
							clickTarget = fe.target.parentNode
						}
					})
				}
				if(!submit){
					return;
				}
				if(!$(clickTarget).prop("disabled") == false){
					return;
				}
				var form = this;
				if(typeof(options.before) == "function"){
					options.before();
				}
				var form_validator = {};
				form_validator.rules = {};
				form_validator.messages={};
				$.each($(form).find('input'),function(i,e){
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
					form_validator.rules[e.name] = rule;
					form_validator.messages[e.name] = message;
				})
				
				$.each($(form).find('textarea'),function(i,e){
					if($(e).attr("valid-required") != undefined){
						form_validator.rules[e.name] = { required : true };
						form_validator.messages[e.name] = { required : $(e).attr("valid-required") };
					}
				})
				
				$.each($(form).find('select'),function(i,e){
					if($(e).attr("valid-required") != undefined){
						form_validator.rules[e.name] = { required : true };
						form_validator.messages[e.name] = { required : $(e).attr("valid-required") };
					}
				})
				
				form_validator.errorPlacement = function(error, element) {
					error.insertAfter(element.parent());
				}
				
				form_validator.submitHandler = function(form){
					var _success = options.success;
					var _error = options.error;
					$(clickTarget).prop("disabled",true);
					$(form).ajaxSubmit({
						type: "post",
						data: options.data,
					    url: $(form).attr('action'),
					    success : function(result){
					    	$(clickTarget).prop("disabled",false);
					    	if(typeof(options.after) == "function"){
					    		options.after();
					    	}
					    	if(result.status == "error" && typeof(_error)=="function"){
					    		_error(result.message);
					    	}else if(result.status == "success" && typeof(_success)=="function"){
					    		_success(result.message);
					    	}else if(result.status == "error"){
					    		alert(result.message);
					    	}
					    },
					    error : function(error){
					    	$(clickTarget).prop("disabled",false);
					    	if(typeof(options.after) == "function"){
					    		options.after();
					    	}
					    	if(error.status == 400){
								alert("检查值类型，数据类型不匹配");
							}else if(error.status == 401 || error.status == 403){
								var data = JSON.parse(error.responseText);
								if(typeof(_error)=="function"){
					    			_error(data.message);
						    	}
							}else if(error.status == 404){
								alert("请求地址没有找到");
							}else if(error.status<500){
								alert("请检查网络连接是否正常");
					    	}else if(error.responseText){
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
					});
				}
				$(form).validate(form_validator);
				$(form).submit();
			})
		}
	})
})(jQuery);