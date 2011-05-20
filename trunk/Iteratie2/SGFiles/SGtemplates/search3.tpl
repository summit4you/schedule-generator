<style type="text/css" title="currentStyle">
		@import "../js/datatables/css/demo_page.css";
		@import "../js/datatables/css/demo_table.css";
</style>
<script type="text/javascript" language="javascript" src="../js/jquery.js"></script>
<script type="text/javascript" language="javascript" src="../js/datatables/jquery.dataTables.js"></script>

<script type="text/javascript">
\$(document).ready(function() {
	/* Add a click handler to the rows - this could be used as a callback */
	\$("#results tbody").click(function(event) {
		\$(oTable.fnSettings().aoData).each(function (){
			\$(this.nTr).removeClass('row_selected');
		});
		\$(event.target.parentNode).addClass('row_selected');
		var anSelected = fnGetSelected( oTable );
		document.URL="{LINK}"+"&searchresult="+oTable.fnGetData(anSelected)[2];
	});
	
	/* Init the table */
	oTable = \$('#results').dataTable( );
} );


/* Get the rows which are currently selected */
function fnGetSelected( oTableLocal )
{
	var aTrs = oTableLocal.fnGetNodes();
	
	for ( var i=0 ; i<aTrs.length ; i++ )
	{
		if ( \$(aTrs[i]).hasClass('row_selected') )
		{
			return aTrs[i];
		}
	}	
}
</script>

{SEARCHRESULTS}