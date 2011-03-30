<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
    <title><%= application.getServerInfo() %></title>
    <style type="text/css">
    /*<![CDATA[*/
      body {
          color: #000000;
          background-color: #FFFFFF;
	  font-family: Arial, "Times New Roman", Times, serif;
          margin: 10px 0px;
      }

    img {
       border: none;
    }
    
    a:link, a:visited {
        color: blue
    }

    th {
        font-family: Verdana, "Times New Roman", Times, serif;
        font-size: 110%;
        font-weight: normal;
        font-style: italic;
        background: #D2A41C;
        text-align: left;
    }

    td {
        color: #000000;
	font-family: Arial, Helvetica, sans-serif;
    }
    
    td.menu {
        background: #FFDC75;
    }

    .center {
        text-align: center;
    }

    .code {
        color: #000000;
        font-family: "Courier New", Courier, monospace;
        font-size: 110%;
        margin-left: 2.5em;
    }
    
     #banner {
        margin-bottom: 12px;
     }

     p#congrats {
         margin-top: 0;
         font-weight: bold;
         text-align: center;
     }

     p#footer {
         text-align: right;
         font-size: 80%;
     }
     /*]]>*/
   </style>
</head>

<body>

<!-- Header -->
<table id="banner" width="100%">
    <tr>
      <td align="left" style="width:130px">
        <a href="http://tomcat.apache.org/">
	  <img src="tomcat.gif" height="92" width="130" alt="The Mighty Tomcat - MEOW!"/>
	</a>
      </td>
      <td align="left" valign="top"><b><%= application.getServerInfo() %></b></td>
      <td align="right">
        <a href="http://jakarta.apache.org/">
	  <img src="asf-logo-wide.gif" height="51" width="537" alt="The Apache Software Foundation"/>
	</a>
       </td>
     </tr>
</table>

<table>
    <tr>

        <!-- Table of Contents -->
        <td valign="top">
<table width="100%" border="1" cellspacing="0" cellpadding="3">
<tr><th>Administration</th></tr>
<tr><td class="menu">
		<a href="manager/status">Status</a><br/>
    <!--<a href="admin">Tomcat&nbsp;Administration</a><br/>-->
    <a href="manager/html">Tomcat&nbsp;Manager</a>
    &nbsp;</td>
</tr>
</table>
<br/>
<table width="100%" border="1" cellspacing="0" cellpadding="3">
<tr><th>Documentation</th></tr>
<tr><td class="menu">
    <a href="RELEASE-NOTES.txt">Release&nbsp;Notes</a><br/>
    <a href="docs/changelog.html">Change&nbsp;Log</a><br/>
    <a href="docs">Tomcat&nbsp;Documentation</a><br/> 
    <a href="http://docs.coreservlets.com/servlet-3.0-api/">Servlet 3.0 &amp; JSP 2.2 API</a><br/>
    <a href="http://java.sun.com/products/servlet/2.5/docs/servlet-2_5-mr2/">Servlet 2.5 API</a><br/>
    <a href="http://java.sun.com/products/jsp/2.1/docs/jsp-2_1-pfd2/">JSP 2.1 API</a><br/>
    <a href="http://jakarta.apache.org/tomcat/tomcat-5.5-doc/servletapi/">Servlet 2.4 API</a><br/>
    <a href="http://jakarta.apache.org/tomcat/tomcat-5.5-doc/jspapi/">JSP 2.0 API</a>
		&nbsp;
    </td>
</tr>
</table>
<br/>
<table width="100%" border="1" cellspacing="0" cellpadding="3">
<tr><th>Tutorials</th></tr>
<tr><td class="menu">
    <a href="http://www.coreservlets.com/Apache-Tomcat-Tutorial/">Apache Tomcat Tutorial</a><br/>
    <a href="http://courses.coreservlets.com/Course-Materials/csajsp2.html">Servlet &amp; JSP Tutorial</a><br/>
    <a href="http://courses.coreservlets.com/Course-Materials/ajax.html">Ajax &amp; jQuery Tutorial</a><br/> 
    <a href="http://www.coreservlets.com/JSF-Tutorial/jsf1/">JSF 1.x Tutorial</a><br/> 
    <a href="http://www.coreservlets.com/JSF-Tutorial/jsf2/">JSF 2.0 Tutorial</a><br/> 
    <a href="http://courses.coreservlets.com/Course-Materials/gwt.html">GWT Tutorial</a><br/>
    <a href="http://courses.coreservlets.com/Course-Materials/spring.html">Spring Tutorial</a><br/>
    <a href="http://courses.coreservlets.com/Course-Materials/hibernate.html">Hibernate &amp; JPA Tutorial</a><br/>
    <a href="http://courses.coreservlets.com/Course-Materials/java5.html">Java 6 Programming Tutorial</a><br/>
    <a href="http://courses.coreservlets.com/Course-Materials/java-ee.html">EJB3 &amp; Web Services</a>
    &nbsp;</td>
