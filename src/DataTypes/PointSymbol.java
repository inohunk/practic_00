package DataTypes;

public class PointSymbol extends BaseSymbol {
    PointSymbol()
    {
        otp = 1;
    }

    //DataSize word
    short reserved;

    //Specific data
    short stType;
    //stFlags word
    short stColor;
    short stLineWidth;
    short stDiameter;
    short stnPoly;
    short stRes1;
    short stRes2;
    //Array of TPoint -  stPoly


}
