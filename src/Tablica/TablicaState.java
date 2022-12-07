package Tablica;

import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;

public class TablicaState {
    private static TablicaState tablicaState;
    private boolean header = false;
    private boolean footer = false;
    private boolean ordinalNumbers = false;

	private TablicaState(){}

	public static TablicaState getInstance()
	{
		if (tablicaState == null)
		{
			tablicaState = new TablicaState();
		}
		return tablicaState;
	}

    private void findOption(String option) throws Exception
	{   
		switch (option.trim()) {
            case "Z":
                header = true;
                return;
            case "P":
                footer = true;
                return;
            case "RB":
                ordinalNumbers = true;
                return;
			default:
                throw new Exception("No option: "+option +" for command T");
		}
	}

    public void alterState(String option1, String option2, String option3)
    {
        if(option1 == null && option2 == null && option3 == null)
        {
            header = false;
            footer = false;
            ordinalNumbers = false;
            return;
        }
        if(option1 != null)
            try {
                findOption(option1);
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().catchGeneralError(e);
            }
        if(option2 != null)
            try {
                findOption(option2);
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().catchGeneralError(e);
            }
        if(option3 != null)
            try {
                findOption(option3);
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().catchGeneralError(e);
            }
    }


    public void print(String headerTitle, String columns, List<String> content)
    {
        if(header)
        {
            System.out.println(String.format("%130s","").replace(" ","_"));
            if(headerTitle != null)
                System.out.println("\nTablica - "+headerTitle);
            else
            System.out.println("\nTablica - Bez naslova");
            System.out.println(String.format("%130s","").replace(" ","_"));
            if(ordinalNumbers)
                System.out.println(String.format("%1$5s |", "rb")+columns);
            else 
                System.out.println(columns);
        }
        if(ordinalNumbers)
        {
            Integer index = 1;
            for (String string : content) {
                System.out.println(String.format("%1$5s |", index++)+string);
            }
        }
        else
            for (String string : content) 
                System.out.println(string);
            
        if(footer)
        {
            System.out.println(String.format("%130s","").replace(" ","_"));
            System.out.println("\nTotal: "+content.size());
            System.out.println(String.format("%130s","").replace(" ","_"));
        }
    }
    

}
