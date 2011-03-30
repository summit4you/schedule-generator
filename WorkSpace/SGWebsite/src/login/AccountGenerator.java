package login;

import java.util.Vector;

import database.*;

public class AccountGenerator
{
	public static void main(String[] args)
	{
		clean();
		write();
		read();
	}
	
	public static void write()
	{	
		Database db=new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		db.connect();
		
		db.write(new Account("Adam","admin"));
		db.write(new Account("Zjef","admin"));	
		db.write(new Account("Matthias","admin"));	
		db.write(new Account("Alexander","admin"));	
		
		db.disconnect();
	}
	
	public static void read()
	{
		Database db=new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		db.connect();

		Vector<Account> vec=db.readAll(new Search(Account.class));

		System.out.println("List of accounts in database:");
		for(Account acc:vec) 
		{ 
			System.out.println(acc.getUserName());
		} 		
		System.out.println("Result of search on 'Adam'");
		Search s = new Search(Account.class,"getUserName;getPassword","Adam","admin");
		Account acc = db.read(s);
		if (acc!=null){System.out.println(acc.getUserName());}
		db.disconnect();
	}
	
	public static void clean()
	{
		Database db=new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		db.connect();
		
		db.deleteTable(Account.class);
		
		db.disconnect();
	}
}
