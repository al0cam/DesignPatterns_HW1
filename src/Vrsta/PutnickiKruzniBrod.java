package Vrsta;

import models.Brod;

public class PutnickiKruzniBrod extends VrstaHandler {
    private static VrstaHandler instance;

	private PutnickiKruzniBrod(){
        this.nextHandler = Ribarica.getInstance();
    }

	public static VrstaHandler getInstance()
	{
		if (instance == null)
		{
			instance = new PutnickiKruzniBrod();
		}
		return instance;
	}
    @Override
    public void handle(Brod brod) {
        if(brod.getVrsta().equals("KR"))
        {
            System.out.println("Brod: " + brod.getId() +" je Putnicki Kruzni Brod!");
            System.out.println(
                """
                             ________________________           
                            /  /___/  /___/  /___/  /
                           /  ____   ____   ____   /           
                    ______/  /___/  /___/  /___/  /__________
                    \\ =  : : : : : : : : : : : : : : : : :  /
                   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """);
        }
        else
            this.nextHandler.handle(brod);
        
    }    
}