</tr>
</table>
<br/>
<table width="100%" border="1" cellspacing="0" cellpadding="3">
<tr><th>Tomcat Online</th></tr>
<tr><td class="menu">
    <a href="http://tomcat.apache.org/">Home&nbsp;Page</a><br/>
		<a href="http://tomcat.apache.org/faq/">FAQ</a><br/>
    <a href="http://tomcat.apache.org/bugreport.html">Bug&nbsp;Database</a><br/>
    <a href="http://issues.apache.org/bugzilla/buglist.cgi?bug_status=UNCONFIRMED&amp;bug_status=NEW&amp;bug_status=ASSIGNED&amp;bug_status=REOPENED&amp;bug_status=RESOLVED&amp;resolution=LATER&amp;resolution=REMIND&amp;resolution=---&amp;bugidtype=include&amp;product=Tomcat+5&amp;cmdtype=doit&amp;order=Importance">Open Bugs</a><br/>
    <a href="http://mail-archives.apache.org/mod_mbox/tomcat-users/">Users&nbsp;Mailing&nbsp;List</a><br/>
    <a href="http://mail-archives.apache.org/mod_mbox/tomcat-dev/">Developers&nbsp;Mailing&nbsp;List</a>
		&nbsp;</td>
</tr>
</table>
    <br/>
<table width="100%" border="1" cellspacing="0" cellpadding="3">
<tr><th>Training Courses<br/>
(Customized/Onsite or Public)</th></tr>
<tr><td class="menu">
    <a href="http://courses.coreservlets.com/servlet+jsp-training.html">
	  Servlet &amp; JSP Training</a><br/>
    <a href="http://courses.coreservlets.com/advanced-servlet+jsp-training.html">
		Advanced JSP Training</a><br/>
    <a href="http://courses.coreservlets.com/jsf-training.html">
		JSF 2.0 Training</a><br/>
    <a href="http://courses.coreservlets.com/ajax-training.html">
	  Ajax &amp; jQuery Training</a><br/>
    <a href="http://courses.coreservlets.com/gwt-training.html">
	  GWT Training</a><br/>
    <a href="http://courses.coreservlets.com/spring-training.html">
	  Spring Training</a><br/>
    <a href="http://courses.coreservlets.com/Course-Materials/hibernate.html">
	  Hibernate &amp; JPA Training</a><br/>
    <a href="http://courses.coreservlets.com/java-training.html">
    Java 6 Training</a><br/>
    <a href="http://courses.coreservlets.com/public-courses/">
	  Public Training Schedule</a>		
    &nbsp;</td>
 </tr>
 </table>
			<br/>
<table width="100%" border="1" cellspacing="0" cellpadding="3">
<tr><th>Examples</th></tr>
<tr><td class="menu">
    <a href="examples/servlets/">Servlets Examples</a><br/>
    <a href="examples/jsp/">JSP Examples</a><br/>
    <a href="webdav/">WebDAV&nbsp;capabilities</a>
    &nbsp;</td>
</tr>
</table>
	    
            <br/>
<table width="100%" border="1" cellspacing="0" cellpadding="3">
<tr><th>Miscellaneous</th></tr>
<tr><td class="menu">
    <a href="http://java.sun.com/products/jsp">Sun's&nbsp;JavaServer&nbsp;Pages&nbsp;Site</a><br/>
    <a href="http://java.sun.com/products/servlet">Sun's&nbsp;Servlet&nbsp;Site</a><br/>
    <a href="http://www.apl.jhu.edu/~hall/java/">Java Programming Resources</a>
    &nbsp;</td>
</tr>
</table>
        </td>

        <td style="width:20px">&nbsp;</td>
	
        <!-- Body -->
        <td align="left" valign="top">
          <p id="congrats">If you're seeing this page via a web browser, it means you've setup Tomcat successfully. Congratulations!<br/><br/>
		  <b><font color="red">Pre-configured version of Tomcat 7 from Marty Hall
	      and coreservlets.com. See 
		  <a href="http://www.coreservlets.com/Apache-Tomcat-Tutorial/">
          http://www.coreservlets.com/Apache-Tomcat-Tutorial/</a>.</font></b></p>
 
          <p>As you may have guessed by now, this is the default Tomcat home page. It can be found on the local filesystem at:</p>
          <p class="code">$CATALINA_HOME/webapps/ROOT/index.jsp</p>
	  
          <p>where "$CATALINA_HOME" is the root of the Tomcat installation directory. If you're seeing this page, and you don't think you should be, then either you're either a user who has arrived at new installation of Tomcat, or you're an administrator who hasn't got his/her setup quite right. Providing the latter is the case, please refer to the <a href="tomcat-docs">Tomcat Documentation</a> for more detailed setup and administration information than is found in the INSTALL file.</p>

            <p><b>NOTE: For security reasons, using the administration webapp
            is restricted to users with role "admin". The manager webapp
            is restricted to users with role "manager".</b>
            Users are defined in <code>$CATALINA_HOME/conf/tomcat-users.xml</code>.</p>

            <p>Included with this release are a host of sample Servlets and JSPs (with associated source code), extensive documentation, and an introductory guide to developing web applications.</p>

            <p>Tomcat mailing lists are available at the Tomcat project web site:</p>

           <ul>
               <li><b><a href="mailto:users@tomcat.apache.org">users@tomcat.apache.org</a></b> for general questions related to configuring and using Tomcat</li>
               <li><b><a href="mailto:dev@tomcat.apache.org">dev@tomcat.apache.org</a></b> for developers working on Tomcat</li>
           </ul>

            <p>Thanks for using Tomcat!</p>

            <p id="footer"><img src="tomcat-power.gif" width="77" height="80" alt="Powered by Tomcat"/><br/>
	    &nbsp;

	    Copyright &copy; 1999-2010 Apache Software Foundation<br/>
            All Rights Reserved
            </p>
        </td>

    </tr>
</table>

</body>
</html>
