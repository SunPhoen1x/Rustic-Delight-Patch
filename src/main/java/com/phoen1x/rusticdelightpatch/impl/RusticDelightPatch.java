package com.phoen1x.rusticdelightpatch.impl;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RusticDelightPatch implements ModInitializer {

    @Override
    public void onInitialize() {
        PolymerResourcePackUtils.addModAssets("rusticdelight");
        ResourcePackExtras.forDefault().addBridgedModelsFolder(Identifier.of("rusticdelight", "block"));
    }
}