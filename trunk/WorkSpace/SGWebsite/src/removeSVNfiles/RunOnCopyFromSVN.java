package removeSVNfiles;

import java.io.File;

/**
 * Removes all .svn folders from this project folder
 * @author Zjef
 * @version 1.0
 */
public class RunOnCopyFromSVN 
{	
	public static void main(String[] args)
	{
		removeSVN(new File(System.getProperty("user.dir")));
		System.out.println("Done");
		
	}
	
	private static void removeSVN(File file)
	{
		if (file.isDirectory())
		{
			if (file.getName().equals(".svn"))
			{
				deleteDir(file);
			}
			else
			{
				for (File i:file.listFiles())
				{
					removeSVN(i);
				}
			}
		}
	}
	
	private static void deleteDir(File file)
	{
		for (File i:file.listFiles())
		{
			if (i.isDirectory())
			{
				deleteDir(i);
			}
			else if (i.isFile())
			{
				i.delete();
			}
		}
		file.delete();
	}
}
