package generators;

import java.util.Vector;

import pseudoServlets.StudentTable;

import login.*;
import dataStructure.*;

import database.Databasable;
import database.Database;
import database.Search;

public class FillDatabase
{
	public static void main(String[] args)
	{
		cleanall();
		write();
		read();
	}
	
	public static void write()
	{	
			
		
		Educator rolain = new Educator("Yves", "Rolain",1);
		Educator gerd = new Educator("Gerd", "Vandersteen",10);
		Educator pintelon = new Educator("Rik", "Pintelon",9);
		Educator ragnhild = new Educator("Ragnhild", "Verstraete",3);
		Educator leo = new Educator("Leo", "Van Biesen",2);
		Educator hugo = new Educator("Hugo", "Thienpont",0);
		
		Hardware bord = new Hardware("bord");
		Hardware beamer = new Hardware("beamer");
		Hardware projector = new Hardware("projector");
		Hardware spec = new Hardware("Spectrum Analyser");
		Hardware entertainement = new Hardware("wulpse vrouwen");

		Vector<Hardware> hardwarevector = new Vector<Hardware>();
		hardwarevector.add(bord);hardwarevector.add(entertainement);hardwarevector.add(spec);
		Vector<Educator> educatorvector = new Vector<Educator>();
		educatorvector.add(rolain);educatorvector.add(gerd);
		Subcourse HF = new Subcourse("Hoogfrequente theorie", 6, "cool vak", 8, 2, hardwarevector, educatorvector);
		educatorvector.clear(); educatorvector.add(hugo);
		hardwarevector.clear(); hardwarevector.add(bord);
		Subcourse Fo = new Subcourse("Fotonica theorie", 15, "Hugo is een zwans", 40, 2, hardwarevector, educatorvector);
		educatorvector.clear(); educatorvector.add(leo);
		Subcourse Signaal = new Subcourse("Signaaltheorie theorie", 6, "bla", 20, 2, hardwarevector, educatorvector);
		educatorvector.clear(); educatorvector.add(ragnhild);
		Subcourse SE = new Subcourse("Software Engineering theorie", 6, "het coolste vak ter wereld en ik hoop dat ragnhild dit leest", 20, 2, hardwarevector, educatorvector);
		educatorvector.clear(); educatorvector.add(pintelon);
		Subcourse controle = new Subcourse("Controletheorie", 6, "bla", 20, 2, hardwarevector, educatorvector);
		educatorvector.clear(); educatorvector.add(pintelon);
		hardwarevector.add(spec);
		Subcourse controlelabs = new Subcourse("Controletheorie labo", 6, "bla", 20, 2, hardwarevector, educatorvector);
		
		
		Vector<Subcourse> subcoursevector = new Vector<Subcourse>();
		subcoursevector.add(HF);
		Course hoogfreq = new Course("Hoogfrequente Electronica en Antennes", subcoursevector, rolain);
		subcoursevector.clear();subcoursevector.add(Fo);
		Course fotonica = new Course("Fotonica", subcoursevector, hugo);
		subcoursevector.clear();subcoursevector.add(controle);subcoursevector.add(controlelabs);
		Course controleth = new Course("Controletheorie", subcoursevector, pintelon);
		subcoursevector.clear();subcoursevector.add(SE);
		Course softwareengineering = new Course("Software Engineering", subcoursevector, ragnhild);
		subcoursevector.clear();subcoursevector.add(Signaal);
		Course signaaltheorie = new Course("Signaaltheorie", subcoursevector, leo);
		
		Vector<Course> coursevector = new Vector<Course>();
		coursevector.add(hoogfreq);coursevector.add(signaaltheorie);coursevector.add(controleth);
		Program EIT = new Program(4, "Electronica", coursevector);
		coursevector.clear();coursevector.add(softwareengineering);coursevector.add(fotonica);
		Program COMP = new Program(4, "Niet-electronica", coursevector);
		coursevector.clear();coursevector.add(softwareengineering);
		Program REST = new Program(4, "Nog iets anders", coursevector);
		
		coursevector.clear();coursevector.add(hoogfreq);
		rolain.setCourses(coursevector);
		coursevector.clear();coursevector.add(signaaltheorie);
		leo.setCourses(coursevector);
		coursevector.clear();coursevector.add(controleth);
		pintelon.setCourses(coursevector);
		coursevector.clear();coursevector.add(softwareengineering);coursevector.add(fotonica);
		ragnhild.setCourses(coursevector);
		
		subcoursevector.clear();subcoursevector.add(controle);
		pintelon.setSubcourses(subcoursevector);
		subcoursevector.clear();subcoursevector.add(controlelabs);subcoursevector.add(HF);
		gerd.setSubcourses(subcoursevector);
		subcoursevector.clear();subcoursevector.add(HF);
		rolain.setSubcourses(subcoursevector);
		subcoursevector.clear();subcoursevector.add(SE);
		ragnhild.setSubcourses(subcoursevector);
		subcoursevector.clear();subcoursevector.add(Signaal);
		leo.setSubcourses(subcoursevector);
		subcoursevector.clear();subcoursevector.add(Fo);
		hugo.setSubcourses(subcoursevector);
		
		Vector<Program> programvector = new Vector<Program>();
		programvector.add(EIT);programvector.add(COMP);
		educatorvector.clear();educatorvector.add(rolain);educatorvector.add(leo);educatorvector.add(pintelon);educatorvector.add(gerd);
		Faculty IR = new Faculty("IR",programvector,educatorvector);
		programvector.clear();programvector.add(REST);
		educatorvector.clear();educatorvector.add(hugo);educatorvector.add(ragnhild);
		Faculty WE = new Faculty("WE",programvector,educatorvector);
		
		Room k7 = new Room("K7",30);
		hardwarevector.clear();hardwarevector.add(entertainement);hardwarevector.add(bord);hardwarevector.add(spec);
		k7.setPresentHardware(hardwarevector);
		Room k8 = new Room("K8",20);
		hardwarevector.clear();hardwarevector.add(bord);
		k8.setPresentHardware(hardwarevector);
		Room k2 = new Room("K2",10);
		k2.setPresentHardware(hardwarevector);
		Room l1 = new Room("L1",20);
		l1.setPresentHardware(hardwarevector);
		Room d7 = new Room("0.07",8);
		d7.setPresentHardware(hardwarevector);
		Room d2 = new Room("0.02",100);
		d2.setPresentHardware(hardwarevector);
		
		Vector<Room> roomvector = new Vector<Room>();
		Building k = new Building("K");
		roomvector.add(k7);roomvector.add(k2);roomvector.add(k8);
		k.setRooms(roomvector);
		Building l = new Building("L");
		roomvector.clear();roomvector.add(l1);
		l.setRooms(roomvector);
		Building d = new Building("D");
		roomvector.clear();roomvector.add(d7);roomvector.add(d2);
		d.setRooms(roomvector);

		
		Student adam = new Student(1, "Adam", "Cooman");
		adam.addProgram(EIT);
		adam.addCourse(softwareengineering);
		
		Student zjef = new Student(2, "Zjef", "Van de Poel");
		zjef.addProgram(REST);
		zjef.addCourse(fotonica);
		
		Student alex = new Student(3, "Alexander", "De Cock");
		alex.addProgram(COMP);
		alex.addCourse(hoogfreq);
		
		Student matsi = new Student(4, "Matthias", "Caenepeel");
		matsi.addCourse(hoogfreq);
		matsi.addCourse(softwareengineering);
		
		
		
		Admin ad = new Admin();
		//Account ac1 = new Account(name, pass, language, student, educator, admin, type)
		Account ac1 = new Account("adam", "tolkien", "dutch", adam , null, null,new UserType("Student"));
		Account ac2 = new Account("zjef", "sertyuiop", "english", zjef, null, null,new UserType("Student"));
		Account ac3 = new Account("alex", "admin", "dutch", alex, null, null,new UserType("Student"));
		Account ac4 = new Account("koningcanis", "kak", "english", matsi, null, null,new UserType("Student"));
		Account ac5 = new Account("yves","EMrules","flenglish",null,rolain,null,new UserType("Educator"));
		Account ac6 = new Account("rik", "nummer9", "dutch", null, pintelon, null,new UserType("Educator"));
		Account ac7 = new Account("ragnhild","acasias", "dutch", null, ragnhild, null,new UserType("Educator"));
		Account ac8 = new Account("gerd", "eSYSID", "dutch", null, gerd, null,new UserType("Educator"));
		Account ac9 = new Account("leo", "aalstrules", "dutch", null, leo, null,new UserType("Educator"));
		Account ac10 = new Account("hugo", "fotonicasucks", "dutch", null, hugo, null,new UserType("Educator"));
		Account ac0 = new Account("admin", "admin", "english",null, null, ad,new UserType("Admin"));
		Account ac11 = new Account("guest","guest","english",null,null,null,new UserType("Guest"));
		
		Database db=new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		db.connect();
		db.write(ac1);
		db.write(ac2);
		db.write(ac3);
		db.write(ac4);
		db.write(ac5);
		db.write(ac6);
		db.write(ac7);
		db.write(ac8);
		db.write(ac9);
		db.write(ac10);
		db.write(ac11);
		db.write(ac0);
		db.write(k);
		db.write(l);
		db.write(d);
		db.write(IR);
		db.write(WE);
		db.disconnect();
		
		
	}
	
	public static void read()
	{
		
	}
	
	public static void clean()
	{
		Database db=new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		db.connect();
		db.deleteTable(Account.class);
		db.disconnect();
	}
	
	public static void cleanall()
	{
		Database db=new Database("wilma.vub.ac.be/se5_1011","se5_1011","nieveGroep");
		db.connect();
		db.deleteTable(Account.class);
		db.deleteTable(Student.class);
		db.deleteTable(Course.class);
		db.deleteTable(Subcourse.class);
		db.deleteTable(Educator.class);
		db.deleteTable(Program.class);
		db.deleteTable(Admin.class);
		db.deleteTable(Room.class);
		db.deleteTable(Building.class);
		db.deleteTable(Faculty.class);
		db.disconnect();
	}
	
}
