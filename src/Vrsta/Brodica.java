package Vrsta;

import models.Brod;

public class Brodica extends VrstaHandler {
    private static VrstaHandler instance;

	private Brodica(){
        this.nextHandler = RonilackiBrod.getInstance();
    }

	public static VrstaHandler getInstance()
	{
		if (instance == null)
		{
			instance = new Brodica();
		}
		return instance;
	}
    @Override
    public void handle(Brod brod) {
        if(brod.getVrsta().equals("BR"))
        {
            System.out.println("Brod: " + brod.getId() +" je Brodica!");
            System.out.println(
                """
                          
                    _/________
            _______/_____\\___|___
            \\                   |
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """);
        }
        else
            this.nextHandler.handle(brod);
        
    }    
}
