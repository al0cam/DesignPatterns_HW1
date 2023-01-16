package Vrsta;

import models.Brod;
import view.VT99;

public class Katamaran extends VrstaHandler {
    private static VrstaHandler instance;

	private Katamaran(){
        this.nextHandler = PutnickiBrod.getInstance();
    }

	public static VrstaHandler getInstance()
	{
		if (instance == null)
		{
			instance = new Katamaran();
		}
		return instance;
	}
    
    @Override
    public void handle(Brod brod) {
        if(brod.getVrsta().equals("KA"))
        {
            VT99.getInstance().writeLine("Brod: " + brod.getId() +" je Katamaran!");
            VT99.getInstance().writeLine(
                """
                    /|\\
                    /__| )
                  /____| ))
                /______| )))
              /________|  )))
                      _|____))
              \\======| o o /
             ~~~~~~~~~~~~~~~~~~~
                """);
        }
        else
            this.nextHandler.handle(brod);
        
    }    
}
