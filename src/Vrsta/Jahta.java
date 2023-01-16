package Vrsta;

import models.Brod;
import view.VT99;

public class Jahta extends VrstaHandler {
    private static VrstaHandler instance;

	private Jahta(){
        this.nextHandler = Brodica.getInstance();
    }

	public static VrstaHandler getInstance()
	{
		if (instance == null)
		{
			instance = new Jahta();
		}
		return instance;
	}
    @Override
    public void handle(Brod brod) {
        if(brod.getVrsta().equals("JA"))
        {
            VT99.getInstance().writeLine("Brod: " + brod.getId() +" je Jahta!");
            VT99.getInstance().writeLine(
                """
                                                   )___(
                                            _______/__/_
                                ___       /===========|   ___
            ____       __   [\\\\\\]___/____________|__[///]  __
            \\   \\_____[\\\\]__/___________________________\\__[//]___
            \\40A                     `                            |
            \\                                                    /
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """);
        }
        else
            this.nextHandler.handle(brod);
        
    }    
}
