package Vrsta;

import models.Brod;

public class Ribarica extends VrstaHandler {
    private static VrstaHandler instance;

	private Ribarica(){
        this.nextHandler = TeretniBrod.getInstance();
    }

	public static VrstaHandler getInstance()
	{
		if (instance == null)
		{
			instance = new Ribarica();
		}
		return instance;
	}
    @Override
    public void handle(Brod brod) {
        if(brod.getVrsta().equals("RI"))
        {
            System.out.println("Brod: " + brod.getId() +" je Ribarica!");
            System.out.println(
                """

              _ 
              \\    _____
               \\  |  _  |     ____
             ___\\_| |_| |____|    / 
            \\                    /
             \\                  /
             ~~~~~~~~~~~~~~~~~~~
                """);
        }
        else
            this.nextHandler.handle(brod);
        
    }    
}
