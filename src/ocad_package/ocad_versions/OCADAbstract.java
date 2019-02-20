package ocad_package.ocad_versions;

import ocad_package.interfaces.IOCADCreator;
import ocad_package.interfaces.SymbolManager;
import ocad_package.symbols.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/***
 * @author hunkel
 * @version 0.1.0
 * Abstract OCAD Class
 */
public abstract class OCADAbstract implements IOCADCreator {

    protected ByteBuffer buffer = null;
    public OCADAbstract(ByteBuffer buf)
    {
        this.buffer = buf;
    }

    //File Header
    public int getFileType(){ return buffer.get(2);}

    public int getFileStatus() { return buffer.get(3);}

    public int getVersion() {
        return buffer.getShort(4);
    }

    public int getSubVersion() { return buffer.get(6);}

    public int getSubSubVersion() { return buffer.get(7);}

    public int getFirstSymbolIndexBlock() { return buffer.getInt(8);}

    public int getFirstObjectBlock() { return buffer.getInt(12);}


    private String hexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < hex.length() - 1; count += 2) {
            String output = hex.substring(count, (count + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char)decimal);
        }
        return sb.toString();
    }

    public int getFileNamePosition()
    {
        return buffer.getInt(36);
    }
    public int getFileNameSize()
    {
        return buffer.getInt(40);
    }
    public String getFileName()
    {
        String source = "";
        /*System.out.println("Source is: " + source);
        System.out.println(getFileNamePosition());
        System.out.println(getFileNameSize());*/
        int pos=getFileNamePosition();
        for(int i=0;i<getFileNameSize();i++)
        {
            String num = Integer.toHexString(buffer.get(pos));
            source+=num;
            //System.out.println((i+1)+"("+pos+")"+" : " +buffer.get(pos)+" - " + hexToString(String.valueOf(num)));
            // += hexToString(String.valueOf(buffer.get(pos)));
            pos++;
        }
        source = hexToString(source);
        return source;
    }
    //Symbols
    //private Map<Integer,Integer> symbols = new HashMap<Integer, Integer>();
    private ArrayList<BaseSymbol> symbols = new ArrayList<>();

    public void outSymbols()
    {
        try {
            for (int i = 0; i < symbols.size(); i++) {
                System.out.println(symbols.size());
                SymbolManager baseSymbol = symbols.get(i);
                System.out.println(baseSymbol.getSymNum());
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public int  getSymbolID(int index)
    {
        return buffer.getInt(buffer.getInt(index)+4);
    }

    public int test() {
        return buffer.getInt(40);
    }

    public int getNumberOfCharacters()
    {
        int nextBlock = getFirstSymbolIndexBlock(); //Ссылка на следующий массив символов
        int index = getFirstSymbolIndexBlock()+4; //Ссылка на первый символ
        int count=0; //Счетчик символов
        boolean active = true;
        while(active)
        {
            if(buffer.getInt(nextBlock)!=0) {
                nextBlock = buffer.getInt(nextBlock);
                int i = 0;
                while (i++ < 256)
                {
                    Integer indexValue = getSymbolID(index);
                    SymbolManager baseSymbol = symbolCreator(getOTP(index));
                    baseSymbol.setParameters(1,2);
                    //
                    //baseSymbol.setParameters(getSymbolSize(index),getSymbolNumber(index),getOTP(index),getFlags(index));
                    //symbols.add((BaseSymbol) baseSymbol);
                    //symbols.put(index,indexValue);
                    //System.out.println(getSymbolSize(index));
                    //System.out.println(getFlags(index));
                    //System.out.println(getOTP(index));
                    //System.out.println(getFlags(index));
                    count++;
                    index+=4;
                }
            } else {
                int i = 0;
                index = nextBlock + 4;
                while (i++ < 256 && buffer.getInt(index)!=0)
                {
                    Integer indexValue = getSymbolID(index);
                    //symbols.put(index,indexValue);
                    count++;
                    index+=4;
                }
                active = false;
            }
        }
        return count;
    }
    private static BaseSymbol symbolCreator(int otp)
    {
        BaseSymbol symbol = null;
        try {

            switch (otp) {
                case 1:
                    symbol = new PointSymbol();
                    break;
                case 2:
                    symbol = new LineSymbol();
                    break;

                case 3:
                    symbol = new AreaSymbol();
                    break;
                case 4:
                    symbol = new TextSymbol();
                    break;
                case 6:
                    symbol = new LineTextSymbol();
                    break;
                case 7:
                    symbol = new RectangleSymbol();
                    break;
            }
            System.out.println("Symbol created");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return symbol;
    }
    int getSymbolSize(int startIndex)
    {
        return buffer.getInt(buffer.getInt(startIndex));
    }

    int getSymbolNumber(int startIndex)
    {
        return buffer.getInt(buffer.getInt(startIndex)+4);
    }
    byte getOTP(int startIndex)
    {
        return buffer.get(buffer.get(startIndex)+5);
    }
    byte getFlags(int startIndex)
    {
        return buffer.get(buffer.get(startIndex)+6);
    }


}