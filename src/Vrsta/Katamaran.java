package Vrsta;

import models.Brod;

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
            System.out.println("Brod: " + brod.getId() +" je Katamaran!");
            System.out.println(
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
