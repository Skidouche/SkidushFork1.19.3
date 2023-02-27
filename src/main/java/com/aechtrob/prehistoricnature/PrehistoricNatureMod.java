package com.aechtrob.prehistoricnature;

import com.aechtrob.prehistoricnature.block.BlockHandler;
import com.aechtrob.prehistoricnature.block.trees.WoodTypeHelper;
import com.aechtrob.prehistoricnature.entity.block.PNBlockEntities;
import com.aechtrob.prehistoricnature.entity.entities.PNEntities;
import com.aechtrob.prehistoricnature.item.ItemHandler;
import com.aechtrob.prehistoricnature.world.ModConfiguredFeatures;
import com.aechtrob.prehistoricnature.world.tree.PNFoliagePlacerType;
import com.aechtrob.prehistoricnature.world.tree.PNTrunkPlacerType;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PrehistoricNatureMod.MOD_ID)
public class PrehistoricNatureMod
{
    public static final String MOD_ID = "prehistoricnature";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PrehistoricNatureMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        PNBlockEntities.register(modEventBus);
        BlockHandler.register(modEventBus);
        PNEntities.register(modEventBus);
        ItemHandler.register(modEventBus);

        PNTrunkPlacerType.TRUNK_PLACER_TYPES.register(modEventBus);
        PNFoliagePlacerType.FOLIAGE_PLACER_TYPES.register(modEventBus);
        ModConfiguredFeatures.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void addCreative(CreativeModeTabEvent.BuildContents event){

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

   @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            WoodTypeHelper.getWoodTypes().stream().forEach((woodType -> Sheets.addWoodType(woodType)));
            WoodTypeHelper.getWoodTypes().stream().forEach((woodType -> WoodType.register(woodType)));
        }

       @SubscribeEvent
       public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event)
       {
           PNBlockEntities.registerTileEntityRenders(event);
           PNEntities.registerEntityRenders(event);
       }
    }

}
