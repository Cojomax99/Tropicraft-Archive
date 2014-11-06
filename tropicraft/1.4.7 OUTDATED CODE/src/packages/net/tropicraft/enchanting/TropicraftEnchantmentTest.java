package net.tropicraft.enchanting;



public class TropicraftEnchantmentTest extends TropicraftEnchantment
{
    public TropicraftEnchantmentTest(int par1, int par2)
    {
        super(par1, par2);
        this.setName("test");
    }

    /**
     * Returns the minimal value of enchantability nedded on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return 1;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return this.getMinEnchantability(par1) + 40;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }
}
