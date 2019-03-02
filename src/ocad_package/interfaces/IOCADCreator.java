package ocad_package.interfaces;

import ocad_package.symbols.BaseSymbol;

import java.util.ArrayList;

/***
 * @author hunkel
 * @version 0.1.0
 */
public interface IOCADCreator {
    //File Header
    int getVersion();
    int getFileType();
    int getFileStatus();
    int getSubVersion();
    int getSubSubVersion();
    int getFirstSymbolIndexBlock();
    int getFirstObjectBlock();

    //Symbols
    int getNumberOfCharacters();
    ArrayList<BaseSymbol> getSymbols();
    void outSymbols();



    //
    String getFileName();
    int getFileNamePosition();
    int getFileNameSize();
    int test();


}
