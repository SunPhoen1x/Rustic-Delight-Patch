package com.phoen1x.rusticdelightpatch.mixin;

import com.phantomwing.rusticdelight.itemGroup.ModItemGroups;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModItemGroups.class)
public class ModItemGroupsMixin {
    @Redirect(
        method = "<clinit>", 
        at = @At(
            value = "INVOKE", 
            target = "Lnet/minecraft/registry/Registry;register(Lnet/minecraft/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;"
        ),
        remap = false
    )
    private static Object polymerifyItemGroup(Registry<ItemGroup> registry, Identifier id, Object entry) {
        ItemGroup itemGroup = (ItemGroup) entry;
        PolymerItemGroupUtils.registerPolymerItemGroup(id, itemGroup);
        return Registry.register(registry, id, itemGroup);
    }
}