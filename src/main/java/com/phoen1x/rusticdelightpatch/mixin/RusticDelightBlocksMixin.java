package com.phoen1x.rusticdelightpatch.mixin;

import com.phantomwing.rusticdelight.block.ModBlocks;
import com.phantomwing.rusticdelight.block.custom.*;
import com.phoen1x.rusticdelightpatch.impl.block.BaseFactoryBlock;
import com.phoen1x.rusticdelightpatch.impl.block.StatePolymerBlock;
import eu.pb4.factorytools.api.block.model.generic.BlockStateModelManager;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.common.api.PolymerCommonUtils;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.PieBlock;

@Mixin(value = ModBlocks.class, remap = false)
public class RusticDelightBlocksMixin {
    @Inject(method = "registerModBlocks", at = @At("TAIL"), remap = false)
    private static void polymerifyAllBlocks(CallbackInfo ci) {
        Registries.BLOCK.getIds().forEach(id -> {
            if (id.getNamespace().equals("rusticdelight")) {
                Block block = Registries.BLOCK.get(id);
                polymerify(id, block);
            }
        });
    }

    @Unique
    private static void polymerify(Identifier location, Block block) {
        BlockStateModelManager.addBlock(location, block);

        PolymerBlock overlay = null;
        String path = location.getPath();

        if (block instanceof CottonCropBlock || block instanceof BellPepperCropBlock || block instanceof CoffeeCropBlock) {
            overlay = BaseFactoryBlock.SAPLING;
        }
        else if (block instanceof ModWildCropBlock || block instanceof FlowerPotBlock) {
            overlay = BaseFactoryBlock.SAPLING;
        }
        else if (block instanceof PancakeBlock || block instanceof RiceRollRoyaleBlock) {
            overlay = BaseFactoryBlock.TRIPWIRE;
        }
        else if (block instanceof PieBlock) {
            overlay = BaseFactoryBlock.BOTTOM_TRAPDOOR;
        }
        else if (path.endsWith("_crate") || path.endsWith("_bag")) {
            overlay = StatePolymerBlock.of(block, BlockModelType.FULL_BLOCK);
        }
        else if (!(block instanceof BlockEntityProvider) && block.getDefaultState().isFullCube(PolymerCommonUtils.getFakeWorld(), BlockPos.ORIGIN)) {
            overlay = BaseFactoryBlock.BARRIER;
        }
        if (overlay == null) {
            if (block.getDefaultState().getCollisionShape(PolymerCommonUtils.getFakeWorld(), BlockPos.ORIGIN).isEmpty()) {
                overlay = BaseFactoryBlock.SAPLING;
            } else {
                overlay = BaseFactoryBlock.BARRIER;
            }
        }

        PolymerBlock.registerOverlay(block, overlay);
        if (overlay instanceof BlockWithElementHolder blockWithElementHolder) {
            BlockWithElementHolder.registerOverlay(block, blockWithElementHolder);
        }
    }
}