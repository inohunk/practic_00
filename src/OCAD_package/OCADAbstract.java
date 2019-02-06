package OCAD_package;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/***
 * @author hunkel
 * @version 0.1.0
 * Abstract OCAD Class
 */
public abstract class OCADAbstract implements OCADCreator {

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

    //Symbols
    private Map<Integer,Integer> symbols = new HashMap<Integer, Integer>();
    public int  getSymbolID(int index)
    {
        return buffer.getInt(buffer.getInt(index)+4);
    }
    public void getData() {}

    public Object test(int index) {
        return symbols.get(index);
    }

    public int getNumberOfCharacters()
    {
        int index = getFirstSymbolIndexBlock()+4;
        int nextBlock = getFirstSymbolIndexBlock();
        int count=0;
        boolean active = true;
        while(active)
        {
            if(buffer.getInt(nextBlock)!=0) {
                nextBlock = buffer.getInt(nextBlock);
                int i = 0;
                while (i++ < 256)
                {
                    Integer indexValue = getSymbolID(index);
                    symbols.put(index,indexValue);
                    count++;
                    index+=4;
                }
            } else {
                int i = 0;
                index = nextBlock + 4;
                while (i++ < 256 && buffer.getInt(index)!=0)
                {
                    Integer indexValue = getSymbolID(index);
                    symbols.put(index,indexValue);
                    count++;
                    index+=4;
                }
                active = false;
            }
        }
        return count;
    }

}