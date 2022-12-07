package Visitor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import models.Vez;
import store.StoreSingleton;

public class VezVisitor {
    private static VezVisitor vezVisitor;
    private Map<String, Integer> map = new HashMap<String,Integer>();
    private LocalDateTime time;



	private VezVisitor(){}

	public static VezVisitor getInstance()
	{
		if (vezVisitor == null)
		{
			vezVisitor = new VezVisitor();
		}
		return vezVisitor;
	}

    public void visitPUVez(Vez vez)
    {
        if(StoreSingleton.getInstance().vezZauzetUVrijeme(vez, time))
            map.put("PU", map.get("PU")+1);
    }

    public void visitPOVez(Vez vez)
    {
        if(StoreSingleton.getInstance().vezZauzetUVrijeme(vez, time))
            map.put("PO", map.get("PO")+1);
    }

    public void visitOSVez(Vez vez)
    {
        if(StoreSingleton.getInstance().vezZauzetUVrijeme(vez, time))
            map.put("OS", map.get("OS")+1);
    }

    public Map<String, Integer> getMap() {
        return map;
    }
    
    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    public void clearMap() {
        this.map.clear();
        map.put("PU", 0);
        map.put("PO", 0);
        map.put("OS", 0);
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    
    
}
