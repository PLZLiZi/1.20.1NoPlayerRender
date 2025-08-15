package plz.lizi.nprfix;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NPRFixMod.MODID)
public class NPRFixMod {
	// Define mod id in a common place for everything to reference
	public static final String MODID = "nprfix";
	// Directly reference a slf4j logger
	private static final Logger LOGGER = LogUtils.getLogger();

	public NPRFixMod(FMLJavaModLoadingContext context) {
		IEventBus modEventBus = context.getModEventBus();
		// Register the commonSetup method for modloading
		modEventBus.addListener(this::commonSetup);
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		// Register the item to a creative tab
		modEventBus.addListener(this::addCreative);
		// Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
		context.registerConfig(ModConfig.Type.COMMON, NPRFixConfig.SPEC);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		// Some common setup code
		if (NPRFixConfig.npr_enable) {
		}
	}

	// Add the example block item to the building blocks tab
	private void addCreative(BuildCreativeModeTabContentsEvent event) {}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {}

	// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {

		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			if (NPRFixConfig.npr_enable) {
				LOGGER.info(Component.translatable("nprfix.enable.warnmessage").getString());
			}
		}
	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static class ModEvents {

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void onLivingEntityRender(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
			if (NPRFixConfig.npr_enable && Minecraft.getInstance().level != null && Minecraft.getInstance().player != null) {
				Entity entity = event.getEntity();
				if (entity instanceof Player) {
					if (event.isCancelable()) {
						event.setCanceled(true);
					}
					event.setResult(Result.DENY);
				}
			}
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void onEntityJoinLevel(PlayerLoggedInEvent event) {
			if (NPRFixConfig.npr_enable && event.getEntity() instanceof LocalPlayer player) {
				player.sendSystemMessage(Component.translatable("nprfix.enable.warnmessage"));
			}
		}
	}
}
