package DataTypes;

public abstract class BaseSymbol {
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

}
