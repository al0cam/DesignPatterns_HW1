package Vrsta;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Brod;

public class RonilackiBrod extends VrstaHandler {
    private static VrstaHandler instance;

	private RonilackiBrod(){
    }

	public static VrstaHandler getInstance()
	{
		if (instance == null)
		{
			instance = new RonilackiBrod();
		}
		return instance;
	}
    @Override
    public void handle(Brod brod) {
        if(brod.getVrsta().equals("RO"))
        {
            System.out.println("Brod: " + brod.getId() +" je Ronilacki Brod!");
            System.out.println(
                """
                           
                _|________P
            ___/ O O |___|___
            \\               |
          ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """);
        }
        else
            ErrorCatcherSingleton.getInstance().catchCustomError("No brod with vrsta: "+brod.getVrsta());
        
    }    
}
