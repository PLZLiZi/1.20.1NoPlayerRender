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
	private static final ForgeConfigSpec.BooleanValue NPR_SETTING_ENABLE = BUILDER.comment(" If a player experiences abnormal lag while their body is visible on the backpack interface, while sleeping, etc., please enable the option\n 如果玩家在背包界面、睡觉等一切看得到玩家身体的情况下遇到异常卡顿，则请启用选项\n true : 启用NPR Fix, 玩家模型将不会被渲染以减少卡顿\n false : 不启用NPR Fix, 正常渲染玩家模型 (默认)").define("enable_npr_fix", false);
	static final ForgeConfigSpec SPEC = BUILDER.build();
	public static boolean npr_enable;

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		npr_enable = NPR_SETTING_ENABLE.get();
	}
}
