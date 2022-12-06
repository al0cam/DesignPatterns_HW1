package Vrsta;

import models.Brod;

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
            System.out.println("Brod: " + brod.getId() +" je Trajekt!");
            System.out.println(
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
