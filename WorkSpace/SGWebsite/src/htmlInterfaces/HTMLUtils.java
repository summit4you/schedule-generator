package htmlInterfaces;

import java.util.Vector;

import com.hp.gagawa.java.elements.*;

/**
 * Toolbox used to automatically generate HTML code.
 * @author Adam
 * @version 1.1
 */
public class HTMLUtils
{
	/**
	 * Makes a html table from a vector containing the rows of the
	 * table. Every row is a vector of string. Every string is a 
	 * table element. 
	 */
	static public Table toHTMLTable(Vector<Vector<String>> in)
	{
		Table tabel = new Table();
		for (Vector<String> i : in)
		{
			Tr rij = new Tr();
			for (String j : i)
			{
				rij.appendChild(new Td().appendChild(new Text(j)));
			}
			tabel.appendChild(rij);
		} 
		return tabel;
	}
	
	static public Table toDataTable(String tableID, Vector<String> head,Vector<Vector<String>> data)
	{
		Tr headrij = new Tr();
		for (String n : head)
		{
			headrij.appendChild(new Th().appendChild(new Text(n)));
		}
		Thead thead = new Thead().appendChild(headrij);
		Table tabel = new Table().appendChild(thead).setId(tableID);
		
		Tbody tbody = new Tbody();
		Integer count = new Integer(0);
		for (Vector<String> i : data)
		{
			count++;
			Tr rij = new Tr();
			for (String j : i)
			{
				rij.setId(count.toString()).appendChild(new Td().appendChild(new Text(j)));
			}
			tbody.appendChild(rij);
		} 
		tabel.appendChild(tbody);
		return tabel;
	}
	
	/**
	 * Makes a standard  form  identified by a string and servlet. 
	 * Id is needed to resolve the form action link 
	 * @author Adam
	 */
	static public Form makeStandardForm(String select, String servletname, String ID)
	{
		if (select.toLowerCase().equals("login"))
		{
			Form form = new Form(servletname);
			form.setMethod("post");
			Table tabel = new Table();
			tabel.setAlign("center").setBorder("0").setStyle("borde-collapse:collapse");
			Tr rij1 = new Tr();
			// accountnaam <input type="text" name="name"></input>
			rij1.appendChild(new Td().appendChild(new Text("Accountnaam")));
			rij1.appendChild(new Td().appendChild(new Input().setType("text").setName("name")));
			tabel.appendChild(rij1);
			Tr rij2 = new Tr();
			// password <input type="password" name="pass"></input>
			rij2.appendChild(new Td().appendChild(new Text("Wachtwoord")));
			rij2.appendChild(new Td().appendChild(new Input().setType("password").setName("pass")));
			tabel.appendChild(rij2);
			Tr rij3 = new Tr();
			// Send button <input type="submit"></input>
			rij3.appendChild(new Td().setColspan("2").setAlign("center").appendChild(new Input().setType("submit")));
			tabel.appendChild(rij3);
			form.appendChild(tabel);
			// Form type (for processing) <input type="hidden" name="type" value="login"></input>
			return form.appendChild(new Input().setType("hidden").setName("formtype").setValue("login"));
		}
		else if (select.toLowerCase().equals("search")) 
		{
			Form form = new Form(servletname);
			form.setMethod("post");
			Table tabel = new Table();
			tabel.setAlign("center").setBorder("0").setStyle("borde-collapse:collapse");
			Tr rij1 = new Tr();
			rij1.appendChild(new Td().appendChild(new Text("Type")));
			Select dropdown = new Select().setName("type");
			dropdown.appendChild(new Option().appendChild(new Text("student")));
			dropdown.appendChild(new Option().appendChild(new Text("educator")));
			dropdown.appendChild(new Option().appendChild(new Text("room")));
			rij1.appendChild(new Td().appendChild(dropdown));
			tabel.appendChild(rij1);
			Tr rij2 = new Tr();
			rij2.appendChild(new Td().appendChild(new Text("Search")));
			rij2.appendChild(new Td().appendChild(new Input().setType("text").setName("query")));
			tabel.appendChild(rij2);
			Tr rij3 = new Tr();
			rij3.appendChild(new Td().setColspan("2").setAlign("center").appendChild(new Input().setType("submit")));
			tabel.appendChild(rij3);
			form.appendChild(tabel);
			form.appendChild(new Input().setType("hidden").setName("ID").setValue(ID));
			return form.appendChild(new Input().setType("hidden").setName("formtype").setValue("search"));
		}
		return null;
	}
	
}
