package ocad_package.symbols;

import ocad_package.interfaces.SymbolManager;

public abstract class BaseSymbol implements SymbolManager {

    int size;
    int number;
    byte otp;
    byte flags;
    boolean selected;
    byte status;
    byte prefferedDrawingTool;
    byte csMode;
    byte csObjectType;
    byte csCdFlags;
    int extend;
    int filePos;
    short nColors;
    short[] colors = new short[14];
    char[] description = new char[64];
    byte[] iconBits = new byte[484];
    //One more

    public void setParameters(int size, int symNum, int otp, int flags)
    {
        this.size = size;
        this.number = symNum;
        this.otp = (byte) otp;
        this.flags = (byte) flags;
    }

    public void setParameters(int size,int symNum)
    {
        this.size = size;
        this.number = symNum;
    }
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getSymNum() {
        return number;
    }

    @Override
    public byte getOTP() {
        return otp;
    }
}
