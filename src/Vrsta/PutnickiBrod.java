package Vrsta;

import models.Brod;
import view.VT99;

public class PutnickiBrod extends VrstaHandler {
    private static VrstaHandler instance;

	private PutnickiBrod(){
        this.nextHandler = PutnickiKruzniBrod.getInstance();
    }

	public static VrstaHandler getInstance()
	{
		if (instance == null)
		{
			instance = new PutnickiBrod();
		}
		return instance;
	}
    @Override
    public void handle(Brod brod) {
        if(brod.getVrsta().equals("KL"))
        {
            VT99.getInstance().writeLine("Brod: " + brod.getId() +" je Putnicki Brod!");
            VT99.getInstance().writeLine(
                """
                           ________________________  
                    ______/  /___/  /___/  /___/  |
                    \\ =  : : : : : : : : : : : : /
                   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """);
        }
        else
            this.nextHandler.handle(brod);
        
    }    
}
