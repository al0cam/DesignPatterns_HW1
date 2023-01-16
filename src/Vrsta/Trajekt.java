package Vrsta;

import models.Brod;
import view.VT99;

public class Trajekt extends VrstaHandler {
    private static VrstaHandler instance;

	private Trajekt(){
        this.nextHandler = Katamaran.getInstance();
    }

	public static VrstaHandler getInstance()
	{
		if (instance == null)
		{
			instance = new Trajekt();
		}
		return instance;
	}

    @Override
    public void handle(Brod brod) {
        if(brod.getVrsta().equals("TR"))
        {
            VT99.getInstance().writeLine("Brod: " + brod.getId() +" je Trajekt!");
            VT99.getInstance().writeLine(
                """
                                  __/___            
                            _____/______|           
                    _______/_____\\_______\\_______     
                    \\              < < <         /    
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """);
        }
        else
            this.nextHandler.handle(brod);
        
    }    
}
