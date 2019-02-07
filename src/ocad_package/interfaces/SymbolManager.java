package ocad_package.interfaces;

public interface SymbolManager {
    int getSize();
    int getSymNum();
    byte getOTP();
    void setParameters(int size,int symNum);
    void setParameters(int size, int symNum, int otp, int flags);
    //more



}
