package login;

import pseudoServlets.PseudoServlet;
import htmlBuilder.MainServlet;
import htmlBuilder.Site;

public class GuestType extends UserType
{
	
	GuestType()
	{
		super();
		pseudos.add("Search");
	}
	
	@Override
	public String buildMainSite(String baselink, String id)
	{
		String link=baselink+"?"+MainServlet.identifierParamTag+"="+id+"?"+PseudoServlet.pseudoServletParamTag;
		Site mainSite = new Site();
		mainSite.addTabWithIFrame(Site.TabName.Search,link+Site.TabName.Search.toString());
		return mainSite.getHtmlCode();
	}

}
