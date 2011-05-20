<html>
<head>

	<link rel="stylesheet" href="../js/slimpicker/pagestyle.css" media="screen, projection" />
	<link rel="stylesheet" href="../js/slimpicker/slimpicker.css" media="screen, projection" />
	<script type="text/javascript" src="../js/slimpicker/mootools-1.2.4-core-yc.js"></script>
	<script type="text/javascript" src="../js/slimpicker/mootools-1.2.4.4-more-yc.js"></script>
	<script type="text/javascript" src="../js/slimpicker/slimpicker.js"></script>

</head>
<body>



<table border="1" style="border-collapse:collapse">
	<tr>
		<td>
			<table border="1" style="border-collapse:collapse">
				<tbody>
					<form method="post" action="{MASTERSERVLET}&change=single">
						<input type="hidden" name="dateFormat" value="##DateFormat##"></input>
						<tr>
							<td colspan="4">
								##add_single_day##
							</td>
						</tr>
						<tr>
							<td width="185">
								<label for="inputdate">##date##</label>
							</td>
							<td>
								
							</td>
							<td>
								
							</td>
							<td>
							
							</td>
						</tr>
						<tr>
							<td>
								<input id="inputDate" name="inputDate" type="text" class="slimpicker" autocomplete="off" value="" />
							</td>
							<td style="vertical-align:top">
								<label for="startHour">##start_hour##</label>
								<select id="startHour" name="startHour">
								<option>0:00</option>
								<option>1:00</option>
								<option>2:00</option>
								<option>3:00</option>
								<option>4:00</option>
								<option>5:00</option>
								<option>6:00</option>
								<option>7:00</option>
								<option>8:00</option>
								<option>9:00</option>
								<option>10:00</option>
								<option>11:00</option>
								<option>12:00</option>
								<option>13:00</option>
								<option>14:00</option>
								<option>15:00</option>
								<option>16:00</option>
								<option>17:00</option>
								<option>18:00</option>
								<option>19:00</option>
								<option>20:00</option>
								<option>21:00</option>
								<option>22:00</option>
								<option>23:00</option>
								</select>
							</td>
							<td style="vertical-align:top">
								<label for="stopHour">##stop_hour##</label>
								<select id="stopHour" name="stopHour">
								<option>0:00</option>
								<option>1:00</option>
								<option>2:00</option>
								<option>3:00</option>
								<option>4:00</option>
								<option>5:00</option>
								<option>6:00</option>
								<option>7:00</option>
								<option>8:00</option>
								<option>9:00</option>
								<option>10:00</option>
								<option>11:00</option>
								<option>12:00</option>
								<option>13:00</option>
								<option>14:00</option>
								<option>15:00</option>
								<option>16:00</option>
								<option>17:00</option>
								<option>18:00</option>
								<option>19:00</option>
								<option>20:00</option>
								<option>21:00</option>
								<option>22:00</option>
								<option>23:00</option>
								</select>
							</td>
							<td style="vertical-align:top">
								<input type="submit">
							</td>
						</tr>
					
					</form>

					<form method="post" action="{MASTERSERVLET}&change=week">
						<input type="hidden" name="dateFormat" value="##DateFormat##"></input>
						<tr>
							<td colspan="4" height="50" style="vertical-align:bottom">
								##add_weekly_day##
							</td>
						</tr>
						<tr>
							<td>
								
							</td>
							<td>
								<label for="startDate">##start_week##</label>
							</td>
							<td>
								<label for="stopDate">##stop_week##</label>
							</td>
							<td>
							
							</td>
						</tr>
						<tr>
							<td style="vertical-align:top">
								<select name="day">
									<option value="MO">##monday##</option>
									<option value="TU">##tuesday##</option>
									<option value="WE">##wednesday##</option>
									<option value="TH">##thursday##</option>
									<option value="FR">##friday##</option>
									<option value="SA">##saturday##</option>
									<option value="SU">##sunday##</option>
								</select>
							</td>
							<td>
								<input id="startDate" name="startDate" type="text" class="slimpicker" autocomplete="off" value="" />
							</td>
							<td>
								<input id="stopDate" name="stopDate" type="text" class="slimpicker" autocomplete="off" value="" />
							</td>
							<td>
								
							</td>
						</tr>
						<tr>
							<td>
								
							</td>
							<td style="vertical-align:top">
								<label for="stopHour2">##start_hour##</label>
								<select id="stopHour2" name="startHour">
								<option>0:00</option>
								<option>1:00</option>
								<option>2:00</option>
								<option>3:00</option>
								<option>4:00</option>
								<option>5:00</option>
								<option>6:00</option>
								<option>7:00</option>
								<option>8:00</option>
								<option>9:00</option>
								<option>10:00</option>
								<option>11:00</option>
								<option>12:00</option>
								<option>13:00</option>
								<option>14:00</option>
								<option>15:00</option>
								<option>16:00</option>
								<option>17:00</option>
								<option>18:00</option>
								<option>19:00</option>
								<option>20:00</option>
								<option>21:00</option>
								<option>22:00</option>
								<option>23:00</option>
								</select>
							</td>
							<td style="vertical-align:top">
								<label for="stopHour2">##stop_hour##</label>
								<select id="stopHour" name="stopHour">
								<option>0:00</option>
								<option>1:00</option>
								<option>2:00</option>
								<option>3:00</option>
								<option>4:00</option>
								<option>5:00</option>
								<option>6:00</option>
								<option>7:00</option>
								<option>8:00</option>
								<option>9:00</option>
								<option>10:00</option>
								<option>11:00</option>
								<option>12:00</option>
								<option>13:00</option>
								<option>14:00</option>
								<option>15:00</option>
								<option>16:00</option>
								<option>17:00</option>
								<option>18:00</option>
								<option>19:00</option>
								<option>20:00</option>
								<option>21:00</option>
								<option>22:00</option>
								<option>23:00</option>
								</select>
							</td>
							<td style="vertical-align:top">
								<input type="submit">
							</td>
						</tr>
					
					</form>
					
					<form method="post" action="{MASTERSERVLET}&change=long">
						<input type="hidden" name="dateFormat" value="##DateFormat##"></input>
						<tr>
							<td colspan="4" height="50" style="vertical-align:bottom">
								##add_long_period##
							</td>
						</tr>
						<tr>
							<td>
							
							</td>
							<td style="vertical-align:bottom">
								<label for="startDate">##start date##</label> 
							</td>
							<td style="vertical-align:bottom">
								<label for="stopDate">##stop_date##</label>
							</td>
							<td>
							
							</td>
						</tr>
						<tr>
							<td>
							
							</td>
							<td style="vertical-align:top">
								<input class="slimpicker" name="startDate" id="startDate" type="text"  autocomplete="off"/>
							</td>
							<td style="vertical-align:top">
								 <input class="slimpicker" name="stopDate" id="stopDate" type="text" autocomplete="off"/>
							</td>
							<td style="vertical-align:top">
								<input type="submit">
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>		
						</tr>
					</form>
				</tbody>		
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<iframe frameborder=0 src="{CALENDARLINK}" width="980" height="750">
			##noiframe##
			</iframe>
		</td>
	</tr>
</table>


	<script>
	$$('input.slimpicker').each( function(el){
		var picker = new SlimPicker(el);
	});
	</script>

</body>
</html>