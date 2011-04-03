package HTMLBuilder;

import htmlInterfaces.HTMLFormable.FormOutput;
import htmlInterfaces.HTMLTablable.TableInput;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Vector;

import xml.XMLDocument;

/**
 * <b>This is a class for testing and will be removed later on. </b></br>
 * This class should be replaced with a JUnit TEST
 * @author Alexander
 * @version 0.1
 * @see HTMLBuilder
 */
public class Test
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String path = "C:\\Documents and Settings\\GEBRUIKER\\Mijn documenten\\VUB\\SoftwareEngeneering\\SVN\\WorkSpace\\SGJava\\src\\HTMLBuilder\\site.xml";
		
		Site testSite =new Site(path);
		testSite.addTabWithIFrame("test1","http\\\\:lolly");
		testSite.addTabWithIFrame("test2","http\\\\:lolly");
		testSite.addTabWithIFrame("test3","http\\\\:lolly");
		System.out.println(testSite.getHtmlCode());
	}

}
