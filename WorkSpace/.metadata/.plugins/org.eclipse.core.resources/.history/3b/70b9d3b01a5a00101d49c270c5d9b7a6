package database;

/**
 * Field entry for a table
 * @author Zjef
 * @version 1.0
 */
class Field implements Syntaxable
{
	private String name;
	private FieldType fieldType;
	private FieldDefault fieldDefault;
	
	public Field(String name,FieldType fieldType,FieldDefault fieldDefault)
	{
		this.name=name;
		this.fieldType=fieldType;
		this.fieldDefault=fieldDefault;
	}
	
	@Override
	public String getText()
	{
		return name+" "+fieldType.getText()+" "+fieldDefault.getText();
	}
}