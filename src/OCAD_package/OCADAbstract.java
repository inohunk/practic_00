package OCAD_package;

import java.nio.ByteBuffer;

/***
 * @author hunkel
 * @version 0.1.0
 * Abstract OCAD Class
 */
public abstract class OCADAbstract implements OCADCreator {

    protected ByteBuffer buffer = null;
    public OCADAbstract(ByteBuffer buf)
    {
        this.buffer = buf;
    }

    @Override
    public int getVersion() {
        return buffer.getShort(4);
    }

}