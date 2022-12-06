package Vrsta;

import models.Brod;

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
            System.out.println("Brod: " + brod.getId() +" je Putnicki Brod!");
            System.out.println(
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
