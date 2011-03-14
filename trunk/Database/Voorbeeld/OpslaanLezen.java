package database;

import java.util.Vector;

/**
 * Voorbeeld om te lezen, schrijven, delete
 * @author Zjef
 * @version 1.0
 */
public class OpslaanLezen
{
	/**
	 * 
	 * Lees eerst even Test1 en Test2 door om te zien hoe klasses Databasable moeten implementeren.
	 * 
	 */
	
	public static void main(String[] args)
	{
		//run om te testen of alles werkt
		//benodigdheden: JDBC driver
		delete();
		opslaan();
		lezen();
	}
	
	public static void opslaan()
	{
		//aanmaken van Databasable objecten
		Test1 test1=new Test1("naam");
		Test2 test2=new Test2(20);
		test1.addTest(test2);	//test2 wordt toegevoegd aan test1
		
		//connectie database
		Database db=new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		db.connect();
		
		db.write(test1);	//test1 is nu opgeslagen in de database. 
		//test1 heeft test2 als parameter, maar hiervan is enkel de referentie opgeslagen, niet het object zelf.
		//Dus moet ook test2 expliciet worden opgeslagen:
		db.write(test2);
		
		db.disconnect();
	}
	
	public static void lezen()
	{
		Database db=new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		db.connect();
		
		//We gaan nu test1 weer uitlezen uit de database, hiervoor moet een Search criterium aangemaakt worden:
		Search search=new Search(Test1.class,"getName","naam"); //eerst geven we de klasse van het te zoeken object, daarna de zoekcriteria
		//Eerst zijn dit de getters van de parameters die we willen zoeken, daarna de waarden die deze parameters moeten hebben.
		//Meerdere criteria doe je zo: new Search(class,"getter1;getter2;...",object1,object2,...)
		
		Test1 test1=db.read(search);	//Uit de database is nu het element gehaald waarvan de 'Name' "naam" is.
		//Bij het laden worden wél automatisch de andere Databasable objecten ingeladen.
		//Dus het object test2 dat we eerder toegekend hadden aan test1 is nu al automatisch geladen.
		
		//Merk op dat de parent van test2 test1 zou moeten zijn.
		//De database zal exact dezelfde objecten terug geven, niet enkel objecten met dezelfde inhoud, maar fysiek dezelfde objecten.
		System.out.println(test1.getTests().get(0).getParent()==test1);	//dit moet dus true geven
		
		Test2 test2=db.read(new Search(Test2.class,"getParent",test1));	//We gaan nu het object zoeken van Test2, waarvan de parent test1 is
		//Let op; dit gaat enkel wanneer het object test1 al een ID heeft; ID's worden toegekend nadat je een write(object) hebt gedaan
		//of wanneer je het object het verkregen via een object=read();
		
		//test2 zou nu identiek moeten zijn aan de eerder geladen parameter van test1
		System.out.println(test2==test1.getTests().get(0));	//Zou ook weer true moeten zijn.
		
		//De database houdt een cache bij van de geladen objecten. Als je een object voor een tweede keer wil laden,
		//dan wordt het eerder geladen object teruggegeven.
		//Vb: een Educator heeft een lijst met Courses, een Course heeft een Educator.
		//Als je nu eerst alle Courses laadt, dan worden ook hun Educators mee uitgelezen.
		//Wanneer je nu hierna echter ook alle Educators uitleest, dan worden de objecten die al geladen werden terug gegeven.
		//Dit heeft als voordeel dat die objecten fysiek hetzelfde zijn.
		//Je kan echter zelf de cache leeg maken door: db.clearCache();
		
		
		//We kunnen ook alle elementen uit een bepaalde tabel halen, dan doe je hetzelfde alleen maek je volgende Search aan:
		Search s=new Search(Test1.class); 	//enkel de class specifiëren dus.
		Vector<Test1> vec=db.readAll(s);
		
		//readAll geeft alle elementen terug die voldoen aan je search, read enkel het eerste dat de database teruggeeft.
		
		db.disconnect();
	}
	
	public static void delete()
	{
		//objecten kunnen ook verwijderd worden uit een tabel. Hiervoor moet je wel een object hebben dat je eerder uit de database gehaald hebt.
		Database db=new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		db.connect();
		
		Search search=new Search(Test1.class,"getName","naam"); 
		Test1 test1=db.read(search);
		
		db.delete(test1);
		
		//Als je aanpassingen hebt gedaan aan de klasse, die de database beïnvloedt (extra parameter, of parameter verwijderd), 
		//dan moet je de hele tabel verwijderen, vooralleer je met de vernieuwde klasse kan werken
		db.deleteTable(Test1.class);
		db.deleteTable(Test2.class);
		
		db.disconnect();
	}
}