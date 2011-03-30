package planner;

import java.util.Vector;

import dataStructure2.*;

public class TestPlanner
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Subcourse testsub = new Subcourse("kak",10,"prot",5,2,new Hardware("scheet"),null);
		Vector<Subcourse> vec = new Vector<Subcourse>();
		vec.add(testsub);
		Course testcour = new Course("kak2",vec);
		Vector<Subcourseblock> vec2 = Subcourseblocksgenerator.generateBlocks(testcour);
		System.out.println(vec2.toString());

	}

}
