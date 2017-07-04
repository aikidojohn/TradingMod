package com.johnhite.mc.money;

import com.johnhite.mc.money.blocks.MerchantBlock;
import com.johnhite.mc.money.client.gui.MoneyGuiHandler;
import com.johnhite.mc.money.tiles.MerchantBlockTileEntity;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    
    @Instance
    public static ExampleMod instance = new ExampleMod();
    
    public static MerchantBlock merchant = new MerchantBlock();
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new MoneyGuiHandler());

        GameRegistry.addRecipe(new ShapedOreRecipe(
        		new ItemStack(merchant), new Object[] { "III", "PDP", "RRR", 'I', Items.IRON_INGOT, 'P', "plankWood", 'D', Items.DIAMOND, 'R', Items.REDSTONE}));
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	merchant.setRegistryName(new ResourceLocation(MODID + ":merchant_block"));
    	GameRegistry.register(merchant);
    	GameRegistry.register(new ItemBlock(merchant).setRegistryName(merchant.getRegistryName()));
    	GameRegistry.registerTileEntity(MerchantBlockTileEntity.class, "examplemod_merchant_tile_entity");
    	
    	
    	if (event.getSide() == Side.CLIENT) {
    		System.out.println("Registering item: " + Item.getItemFromBlock(merchant));
    		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(merchant), 0,  new ModelResourceLocation(ExampleMod.MODID + ":merchant_block", "inventory"));
        }
    }
}
