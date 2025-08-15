package plz.lizi.nprfix;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = NPRFixMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NPRFixConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.BooleanValue NO_PLAYER_RENDER = BUILDER.comment("If you encounter rendering player lag issues, please open").define("noPlayerRender", true);
	static final ForgeConfigSpec SPEC = BUILDER.build();
	public static boolean noPlayerRender;

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		noPlayerRender = NO_PLAYER_RENDER.get();
	}
}
