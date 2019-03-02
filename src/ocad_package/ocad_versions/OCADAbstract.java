package ocad_package.ocad_versions;

import ocad_package.symbols.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/***
 * @author hunkel
 * @version 0.1.0
 * Abstract OCAD Class
 */
public abstract class OCADAbstract{

    protected ByteBuffer buffer = null;
    public OCADAbstract(ByteBuffer buf)
    {
        this.buffer = buf;
        getNumberOfCharacters();
    }


    //File Header
    public int getOcadMark() { return buffer.getShort(0);}

    public int getFileType(){ return buffer.get(2);}

    public int getFileStatus() { return buffer.get(3);}

    public int getVersion() {
        return buffer.getShort(4);
    }

    public int getSubVersion() { return buffer.get(6);}

    public int getSubSubVersion() { return buffer.get(7);}

    public int getFirstSymbolIndexBlock() { return buffer.getInt(8);}

    public int getFirstObjectBlock() { return buffer.getInt(12);}

    public int getOfflineSyncSerial() { return  buffer.getInt(16);}

    public int getCurrentFileVersion() { return buffer.getInt(20);}

    public int getFirstStringIndexBlock() { return buffer.getInt(32);}

    public int getFileNamePosition()
    {
        return buffer.getInt(36);
    }

    public int getFileNameSize() { return buffer.getInt(40); }


    //Symbols
    private ArrayList<BaseSymbol> symbols = new ArrayList<>();

    private int getSymbolSize(int index) { return buffer.getInt(index);}
    private int getSymbolNumber(int index) { return buffer.getInt(index+4);}
    private int getSymbolOTP(int index) { return buffer.get(index+8);}
    private int getSymbolFlags(int index) { return buffer.get(index+9);}
    private boolean getSymbolSelected(int index)
    {
        byte var = buffer.get(index+10);

        return true && var != 0;
    }
    private int getSymbolStatus(int index) { return buffer.get(index+11);}
    private int getSymbolPreferredDrawingTool(int index) { return buffer.get(index+12);}
    private int getCsMode(int index) {return buffer.get(index+13);}
    private int getCsObjectType(int index) {return buffer.get(index+14);}
    private int getCsCdFlags(int index) { return buffer.get(index+15);}
    private int getExtent(int index) { return buffer.getInt(index+16);}
    private int getFilePosition(int index) { return buffer.get(index+20);}
    private int getNumberOfColors(int index) { return buffer.getInt(index+26);}

    public ArrayList<BaseSymbol> getSymbols() {return symbols;}
    private int counter = 0;
    private void addDataOfSymbols(int startIndex)
    {
        counter++;
        BaseSymbol sym = symbolCreator(getSymbolOTP(startIndex));
        //sym.setParameters(getSymbolSize(startIndex),getSymbolNumber(startIndex),getSymbolOTP(startIndex),getSymbolFlags(startIndex));
        sym.setSize(getSymbolSize(startIndex));
        sym.setNumber(getSymbolNumber(startIndex));
        sym.setOtp((byte) getSymbolOTP(startIndex));
        sym.setFlags((byte) getSymbolFlags(startIndex));
        //sym.setSelected(getSymbolSelected(startIndex));
        sym.setStatus((byte) getSymbolStatus(startIndex));
        sym.setPrefferedDrawingTool((byte) getSymbolPreferredDrawingTool(startIndex));
        sym.setCsMode((byte) getCsMode(startIndex));
        sym.setCsObjectType((byte) getCsObjectType(startIndex));
        sym.setCsCdFlags((byte) getCsCdFlags(startIndex));
        sym.setExtend(getExtent(startIndex));
        sym.setFilePos(getFilePosition(startIndex));
        sym.setnColors((short) getNumberOfColors(startIndex));
        System.out.println("=========" + counter + "=========");
        System.out.println("Size: "+sym.getSize());
        System.out.println("Number: "+sym.getNumber());
        System.out.println("OTP: "+sym.getOtp());
        System.out.println("Flags: "+sym.getFlags());
        System.out.println("Status: "+sym.getStatus());
        System.out.println("PrefferedDrawing: "+sym.getPrefferedDrawingTool());
        System.out.println("CsMode: "+sym.getCsMode());
        System.out.println("CsObjectType: "+sym.getCsObjectType());
        System.out.println("CsCdFlags: "+sym.getCsCdFlags());
        System.out.println("Extend: "+sym.getExtend());
        System.out.println("FilePos: "+sym.getFilePos());
        System.out.println("Number of Colors: "+sym.getnColors());

        symbols.add(sym);

    }

    public int getNumberOfCharacters()
    {

        int index = getFirstSymbolIndexBlock()+4;
        int nextSym = getFirstSymbolIndexBlock();
        int count=0;
        boolean active = true;
        while(active)
        {
            if(buffer.getInt(nextSym)!=0) {
                nextSym = buffer.getInt(nextSym);
                int i = 0;
                while (i++ < 256)
                {
                    addDataOfSymbols(buffer.getInt(index));

                    //Integer indexValue = getSymbolID(index);
                    //symbols.put(index,indexValue);
                    count++;
                    index+=4;
                }
            } else {
                int i = 0;
                index = nextSym + 4;
                while (i++ < 256 && buffer.getInt(index)!=0)
                {
                    addDataOfSymbols(buffer.getInt(index));
                    //Integer indexValue = getSymbolID(index);
                    //symbols.put(index,indexValue);
                    count++;
                    index+=4;
                }
                active = false;
            }
        }
        return count;
    }
    //public int getSymbolSize() {return }
    public void outSymbols()
    {
        try {
            for (int i = 0; i < symbols.size(); i++) {
                System.out.println(symbols.size());
                //SymbolManager baseSymbol = symbols.get(i);
                //System.out.println(baseSymbol.getSymNum());
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
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
            //System.out.println("Symbol created");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return symbol;
    }
    byte getOTP(int startIndex)
    {
        return buffer.get(buffer.get(startIndex)+5);
    }
    byte getFlags(int startIndex)
    {
        return buffer.get(buffer.get(startIndex)+6);
    }


    //Other

    private String hexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < hex.length() - 1; count += 2) {
            String output = hex.substring(count, (count + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char)decimal);
        }
        return sb.toString();
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



    public int test() {
        return buffer.getInt(40);
    }

    /*public int getNumberOfCharacters()
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
    }*/




}