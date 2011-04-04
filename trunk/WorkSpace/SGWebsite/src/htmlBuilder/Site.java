package HTMLBuilder;

import xml.ElementWithChildren;
import xml.ElementWithValue;
import xml.XMLDocument;

public class Site 
{
	private String htmlPage;
	
	private String htmlTab;
	
	private String htmlFrame;
		private final String defaultFrameWidth="100%";
		private final String defaultFrameHeight="600";
	
	private String path;
		private final String defaultPath="/site.xml";
	
	private String htmlcode;
	
	private void init()
	{
		htmlcode=new String(htmlPage);
	}
	
	private boolean loadTemplate()
	{
		XMLDocument template = new XMLDocument(path);
		if (template.load())
		{
			htmlPage=ElementWithValue.class.cast(template.getElement("HTMLPAGE")).getValue();
			htmlTab=ElementWithValue.class.cast(template.getElement("TAB")).getValue();
			htmlFrame=ElementWithValue.class.cast(template.getElement("IFRAME")).getValue();
			return true;
		}
		else
		{
			System.err.println("Site: LoadTemplate(): Template "+path+" could not be read");
			return false;
		}
	}
	
	public Site()
	{
		setPath(defaultPath);
		loadTemplate();
		init();
	}
	public Site(String path)
	{
		setPath(path);
		loadTemplate();
		init();
	}

	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	
	public String getHtmlCode() 
	{
		return htmlcode.replace("#pagecontent#","");
	}

	public void clear()
	{
		init();
	}
	
	public void reloadTemplate()
	{
		loadTemplate();
	}
		
	private String createTab(String tabtitle,String tabcontent)
	{
		String tab = htmlTab;
		tab=tab.replace("#tabtitle#", tabtitle);
		tab=tab.replace("#tabcontent#",tabcontent);
		return tab;
	}
	
	private  String createIFrame(String src, String width, String height)
	{
		String frame = htmlFrame;
		frame=frame.replace("#src#",src);
		frame=frame.replace("#width#",width);
		frame=frame.replace("#height#",height);
		return frame;
	}	
	private  String createIFrame(String src)
	{
		return  createIFrame(src, defaultFrameWidth, defaultFrameHeight);
	}	
	private  String createTabWithIFrame(String tabtitle,String src)
	{
		return createTab(tabtitle,createIFrame(src));
	}
	
	private  void addContent(String content)
	{
		htmlcode=htmlcode.replace("#pagecontent#", content+"#pagecontent#");
	}

	public void addTabWithIFrame(String tabname,String src)
	{
		addContent(createTabWithIFrame(tabname,src));
	}	
	public void addTab(String tabname,String content)
	{
		addContent(createTab(tabname,content));
	}

	
}
