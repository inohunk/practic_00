package ocad_package.symbols;

public abstract class BaseSymbol  {

    private int size;
    private int number;
    private byte otp;
    private byte flags;
    private boolean selected;
    private byte status;
    private byte prefferedDrawingTool;
    private byte csMode;
    private byte csObjectType;
    private byte csCdFlags;
    private int extend;
    private int filePos;
    private short nColors;
    short[] colors = new short[14];
    char[] description = new char[64];
    byte[] iconBits = new byte[484];
    //One more

    public void setParameters(int size, int symNum, int otp, int flags)
    {
        this.setSize(size);
        this.setNumber(symNum);
        this.setOtp((byte) otp);
        this.setFlags((byte) flags);
    }

    public void setParameters(int size,int symNum)
    {
        this.setSize(size);
        this.setNumber(symNum);
    }


    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() { return this.size;}

    public void setNumber(int number) {
        this.number = number;
    }

    public void setOtp(byte otp) {
        this.otp = otp;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public void setPrefferedDrawingTool(byte prefferedDrawingTool) {
        this.prefferedDrawingTool = prefferedDrawingTool;
    }

    public void setCsMode(byte csMode) {
        this.csMode = csMode;
    }

    public void setCsObjectType(byte csObjectType) {
        this.csObjectType = csObjectType;
    }

    public void setCsCdFlags(byte csCdFlags) {
        this.csCdFlags = csCdFlags;
    }

    public void setExtend(int extend) {
        this.extend = extend;
    }

    public void setFilePos(int filePos) {
        this.filePos = filePos;
    }

    public void setnColors(short nColors) {
        this.nColors = nColors;
    }

    public int getNumber() {
        return number;
    }

    public byte getOtp() {
        return otp;
    }

    public byte getFlags() {
        return flags;
    }

    public boolean isSelected() {
        return selected;
    }

    public byte getStatus() {
        return status;
    }

    public byte getPrefferedDrawingTool() {
        return prefferedDrawingTool;
    }

    public byte getCsMode() {
        return csMode;
    }

    public byte getCsObjectType() {
        return csObjectType;
    }

    public byte getCsCdFlags() {
        return csCdFlags;
    }

    public int getExtend() {
        return extend;
    }

    public int getFilePos() {
        return filePos;
    }

    public short getnColors() {
        return nColors;
    }


    //Getters and Setters

}
