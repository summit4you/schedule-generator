package scheduleGenerator;

import dataStructure.Subcourse;


/**
 * 
 * @author matthiascaenepeel
 * @version0.1
 */

public class Node 
{
	private Subcourseblock block;
	private int placeInSchedule;
	private Node parent;
	
	int number;
	
	Node()
	{
		
	}

	public Node(int nr,Subcourseblock block, int placeInSchedule, Node parent)
	{
		super();
		this.block = block;
		this.placeInSchedule = placeInSchedule;
		this.parent = parent;
		this.number=nr;
		
	}

	public Subcourseblock getBlock() 
	{
		return block;
	}

	public void setBlock(Subcourseblock block) 
	{
		this.block = block;
	}

	public int getPlaceInSchedule() 
	{
		return placeInSchedule;
	}

	public void setPlaceInSchedule(int placeInSchedule)
	{
		this.placeInSchedule = placeInSchedule;
	}	
	
	public Node getParent() 
	{
		return parent;
	}
}