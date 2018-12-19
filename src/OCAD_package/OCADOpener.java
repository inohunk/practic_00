package OCAD_package;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
/***
 * @author hunkel
 * @version 0.1.0
 * Factory Class
 */
public  class OCADOpener {
        public static OCADCreator createOCADObj(String ocadFile) throws IOException {
            ByteBuffer buffer = null;
            OCADCreator ocadObject = null;
            //Открытие файла OCAD
            try (RandomAccessFile aFile = new RandomAccessFile((String) ocadFile, "r");
                 FileChannel fc = aFile.getChannel();
                 FileLock fileLock = fc.tryLock(0L, Long.MAX_VALUE, true)) {
                if (null != fileLock) {
                    buffer = ByteBuffer.allocate((int) aFile.length());
                    fc.read(buffer, 0);
                    buffer.order(ByteOrder.LITTLE_ENDIAN);
                } else {
                    throw new OverlappingFileLockException();
                }
            } catch (IOException ioEx) {
                buffer = null;
                throw ioEx;
            }
            int fileVersion = buffer.getShort(4);
            switch (fileVersion)
            {
                case 8:
                    ocadObject = new OCAD_8(buffer);
                    break;
                case 9:
                    ocadObject = new OCAD_9(buffer);
                    break;
                case 10:
                    ocadObject = new OCAD_10(buffer);
                    break;
                case 11:
                    ocadObject = new OCAD_11(buffer);
                    break;
                case 12:
                    ocadObject = new OCAD_12(buffer);
                    break;
            }
            return ocadObject;
        }
}


