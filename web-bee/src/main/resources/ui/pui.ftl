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
		
		<style>
			#tabs-main {
				border: 0px solid #c5d0dc;
			}
			iframe {
				width: 100%;
				margin: 0 0 1em;
				border: 0;
			}
			body{
				line-height: 1;
			}
			
			.ui-jqgrid .ui-jqgrid-pager {
			    line-height: 1px;
			    height: 40px;
			    padding-top: 5px!important;
			    padding-bottom: 5px!important;
			    z-index: 1;
    		}
			
			.ui-jqgrid .ui-jqgrid-title {
			    float: left;
			    margin: 8px;
			    margin-top: 8px;
			    margin-right: 8px;
			    margin-bottom: 0px;
			    margin-left: 8px;
			}
		</style>
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
	<!-- ace scripts -->
	<script src="assets/js/ace-elements.min.js"></script>
	<script src="assets/js/ace.min.js"></script>
	<!-- inline scripts related to this page -->
	<script src="assets/bee/bee.js"></script>
	<script src="assets/js/jquery-ui.min.js"></script>
	
	<script src="assets/bee/bee.jqgrid.js"></script>
	<script src="assets/bee/bee.choice.js"></script>
	
	<script src="assets/js/jquery-form.min.js"></script>
	<script src="assets/bee/app.js"></script>
	<script src="assets/js/jquery.validate.min.js"></script>

	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content" style="padding: 8px 20px 5px;">
					<#nested>
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
		<!-- <#include "include/ui-footer.ftl"/> -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->
</body>
</html>
</#macro>

