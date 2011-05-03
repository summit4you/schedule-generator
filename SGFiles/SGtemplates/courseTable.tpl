<html>
<head>
		
	<style type="text/css" title="currentStyle">
		@import "js/datatables/css/demo_page.css";
		@import "js/datatables/css/demo_table.css";
		@import "js/datatables/css/jquery-ui-1.8.4.custom.css";
			.ui-tabs .ui-tabs-panel { padding: 10px }
	</style>
	<script type="text/javascript" language="javascript" src="js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="js/datatables/jquery.dataTables.js"></script>
	<script type="text/javascript" language="javascript" src="js/datatables/jquery-ui-tabs.js"></script>

</head>
<body>

<script>
	function setIFrameContent(selector,tabframe)
	{
		var op=document.getElementById(selector);
		var ifrm=document.getElementById(tabframe).src='{LINK}'+'&sel='+op.value+'&tab='+tabframe;
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

<script>
$(document).ready(function() {
		$("#tabs").tabs();
		
		$("#tabs").tabs( {
		"show": function(event, ui) {
			setIFrameSize();
		}
	} );

		
		setIFrameSize();
	} );
</script>

<script>
	function calcHeight(name)
	{
	  //find the height of the internal page
	  var the_height=document.getElementById(name).contentWindow.document.body.scrollHeight;

	  //change the height of the iframe
	  document.getElementById(name).height=the_height+20;
	  
	  //find the width of the internal page
	  var the_width=document.getElementById(name).contentWindow.document.body.scrollWidth;

	  //change the height of the iframe
	  document.getElementById(name).width=the_width+20;
	}
	
	function setIFrameSize()
	{
		{SIZE}
	}
</script>
	
</body>
</html>