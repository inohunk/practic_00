package OCAD_package;
/***
 * @author hunkel
 * @version 0.1.0
 * Main interface for manipulate system of classes
 */
public interface OCADCreator {
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
    void getData();


    Object test(int index);

}
