package store;

public class Store {
    private static Store store;

	private Store()
	{
	}
	
	public static Store getInstance()
	{
		if (store == null)
		{
			store = new Store();
		}
		return store;
	}

}
