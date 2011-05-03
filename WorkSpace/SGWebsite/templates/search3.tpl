<script type="text/javascript">
$(document).ready(function() {
	/* Add a click handler to the rows - this could be used as a callback */
	$("#example tbody").click(function(event) {
		$(oTable.fnSettings().aoData).each(function (){
			$(this.nTr).removeClass('row_selected');
		});
		$(event.target.parentNode).addClass('row_selected');
		var anSelected = fnGetSelected( oTable );
		document.URL="{LINK}"+"&resultnumber="+oTable.fnGetData(anSelected)[2];
	});
	
	/* Init the table */
	oTable = $('#example').dataTable( );
} );


/* Get the rows which are currently selected */
function fnGetSelected( oTableLocal )
{
	var aTrs = oTableLocal.fnGetNodes();
	
	for ( var i=0 ; i<aTrs.length ; i++ )
	{
		if ( $(aTrs[i]).hasClass('row_selected') )
		{
			return aTrs[i];
		}
	}	
}
</script>

{SEARCHRESULTS}