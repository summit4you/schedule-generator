<html>
<head>
		
	<style type="text/css" title="currentStyle">
		@import "../js/datatables/css/demo_page.css";
		@import "../js/datatables/css/demo_table.css";
		@import "../js/datatables/css/jquery-ui-1.8.4.custom.css";
			.ui-tabs .ui-tabs-panel { padding: 10px }
	</style>
	<script type="text/javascript" language="javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="../js/datatables/jquery.dataTables.js"></script>
	<script type="text/javascript" language="javascript" src="../js/datatables/jquery-ui-tabs.js"></script>

</head>
<body id="dt_example">

<script>
	function setIFrameContent(selector,tabframe)
	{
		var op=document.getElementById(selector);
		var ifrm=document.getElementById(tabframe).src='{LINK}'+'&sel='+op.value+'&tab='+tabframe;
	}
</script>
<div id="container">
<div id="demo">
	<div id="tabs">
		<ul>
			{TABS}
		</ul>
	{TABCONTENT}
	</div>
</div>
</div>
<script>
$(document).ready(function() {
		$("#tabs").tabs();
	} );
</script>

	
</body>
</html>