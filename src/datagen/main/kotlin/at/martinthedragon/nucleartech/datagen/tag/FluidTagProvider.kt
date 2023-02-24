package at.martinthedragon.nucleartech.datagen.tag

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.fluid.NTechFluids
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.FluidTagsProvider
import net.minecraft.tags.FluidTags
import net.minecraftforge.common.data.ExistingFileHelper

class FluidTagProvider(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) : FluidTagsProvider(dataGenerator, NuclearTech.MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Fluid Tags"

    override fun addTags(): Unit = with(NTechTags.Fluids) {
        tag(FluidTags.LAVA).addTag(VOLCANIC_LAVA)

        tag(CORIUM).add(NTechFluids.corium.source, NTechFluids.corium.flowing)
        tag(CRUDE_OIL).add(NTechFluids.oil.source, NTechFluids.oil.flowing)
        tag(GAS).addTag(NATURAL_GAS)
        tag(NATURAL_GAS).add(NTechFluids.gas.source, NTechFluids.gas.flowing)
        tag(OIL).addTag(CRUDE_OIL)
        tag(STEAM).add(NTechFluids.steam.source, NTechFluids.steam.flowing)
        tag(URANIUM_HEXAFLUORIDE).add(NTechFluids.uraniumHexafluoride.source, NTechFluids.uraniumHexafluoride.flowing)
        tag(VOLCANIC_LAVA).add(NTechFluids.volcanicLava.source, NTechFluids.volcanicLava.flowing)

        tag(LIQUID_FEU).add(NTechFluids.liquid_feu.source, NTechFluids.liquid_feu.flowing)
        tag(AIRIFIED_WATER).add(NTechFluids.airified_water.source, NTechFluids.airified_water.flowing)
        tag(RADEOACTIVE_WATER).add(NTechFluids.radeoactive_water.source, NTechFluids.radeoactive_water.flowing)
        tag(INSTABLE_AIR).add(NTechFluids.instable_air.source, NTechFluids.instable_air.flowing)
        tag(WATERIZED_LM).add(NTechFluids.waterized_lm.source, NTechFluids.waterized_lm.flowing)
    }
}
