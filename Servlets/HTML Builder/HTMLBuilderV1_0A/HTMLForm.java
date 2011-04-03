package HTMLBuilder;

import java.util.Vector;

public class HTMLForm extends HTMLFunction
{
	
//	<form action="/sample/test" method="post">
//	<table id="login" border=0 style="width:200px" align="center"><tbody>
//	<tr><td style="text-align: right">accountnaam</td><td><input type="text" name="NAME"></input></td></tr>
//	<tr><td style="text-align: right">wachtwoord</td><td><input type="password" name="PASS"></input></td></tr>
//	<tr><td colspan=2 style="text-align: center"><input type="submit" value="Send"></input></td></tr>
//	</tbody></table>
//	</form>	
	
	private String action;
	private String method;
	private Vector<FormInput> inputs;
	
	@Override
	public String toHTML()
	{
		return null
	}
}
