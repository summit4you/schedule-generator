<html>
<head>
	<script type="text/javascript" language="javascript" src="../js/jquery.js"></script>
</head>

<style type="text/css"> 
body {
	margin: 10px auto;
	font: 75%/120% Verdana,Arial, Helvetica, sans-serif;
}
p {
	padding: 0 0 1em;
}
.msg_list {
	margin: 0px auto;
	padding: 0px;
	width: 900;
	height:400px;
}
.msg_head {
	padding: 5px 10px;
	cursor: pointer;
	position: relative;
	background-color:#DDE;
	margin:1px;
}
.msg_body {
	padding: 0px 0px 0px;
	background-color:#FFFFFF;
}
</style>
<body>

<script type="text/javascript"> 
$(document).ready(function(){
	//hide the all of the element with class msg_body
	$(".msg_body").hide();
	//toggle the componenet with class msg_body
	$(".msg_head").click(function(){
		expand();
		$(this).next(".msg_body").slideToggle(250);
	});
});
</script>

<div class="msg_list">
<p class='msg_head'>##Load_Applet##</p>
<div class="msg_body" id="expand">	
	<script src='http://www.java.com/js/deployJava.js'>
	</script>
	<script>
		var loaded=false;
		function expand()
		{
			if (loaded==false)
			{
				loaded=true;
				var attributes={code:'components.DynamicTreeApplet',width:900,height:400};
				var parameters={jnlp_href:'{JNLP}.jnlp',url:'{URL}'{PARAMS}};
				deployJava.runApplet(attributes,parameters,'1.6');
			}
		}
	</script>
</div>

</body>
</html>