package other;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Zjef
 * @version 1.0
 */
public class FileIO 
{
	/**
	 * Returns a string of the content of the file
	 */
	public static String readFile(String filePath)
	{
	    byte[] buffer = new byte[(int) new File(filePath).length()];
	    BufferedInputStream f = null;
	    try {
	        f = new BufferedInputStream(new FileInputStream(filePath));
	        f.read(buffer);
	    } 
	    catch (Exception e)
	    {
			e.printStackTrace();
		} 
	    finally 
	    {
	        if (f != null) try 
	        { 
	        	f.close(); 
	        } 
	        catch (IOException ignored) 
	        {
	        }
	    }
	    return new String(buffer);
	}
	
	public static String readFileWithException(String filePath) throws Exception
	{
	    byte[] buffer = new byte[(int) new File(filePath).length()];
	    BufferedInputStream f = null;

	    f = new BufferedInputStream(new FileInputStream(filePath));
	    f.read(buffer);
	    f.close(); 
	    return new String(buffer);
	}
}
