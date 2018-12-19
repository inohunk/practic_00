package ru.hunkel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.*;

public class OCADFile {
    private String fileName;
    private int fileSize;
    private ByteBuffer buffer;
    private Map<Integer,Integer> symbols = new HashMap<Integer, Integer>();
    public OCADFile(String fileName) throws  IOException, OverlappingFileLockException {

        try (RandomAccessFile aFile = new RandomAccessFile(fileName, "r");
             FileChannel fc = aFile.getChannel();
             FileLock fileLock = fc.tryLock(0L, Long.MAX_VALUE, true)) {
            if (null != fileLock) {
                fileSize = (int) aFile.length();
                buffer = ByteBuffer.allocate(fileSize);
                fc.read(buffer, 0);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
            } else {
                throw new OverlappingFileLockException();
            }
        } catch (IOException ioEx) {
            fileSize = 0;
            buffer = null;
            throw ioEx;
        }
        this.fileName = fileName;
    }

    public String getFileName() {return this.fileName;}

    public int getFileSize() {return this.fileSize;}

    public short getVersion() {
        return this.buffer.getShort(4);
    }

    public byte getSubversion() {
        return this.buffer.get(6);
    }

    public byte getSubSubversion() {
        return this.buffer.get(7);
    }

    private int getFirstSymbolIndexBlock()
    {
        return buffer.getInt(8);
    }

    public int getFirstObjectBlock() { return buffer.getInt(12); }

    public int  getSymbolID(int index)
    {
        return buffer.getInt(buffer.getInt(index)+4);
    }

    public int getNumSymbols()  { return getNumberOfCharacters(); }

    public int getNumObjects() {return getObjectsInformation(getFirstObjectBlock()); }

    private int getNumberOfCharacters()
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
                    Integer indexValue = getSymbolID(index);
                    symbols.put(index,indexValue);
                    count++;
                    index+=4;
                }
            } else {
                int i = 0;
                index = nextSym + 4;
                while (i++ < 256 && buffer.getInt(index)!=0)
                {
                    Integer indexValue = getSymbolID(index);
                    symbols.put(index,indexValue);
                    count++;
                    index+=4;
                }
                active = false;
            }
        }
        return count;
    }
    private int getObjectsInformation(int firstIndex)
    {
        int nextObject = getFirstObjectBlock();
        int index = getFirstSymbolIndexBlock()+4;
        int count=0;
        boolean active = true;
        while(active)
        {
            if(buffer.getInt(nextObject)!=0) {
                nextObject = buffer.getInt(nextObject);
                int i = 0;
                while (i++ < 256)
                {
                    count++;
                    index+=40;
                }
            } else {
                int i = 0;
                index = nextObject + 4;
                while (i++ < 256 && buffer.getInt(index)!=0)
                {
                    count++;
                    index+=40;
                }
                active = false;
            }
        }
        return count;
    }
    /*
    public int getCountOf(int firstIndex)
    {

        int nextSym = 0;
        if(firstIndex == 8)
        {
            nextSym = getFirstSymbolIndexBlock();
        }
        else if(firstIndex == 12)
        {
            nextSym = getFirstObjectBlock();
        }
        int index = nextSym+4;
        int count=0;
        boolean active = true;
        while(active)
        {
            if(buffer.getInt(nextSym)!=0) {
                nextSym = buffer.getInt(nextSym);
                int i = 0;
                while (i++ < 256)
                {
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
    */

    private int getNumberOfObjects()
    {

        int index = getFirstObjectBlock()+4;
        int nextSym = getFirstObjectBlock();
        int count=0;
        boolean active = true;
        while(active)
        {
            if(buffer.getInt(nextSym)!=0) {
                nextSym = buffer.getInt(nextSym);
                int i = 0;
                while (i++ < 256)
                {
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
    public void getSymbolsData()
    {
        Iterator<Map.Entry<Integer, Integer>> iterator = symbols.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry<Integer, Integer> pair = iterator.next();
            Integer key = pair.getKey();
            Integer value = pair.getValue();
            //System.out.println(key + ":" + value);
        }
    }
    public String[] getSymbols()
    {
        Iterator<Map.Entry<Integer, Integer>> iterator = symbols.entrySet().iterator();
        int index = 0;
        String[] collection = new String[getNumberOfCharacters()];
        while (iterator.hasNext())
        {
            Map.Entry<Integer, Integer> pair = iterator.next();
            Integer key = pair.getKey();
            collection[index] = String.valueOf(key);
        }
        return collection;
    }
    public String getSymbolSize()
    {
        int indexSize = buffer.getInt(getFirstSymbolIndexBlock()+4);
        return String.valueOf(buffer.getInt(indexSize));
    }
}
