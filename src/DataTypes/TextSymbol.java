package DataTypes;

public class TextSymbol extends BaseSymbol
{
    TextSymbol()
    {

    }
    String[] fontName = new String[31];
    short fontColor;
    short fontSize;
    short weight;
    boolean italic;
    byte res1;
    short charSpace;
    short wordSpace;
    short alignment;
    short lineSpace;
    short paraSpace;
    short indentFirst;
    short indentOther;
    short nTabs;
    long[] tabs = new long[32];
    //wordbool lbON
    short lbColor;
    short lbWidth;
    short lbDist;
    short res2;
    byte frMode;
    byte frLineStyle;
    boolean pointSymOn;
    int pointSymNumber;
    String[] res3 = new String[18];
    short frLeft;
    short frBottom;
    short frRight;
    short frTop;
    short frColor;
    short frWidth;
    short res4;
    //word res5;
    short frOfX;
    short frOfY;

}
