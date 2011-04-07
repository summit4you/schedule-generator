package login;

import pseudoServlets.PseudoServlet;
import htmlBuilder.MainServlet;
import htmlBuilder.Site;

public class AdminType extends UserType
{
	public AdminType()
	{
		super();
		pseudos.add("Search");
		pseudos.add("Schedule");
	}
	
	@Override
	public String buildMainSite(String baselink, String id)
	{
		String link=baselink+"?"+MainServlet.identifierParamTag+"="+id+"?"+PseudoServlet.pseudoServletParamTag;
		Site mainSite = new Site();
		mainSite.addTabWithIFrame(Site.TabName.Search,link+Site.TabName.Search.toString());
		mainSite.addTabWithIFrame(Site.TabName.Schedule,Site.TabName.Schedule.toString());	
		return mainSite.getHtmlCode();
	}
}