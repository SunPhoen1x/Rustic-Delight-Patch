package com.phoen1x.rusticdelightpatch.mixin;

import com.phantomwing.rusticdelight.item.ModItems;
import com.phoen1x.rusticdelightpatch.impl.item.PolyBaseItem;
import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ModItems.class, remap = false)
public class RusticDelightItemsMixin {
    @Inject(method = "registerModItems", at = @At("TAIL"), remap = false)
    private static void polymerifyAllItems(CallbackInfo ci) {
        Registries.ITEM.getIds().forEach(id -> {
            if (id.getNamespace().equals("rusticdelight")) {
                Item item = Registries.ITEM.get(id);
                applyPolymer(item);
            }
        });
    }

    @Unique
    private static void applyPolymer(Item item) {
        PolymerItem polymerItem = new PolyBaseItem(item);
        PolymerItem.registerOverlay(item, polymerItem);
    }
}