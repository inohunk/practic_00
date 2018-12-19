package OCAD_package;

import java.nio.ByteBuffer;


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