package com.johnhite.mc.money;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface ISideProxy {
	void init(FMLInitializationEvent event);
	void preInit(FMLPreInitializationEvent event);
}
