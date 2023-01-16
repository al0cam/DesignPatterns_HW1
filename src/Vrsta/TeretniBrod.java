package Vrsta;

import models.Brod;
import view.VT99;

public class TeretniBrod extends VrstaHandler {
    private static VrstaHandler instance;

	private TeretniBrod(){
        this.nextHandler = Jahta.getInstance();
    }

	public static VrstaHandler getInstance()
	{
		if (instance == null)
		{
			instance = new TeretniBrod();
		}
		return instance;
	}
    @Override
    public void handle(Brod brod) {
        if(brod.getVrsta().equals("TE"))
        {
            VT99.getInstance().writeLine("Brod: " + brod.getId() +" je Teretni Brod!");
            VT99.getInstance().writeLine(
                """

                                .
                                .                 |
                                +                 |
                    .        |                *+W+-*
            .           +y        +W+              . H                 .
            .  +y            |I.   y  |               ! H= .           .  ^
            !   \\     .     |H '. /   |  ___.        .! H  !   +--.--y !  V
            !    \\     \\  +=|H|=='.=+ | |====\\   _  '_H_H__H_. H_/=  J !  !
            . !     \\'    VVV_HHH_/__'._H |  E  \\_|=|_|========|_|==|____H. ! _______.
            I-H_I=I=HH_==_|I_IIIII_I_I_=HH|======.I-I-I-=======-I=I=I=I_=H|=H'===I=I/
            \\                                                                      ,
            |                                                                    /
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """);
        }
        else
            this.nextHandler.handle(brod);
        
    }    
}
