<html>
<head>
		
	<style type="text/css" title="currentStyle">
		@import "../js/datatables/css/demo_page.css";
		@import "../js/datatables/css/demo_table.css";
		@import "../js/datatables/css/jquery-ui-1.7.2.custom.css";
		.ui-tabs .ui-tabs-panel { padding: 10px }
	</style>
	<script type="text/javascript" language="javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="../js/datatables/jquery.dataTables.js"></script>
	<script type="text/javascript" language="javascript" src="../js/datatables/jquery-ui-tabs.js"></script>

</head>
<body>

<script>
	function setIFrameContent(selector,tabframe)
	{
		var op=document.getElementById(selector).value;
		var ifrm=document.getElementById(tabframe).src={LINK}+'&option='+op.value+'&fac='+tabFrame;
	}
</script>

<div id="demo">
	<div id="tabs">
		<ul>
			{TABS}
		</ul>
	{TABCONTENT}
	</div>
</div>

</body>
</html>