package Vrsta;

import models.Brod;
import view.VT99;

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
            VT99.getInstance().writeLine("Brod: " + brod.getId() +" je Brodica!");
            VT99.getInstance().writeLine(
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
