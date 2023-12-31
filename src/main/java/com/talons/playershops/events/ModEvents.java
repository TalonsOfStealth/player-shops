package com.talons.playershops.events;

import com.talons.playershops.config.PlayerShopsCommonConfigs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = "playershops")
public class ModEvents {
    private static long oldTime;
    @SubscribeEvent
    public static void onLevelTickEvent(TickEvent.LevelTickEvent event) {
        if(!event.level.isClientSide) {
            if(PlayerShopsCommonConfigs.UseAllowance.get()) {
                if (event.level.getDayTime() != oldTime) {
                    oldTime = event.level.getDayTime();
                    if (event.level.getDayTime() % PlayerShopsCommonConfigs.AllowanceTimeOfDay.get() == 0) {
                        for (int i = 0; i < event.level.players().size(); i++) {
                            ItemStack giveItem = RegistryObject.create(new ResourceLocation(PlayerShopsCommonConfigs.AllowanceItem.get()), ForgeRegistries.ITEMS).get().getDefaultInstance();
                            giveItem.setCount(PlayerShopsCommonConfigs.AllowanceItemCount.get());
                            if(event.level.players().get(i).addItem(giveItem)) {
                                event.level.players().get(i).displayClientMessage(Component.literal(PlayerShopsCommonConfigs.AllowanceMessage.get()), true);
                            }
                            else  {
                                event.level.players().get(i).displayClientMessage(Component.literal(PlayerShopsCommonConfigs.AllowanceFailMessage.get()), true);
                            }
                        }
                    }
                }
            }
        }
    }
}
