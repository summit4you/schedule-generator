<html>
<head>
		
	<style type="text/css" title="currentStyle">
		@import "js/datatables/css/demo_page.css";
		@import "js/datatables/css/demo_table.css";
	</style>
	<script type="text/javascript" language="javascript" src="js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="js/datatables/jquery.dataTables.js"></script>

</head>
<body>

{TABLE}


<script>
$(document).ready(function(){  
	var oTable = $('#maintable').dataTable(); 
	
	$('td', oTable.fnGetNodes()).editable( '../examples_support/editable_ajax.php', {
		"callback": function( sValue, y ) {
			var aPos = oTable.fnGetPosition( this );
			oTable.fnUpdate( sValue, aPos[0], aPos[1] );
		},
		"submitdata": function ( value, settings ) {
			return {
				"row_id": this.parentNode.getAttribute('id'),
				"column": oTable.fnGetPosition( this )[2]
			};
		},
		"height": "14px"
	} );

});
</script>

</body>
</html>