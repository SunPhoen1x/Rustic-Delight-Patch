package com.phoen1x.rusticdelightpatch.mixin;

import com.phantomwing.rusticdelight.potion.ModPotions;
import eu.pb4.polymer.core.api.utils.PolymerSyncedObject;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ModPotions.class, remap = false)
public class ModPotionsMixin {
    @Inject(
        method = "register(Ljava/lang/String;Lnet/minecraft/registry/entry/RegistryEntry;I)Lnet/minecraft/registry/entry/RegistryEntry;", 
        at = @At("TAIL")
    )
    private static void polymerifyShort(String name, RegistryEntry<StatusEffect> effect, int duration, CallbackInfoReturnable<RegistryEntry<Potion>> cir) {
        syncPotion(cir.getReturnValue().value());
    }

    @Inject(
        method = "register(Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/registry/entry/RegistryEntry;II)Lnet/minecraft/registry/entry/RegistryEntry;", 
        at = @At("TAIL")
    )
    private static void polymerifyLong(String name, String potionName, RegistryEntry<StatusEffect> effect, int duration, int amplifier, CallbackInfoReturnable<RegistryEntry<Potion>> cir) {
        syncPotion(cir.getReturnValue().value());
    }
    private static void syncPotion(Potion potion) {
        PolymerSyncedObject.setSyncedObject(Registries.POTION, potion, (obj, ctx) -> Potions.LUCK.value());
    }
}