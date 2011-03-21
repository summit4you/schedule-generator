package visibilityModifiers;

import database.Databasable;
import database.Database;
import database.ID;

/**
 * @author Zjef
 * @version 1.0
 */
public class DatabaseModifier extends Database
{
	public DatabaseModifier(String url, String name, String password)
	{
		super(url, name, password);
	}

	@Override
	public Databasable getFromCache(Class<? extends Databasable> cl,ID id)
	{
		return super.getFromCache(cl, id);
	}
}