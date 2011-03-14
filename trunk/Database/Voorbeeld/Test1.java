package database;

import java.util.Vector;

/**
 * Voorbeeld klasse om Databasable v1.0 te implementeren
 * @author Zjef
 * @version 1.0
 */
public class Test1 implements Databasable
{
	/**
	 * Maak altijd een parameter van de klasse ID aan.
	 */
	private ID id;
	
	private Vector<Test2> tests;
	private String name;
	private boolean updated;
	
	/**
	 * Constructor zonder parameters, nodig voor Databasable
	 */
	public Test1()
	{
		tests=new Vector<Test2>(); // er mogen wel dingen in gebeuren voor de initialisatie bijvoorbeeld.
		updated=false;
	}
	
	/**
	 * Eigen constructor, hiermee doe je wat je wil
	 */
	public Test1(String name)
	{
		this.name=name;
		tests=new Vector<Test2>();
		updated=false;
	}
	
	/**
	 * Dit is een getter voor de parameter name.
	 * Deze parameter moet in de database worden opgeslagen, dus annoteer je de methode met @Indatabase
	 * 
	 * String parameters zijn beperkt tot 255 karakters.
	 */
	@InDatabase
	public String getName()
	{
		return name;
	}
	
	/**
	 * Dit is de setter voor de parameter name; dus annoteren met @OutDatabase
	 * De setter heeft moet dezelfde naam (na het voorvoegsel get/set/is) hebben als de getter.
	 */
	@OutDatabase
	public void setName(String name)
	{
		this.name=name;
	}
	
	/**
	 * Als een de bepaalde parameter niet in de database moet worden opgeslagen,
	 * dan annoteer je de (eventuele) getter en setter ervan simpelwel niet met InDatabase en OutDatabase
	 */
	public boolean isUpdated()
	{
		return updated;
	}
	/**
	 * Idem, geen annotatie
	 */
	public void setUpdated(boolean updated)
	{
		this.updated=updated;
	}
	
	/**
	 * getter = Indatabase
	 */
	@InDatabase
	public Vector<Test2> getTests()
	{
		return (Vector<Test2>) tests.clone();
	}
	
	/**
	 * setter met een vector als parameter. Hiervoor moet je in de annotatie OutDatabase ook de klasse meegeven van de parameters in de vector
	 * Hier dus Test1.class
	 * Hetzelfde geldt voor de standaard types: String.class,Integer.class, etc. Zie de javadoc van OutDatabase voor de toegelaten types.
	 * 
	 * Merk op dat de waarde in de vector opgeslagen worden in een string in 1 kolom. Deze is 65536 karakters breed.
	 * Houd er dus rekening mee dat er niet oneindig veel elementen in opgeslagen kunnen worden.
	 * De waarden worden gescheiden door ';', dus gebruik geen ';' als in de Strings van de vector.
	 * Databasable objecten in de vector worden voorgesteld door hun ID, een integer (deze begint bij 1 en incrementeert bij het toevoegen van nieuwe waarden)
	 */
	@OutDatabase(Test2.class)
	public void setTests(Vector<Test2> tests)
	{
		this.tests.removeAllElements();
		for (Test2 i:tests)
		{
			addTest(i);
		}
	}
	
	/**
	 * Niets voor in de database, dus ook geen annotatie.
	 * Vormt met Test2 een oneindig lusje, zie ook de commentaar bij Test2
	 */
	public void addTest(Test2 test)
	{
		tests.add(test);
		test.setParent(this);
	}
	
	/**
	 * Deze methode komt van de interface Databasable.
	 * Zorg ervoor dat je hier de in het begin aangemaakte parameter van het type ID teruggeeft
	 */
	@Override
	public ID getId()
	{
		return id;
	}

	/**
	 * Hier stel je de aangemaakte parameter in.
	 */
	@Override
	public void setID(ID id)
	{
		this.id=id;
	}
}