package other;

import login.Account;
import database.Search;
import database.Database;

public class test
{

	/**
	 * @param args
	 */
	
		public static void main(String[] arg)
		 {
		  Database db = Database.getDB();
		     db.connect();
		     Search s = new Search(Account.class,"getUserName;getPassword","admin","admin");
		     Account acc = db.read(s);
		     db.disconnect();
		     System.out.println(acc.getLanguage());
		 }
	

}
