package view;

import java.util.ArrayList;
import java.util.List;

import Controller.CLIController;

public class VT99 {
    private static VT99 vt99;

    // ANSI COMMANDS
    public static final String ANSI_ESC = "\033[";
    public static final String clearScreen = ANSI_ESC + "2J";
    public static final String clearLine = ANSI_ESC + "2K";

    private List<String> topList;
    private List<String> botList;

    private Integer topHeight;
    private Integer botHeight;
    private Integer gapPosition;
    private Integer topStart;
    private Integer botStart;
    private Integer inputPosition;
    private boolean errorsTop;

    private Integer topListPosition;
    private Integer botListPosition;
    
	private VT99(){}
    public VT99(Integer lineNumber, String ratio, String workspaces) {
        Integer topRatio = Integer.parseInt(ratio.split(":")[0]);
        Integer botRatio = Integer.parseInt(ratio.split(":")[1]);
        this.topHeight = ((lineNumber - 2) * topRatio) / (topRatio + botRatio);
        this.botHeight = ((lineNumber - 2) * botRatio) / (topRatio + botRatio);
        this.topStart = 0;
        this.inputPosition = topHeight + 1;
        this.gapPosition = topHeight + 2;
        this.botStart = topHeight + 3;
        this.topList = new ArrayList<String>();
        this.botList = new ArrayList<String>();
        this.topListPosition = 0;
        this.botListPosition = 0;

        if(workspaces.equals("R:P"))
        {
            inputPosition = topHeight + 1;
            errorsTop = false;
        }
        else if(workspaces.equals("P:R"))
        {
            inputPosition = botHeight + 1;
            errorsTop = true;
        }
        
        printInterface();
    }

    public static void setInstance(Integer lineNumber, String ratio, String workspaces)
    {
        vt99 = new VT99(lineNumber, ratio, workspaces);
    }
    public static VT99 getInstance()
    {
        return vt99;
    }


    public void getUserInput()
    {
        while(true)
        {
            postaviNaUnos();
            System.out.println(clearLine);
            postaviNaUnos();
            String string = System.console().readLine();
            if(string.contains("scroll"))
            {
                String[] split = string.split(" ");
                if(split.length == 4)
                {
                    if(split[2].equals("up"))
                        scrollUp(split[1], Integer.parseInt(split[3]));
                    else if(split[2].equals("down"))
                        scrollDown(split[1], Integer.parseInt(split[3]));
                }
                else if(split.length == 3)
                {
                    if(split[2].equals("start"))
                        scrollToStart(split[1]);
                    else if(split[2].equals("end"))
                        scrollToEnd(split[1]);
                }
            }
            else if (string.equals("Q"))
                break;
            else
                CLIController.getInstance().interpretCommand(string);
        }
    }

    public void writeError(String error)
    {
        if(errorsTop)
            addTop(error);
        else
            addBot(error);
    }

    public void writeLine(String line)
    {
        if(errorsTop)
            addBot(line);
        else
            addTop(line);
    }

    void postavi(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    void postaviNaUnos()
    {
        postavi(inputPosition, 0);
        System.out.print("Unesite komandu: ");
    }

    void addToList(String string, List<String> list)
    {
        list.add(string);
        if(list == topList)
            topListPosition = topList.size() -1;
        else if(list == botList)
            botListPosition = botList.size() -1;
    }

    void addTop(String string)
    {
        addToList(string, topList);
        printInterface();
    }

    void addBot(String string)
    {
        addToList(string, botList);
        printInterface();
    }

    void printInterface()
    {
        System.out.println(clearScreen);
        printTop();
        printGap();
        printBot();
        postaviNaUnos();
    }

    void printTop()
    {
        postavi(topStart, 0);
        if(topList.size() <= topHeight)
        {
            if(!topList.isEmpty())
                for(String string : topList)
                    System.out.println(string);
        }
        else
        {
            for(int i = topHeight-1; i >= 0; i--)
                System.out.println(topList.get(topListPosition-i));
        }
    }

    void printGap()
    {
        postavi(gapPosition, 0);
        for(int i = 0; i < 100; i++)
            System.out.print("-");
    }

    void printBot()
    {
        postavi(botStart, 0);
        if(botList.size() <= botHeight)
        {
            if(!botList.isEmpty())
                for(String string : botList)
                    System.out.println(string);
        }
        else
        {
            for(int i = botHeight-1; i >= 0; i--)
                System.out.println(botList.get(botListPosition-i));
        }

    }

    void scrollUp(String workspace, Integer number)
    {
        if(workspace.equals("top"))
        {
            number = Math.abs(number);
            if(topListPosition - number >= 0 && topListPosition - number >= topHeight)
                topListPosition -= number;
            else
                topListPosition = topHeight;
            
        }
        else if(workspace.equals("bot"))
        {
            if(botListPosition - number >= 0 && botListPosition - number >= botHeight)
                botListPosition -= number;
            else
                botListPosition = botHeight;
        }
        printInterface();
    }

    void scrollDown(String workspace, Integer number)
    {
        if(workspace.equals("top"))
        {
            number = Math.abs(number);
            if(topListPosition + number < topList.size() && topListPosition + number <= topList.size())
                topListPosition += number;
            else
                topListPosition = topList.size() - 1;
        }
        else if(workspace.equals("bot"))
        {
            if(botListPosition + number < botList.size() && botListPosition + number <= botList.size())
                botListPosition += number;
            else
                botListPosition = botList.size() - 1;
        }
        printInterface();

    }

    void scrollToEnd(String workspace)
    {
        if(workspace.equals("top"))
            topListPosition = topList.size() - 1;
        else if(workspace.equals("bot"))
            botListPosition = botList.size() - 1;
            printInterface();
        
    }

    void scrollToStart(String workspace)
    {
        if(workspace.equals("top"))
            topListPosition = topHeight;
        else if(workspace.equals("bot"))
            botListPosition = botHeight;
        printInterface();
    }
}