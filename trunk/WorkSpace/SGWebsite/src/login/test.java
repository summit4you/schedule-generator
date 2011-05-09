package login;

import dataStructure.Student;

public class test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Account myAccount = new Account();
		Student myStudent = new Student(89898989,"Alexander","De Cock");
		myAccount.setData(myStudent);
		myStudent=Student.class.cast(myAccount.getData());
		System.out.println(myStudent.getFirstName().toString());
	}

}
