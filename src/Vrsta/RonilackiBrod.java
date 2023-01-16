package Vrsta;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Brod;
import view.VT99;

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
            VT99.getInstance().writeLine("Brod: " + brod.getId() +" je Ronilacki Brod!");
            VT99.getInstance().writeLine(
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
