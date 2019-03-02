package ocad_package.symbols;

public class LineSymbol extends BaseSymbol{
    public LineSymbol()
    {
        setOtp((byte) 2);
    }
    short lineColor;
    short lineWidth;
    short lineStyle;
    short distFromStart;
    short distToEnd;
    short mainLenght;
    short endLenght;
    short mainGap;
    short secGap;
    short endGap;
    short minSym;
    short nPrimSym;
    short primSymDist;
    //word dblMode;
    //word dblFlags;
    short dblFillColor;
    short dblLeftColor;
    short dblRightColor;
    short dblWidth;
    short dblLeftWidth;
    short dblRightWidth;
    short dblLenght;
    short dblGap;
    short dblBackgroundColor;
    short[] dblRes = new short[2];
    //word decMode;
    short decLast;
    short decRes;
    short frColor;
    short ftWidth;
    short frStyle;
    //word primDSize;
    //more words
    //...
    byte useSymbolFlags;
    byte reserved;
}
