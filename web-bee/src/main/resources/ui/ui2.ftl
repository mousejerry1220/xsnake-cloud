<#macro body>
<!DOCTYPE html>
<html lang="en">
<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>jqGrid - Ace Admin</title>

		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="assets/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="assets/css/jquery-ui.min.css" />
		<link rel="stylesheet" href="assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="assets/css/ui.jqgrid.min.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="assets/css/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->
		<link rel="stylesheet" href="assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->
		<script src="assets/js/ace-extra.min.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="assets/js/html5shiv.min.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

<body class="no-skin">

		<!-- basic scripts -->
		<!--[if !IE]> -->
		<script src="assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
<script src="assets/js/jquery-1.11.3.min.js"></script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->
		<script src="assets/js/bootstrap-datepicker.min.js"></script>
		<script src="assets/js/jquery.jqGrid.min.js"></script>
		<script src="assets/js/grid.locale-en.js"></script>
		<script src="assets/js/jquery-ui.min.js"></script>
		<!-- ace scripts -->
		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>
		<script src="assets/bee/bee.js"></script>

	<div id="navbar" class="navbar navbar-default          ace-save-state">
		<#include "include/ui-navbar.ftl"/>
	</div>
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
		<div id="sidebar" class="sidebar responsive ace-save-state">
			<script type="text/javascript">
					try{ace.settings.loadState('sidebar')}catch(e){}
				</script>
			<#include "include/ui-menu-buttons.ftl"/>
			<#include "include/ui-menu.ftl"/>
		</div>
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content"
					style="padding-top: 8px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px;">
					<style>
					#tabs-main {
						border: 0px solid #c5d0dc;
					}
					iframe {
						width: 100%;
						margin: 0 0 1em;
						border: 0;
					}
					</style>
					<script type="text/javascript">
					    $(function(){
					        $('#tabs').tabs();
					    });
					    function setIframeHeight(iframe) {
					        if (iframe) {
					            var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
					            iframe.height = (this.document.documentElement.scrollHeight || this.document.body.scrollHeight) - 103;
					        }
					    };
					    function addTab(title, url) {
					        var code = encodeURI(title);
					        var reg=new RegExp("%","g");
					        code = code.replace(reg,"");
					        //判断是否已经存在，存在则找出位置并选中
					        var p = -1;
					        var list = $("#navTabs li");
					        $.each(list, function(i, e) {
					            if (e.id == 'tab_li_' + code) {
					                p = i;
					            }
					        })
					        if (p > -1) {
					            $('#tabs').tabs("option", "active", p);
					        } else {
					            var li = "<li id='tab_li_"+code+"' ><a href='#tab_div_"+code+"' >" + title + " &nbsp; <img src='${ctx.contextPath}/assets/bee/images/close.png' style='margin-top: -4px;height: 8px;cursor:pointer;' onclick=closeTab('" + code+ "') />  </li>";
					            var div = "<div style='border: 0px solid #c5d0dc;padding: 0px 0px;' id='tab_div_"+code+"'><iframe onload='setIframeHeight(this)'   frameBorder=0  width='100%' src='" + url + "'></iframe></div>";
					            $('#navTabs').append(li);
					            $('#tabs').append(div);
					            $('#tabs').tabs("refresh")
					            var list = $("#navTabs li");
					            $('#tabs').tabs("option", "active", list.length - 1);
					        }
					        $('#tabs').tabs("refresh");
					    }
					 
					    function closeTab(title) {
					        var code = encodeURI(title);
					        var reg=new RegExp("%","g");
					        code = code.replace(reg,"");
					        $('#tab_li_' + code).remove();
					        $('#tab_div_' + code).remove();
					        var list = $("#navTabs li");
					        $('#tabs').tabs('refresh');
					        $('#tabs').tabs("option", "active", list.length - 1);
					    }
					</script>

					<div class="col-xs-12"
						style="padding-left: 1px; padding-right: 1px">
						<div id="tabs">
							<ul id="navTabs" style="margin-top: -10px;">
								<li><a href="#tabs-main">main</a></li>
							</ul>
							<div id="tabs-main">
								<#nested>
							</div>
						</div>
					</div>
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
		<!-- 			<#include "include/ui-footer.ftl"/> -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->
</body>
</html>
</#macro>