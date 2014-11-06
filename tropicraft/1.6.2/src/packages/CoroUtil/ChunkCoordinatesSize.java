package CoroUtil;

import net.minecraft.util.ChunkCoordinates;

public class ChunkCoordinatesSize extends ChunkCoordinates {
	
	public int dimensionId;
	
	public float width;
	public float height;
	
	public ChunkCoordinatesSize(int par1, int par2, int par3, int parDim, float parWidth, float parHeight)
    {
        super(par1, par2, par3);
        this.width = parWidth;
        this.height = parHeight;
        this.dimensionId = parDim;
    }

}
