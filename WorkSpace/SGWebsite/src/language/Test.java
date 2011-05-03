package language;

import java.util.Vector;

public class Test
{
	public static void main(String[] args)
	{
		Dictionary dic = new Dictionary(Dictionary.Language.english, "C:\\SVN\\Workspace\\SGWebsite\\src\\language\\");
		System.out.println(dic.lookUp("##hond##"));
		System.out.println(dic.lookUp("##huis##"));
		System.out.println(dic.lookUp("##hemd##"));
		
		Vector<String> tags=new Vector<String>();
		tags.add("##hond##");
		tags.add("##huis##");
		tags.add("##hemd##");
		System.out.println(dic.translatePage("The ##hond## ate the ##hemd## in the ##huis## "));
	}

}
