package at.martinthedragon.nucleartech.world.gen

import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.FEATURES
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.world.gen.features.HugeGlowingMushroomFeature
import at.martinthedragon.nucleartech.world.gen.features.OilBubbleFeature
import net.minecraft.core.Holder
import net.minecraft.data.BuiltinRegistries
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.HugeMushroomBlock
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.placement.*
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.RegistryObject
import net.minecraft.data.worldgen.features.OreFeatures as VanillaOreFeatures

@Suppress("unused")
object WorldGen {
    @Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    object BiomeFeatures {
        @SubscribeEvent(priority = EventPriority.HIGH)
        @JvmStatic
        fun addBiomeFeatures(event: BiomeLoadingEvent) {
            val name = event.name
            val builder = event.generation
            when (event.category) {
                Biome.BiomeCategory.NETHER -> {
                    addNetherOres(builder)
                    if (name == Biomes.BASALT_DELTAS.location()) addBasaltDeltasOres(builder)
                }
                Biome.BiomeCategory.THEEND -> {
                    addEndOres(builder)
                }
                else -> {
                    addDefaultOres(builder)
                    if (name == Biomes.BADLANDS.location()) addBadlandsOres(builder)
                    if (name == Biomes.DRIPSTONE_CAVES.location()) addDripstoneCavesOres(builder)
                }
            }
        }

        private fun addDefaultOres(builder: BiomeGenerationSettingsBuilder) {
            builder
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_URANIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_URANIUM_LARGE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_URANIUM_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_THORIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_THORIUM_MIDDLE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TITANIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_SULFUR_LOWER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_SULFUR_SMALL)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_NITER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUNGSTEN_MIDDLE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUNGSTEN_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ALUMINIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ALUMINIUM_MIDDLE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_FLUORITE_LOWER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_BERYLLIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_BERYLLIUM_LOWER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LEAD)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LIGNITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ASBESTOS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ASBESTOS_MIDDLE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_RARE_EARTH)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_RARE_EARTH_LARGE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_RARE_EARTH_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COBALT)

                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.OIL_BUBBLE)
        }

        private fun addBadlandsOres(builder: BiomeGenerationSettingsBuilder) {
            builder
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_NITER_EXTRA)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COBALT_EXTRA)
        }

        private fun addDripstoneCavesOres(builder: BiomeGenerationSettingsBuilder) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_FLUORITE_BURIED)
        }

        private fun addNetherOres(builder: BiomeGenerationSettingsBuilder) {
            builder
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_URANIUM_NETHER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_PLUTONIUM_NETHER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUNGSTEN_NETHER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_SULFUR_NETHER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_PHOSPHORUS_NETHER)
        }

        private fun addBasaltDeltasOres(builder: BiomeGenerationSettingsBuilder) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUNGSTEN_DELTAS)
        }

        private fun addEndOres(builder: BiomeGenerationSettingsBuilder) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TRIXITE_END)
        }
    }

    object Features {
        val OIL_BUBBLE: RegistryObject<OilBubbleFeature> = FEATURES.register("oil_bubble") { OilBubbleFeature(NoneFeatureConfiguration.CODEC) }
        val HUGE_GLOWING_MUSHROOM: RegistryObject<HugeGlowingMushroomFeature> = FEATURES.register("huge_glowing_mushroom") { HugeGlowingMushroomFeature(HugeMushroomFeatureConfiguration.CODEC) }
    }

    private object OreFeatures {
        val NATURAL_STONE: RuleTest = VanillaOreFeatures.NATURAL_STONE
        val STONE_ORE_REPLACEABLES: RuleTest = VanillaOreFeatures.STONE_ORE_REPLACEABLES
        val DEEPSLATE_ORE_REPLACEABLES: RuleTest = VanillaOreFeatures.DEEPSLATE_ORE_REPLACEABLES
        val NETHERRACK: RuleTest = VanillaOreFeatures.NETHERRACK
        val NETHER_ORE_REPLACEABLES: RuleTest = VanillaOreFeatures.NETHER_ORE_REPLACEABLES
        val END_STONE: RuleTest = BlockMatchTest(Blocks.END_STONE)
        val ORE_URANIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.uraniumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateUraniumOre.get().defaultBlockState()))
        val ORE_THORIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.thoriumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateThoriumOre.get().defaultBlockState()))
        val ORE_TITANIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.titaniumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateTitaniumOre.get().defaultBlockState()))
        val ORE_SULFUR_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.sulfurOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateSulfurOre.get().defaultBlockState()))
        val ORE_NITER_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.niterOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateNiterOre.get().defaultBlockState()))
        val ORE_TUNGSTEN_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.tungstenOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateTungstenOre.get().defaultBlockState()))
        val ORE_ALUMINIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.aluminiumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateAluminiumOre.get().defaultBlockState()))
        val ORE_FLUORITE_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.fluoriteOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateFluoriteOre.get().defaultBlockState()))
        val ORE_BERYLLIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.berylliumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateBerylliumOre.get().defaultBlockState()))
        val ORE_LEAD_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.leadOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateLeadOre.get().defaultBlockState()))
        val ORE_ASBESTOS_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.asbestosOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateAsbestosOre.get().defaultBlockState()))
        val ORE_RARE_EARTH_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.rareEarthOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateRareEarthOre.get().defaultBlockState()))
        val ORE_COBALT_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.cobaltOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateCobaltOre.get().defaultBlockState()))
        val ORE_URANIUM_SMALL = register("ore_uranium_small", Feature.ORE, OreConfiguration(ORE_URANIUM_TARGET_LIST, 3, .5F))
        val ORE_URANIUM_LARGE = register("ore_uranium_large", Feature.ORE, OreConfiguration(ORE_URANIUM_TARGET_LIST, 12, .7F))
        val ORE_URANIUM_BURIED = register("ore_uranium_buried", Feature.ORE, OreConfiguration(ORE_URANIUM_TARGET_LIST, 6, 1F))
        val ORE_THORIUM = register("ore_thorium", Feature.ORE, OreConfiguration(ORE_THORIUM_TARGET_LIST, 5))
        val ORE_THORIUM_SMALL = register("ore_thorium_small", Feature.ORE, OreConfiguration(ORE_THORIUM_TARGET_LIST, 3))
        val ORE_TITANIUM = register("ore_titanium", Feature.ORE, OreConfiguration(ORE_TITANIUM_TARGET_LIST, 6, .4F))
        val ORE_SULFUR = register("ore_sulfur", Feature.ORE, OreConfiguration(ORE_SULFUR_TARGET_LIST, 10, .2F))
        val ORE_SULFUR_SMALL = register("ore_sulfur_small", Feature.ORE, OreConfiguration(ORE_SULFUR_TARGET_LIST, 4))
        val ORE_NITER = register("ore_niter", Feature.ORE, OreConfiguration(ORE_NITER_TARGET_LIST, 8, .3F))
        val ORE_NITER_SMALL = register("ore_niter_small", Feature.ORE, OreConfiguration(ORE_NITER_TARGET_LIST, 3))
        val ORE_TUNGSTEN = register("ore_tungsten", Feature.ORE, OreConfiguration(ORE_TUNGSTEN_TARGET_LIST, 3))
        val ORE_TUNGSTEN_BURIED = register("ore_tungsten_buried", Feature.ORE, OreConfiguration(ORE_TUNGSTEN_TARGET_LIST, 9, 1F))
        val ORE_ALUMINIUM = register("ore_aluminium", Feature.ORE, OreConfiguration(ORE_ALUMINIUM_TARGET_LIST, 5))
        val ORE_ALUMINIUM_SMALL = register("ore_aluminium_small", Feature.ORE, OreConfiguration(ORE_ALUMINIUM_TARGET_LIST, 3))
        val ORE_FLUORITE = register("ore_fluorite", Feature.ORE, OreConfiguration(ORE_FLUORITE_TARGET_LIST, 4))
        val ORE_FLUORITE_BURIED = register("ore_fluorite_buried", Feature.ORE, OreConfiguration(ORE_FLUORITE_TARGET_LIST, 15, 1F))
        val ORE_BERYLLIUM = register("ore_beryllium", Feature.ORE, OreConfiguration(ORE_BERYLLIUM_TARGET_LIST, 4))
        val ORE_BERYLLIUM_SMALL = register("ore_beryllium_small", Feature.ORE, OreConfiguration(ORE_BERYLLIUM_TARGET_LIST, 2))
        val ORE_LEAD = register("ore_lead", Feature.ORE, OreConfiguration(ORE_LEAD_TARGET_LIST, 10, .6F))
        val ORE_LIGNITE = register("ore_lignite", Feature.ORE, OreConfiguration(STONE_ORE_REPLACEABLES, NTechBlocks.ligniteOre.get().defaultBlockState(), 30, .2F))
        val ORE_ASBESTOS = register("ore_asbestos", Feature.ORE, OreConfiguration(ORE_ASBESTOS_TARGET_LIST, 10, 1F))
        val ORE_RARE_EARTH_SMALL = register("ore_rare_earth_small", Feature.ORE, OreConfiguration(ORE_RARE_EARTH_TARGET_LIST, 1))
        val ORE_RARE_EARTH_LARGE = register("ore_rare_earth_large", Feature.ORE, OreConfiguration(ORE_RARE_EARTH_TARGET_LIST, 15))
        val ORE_RARE_EARTH_BURIED = register("ore_rare_earth_buried", Feature.ORE, OreConfiguration(ORE_RARE_EARTH_TARGET_LIST, 5, 1F))
        val ORE_COBALT_SMALL = register("ore_cobalt_small", Feature.ORE, OreConfiguration(ORE_COBALT_TARGET_LIST, 3))
        val ORE_COBALT_LARGE = register("ore_cobalt_large", Feature.ORE, OreConfiguration(ORE_COBALT_TARGET_LIST, 15, .25F))
        val ORE_NETHER_URANIUM = register("ore_nether_uranium", Feature.ORE, OreConfiguration(NETHERRACK, NTechBlocks.netherUraniumOre.get().defaultBlockState(), 6))
        val ORE_NETHER_PLUTONIUM = register("ore_nether_plutonium", Feature.ORE, OreConfiguration(NETHERRACK, NTechBlocks.netherPlutoniumOre.get().defaultBlockState(), 4))
        val ORE_NETHER_TUNGSTEN = register("ore_nether_tungsten", Feature.ORE, OreConfiguration(NETHERRACK, NTechBlocks.netherTungstenOre.get().defaultBlockState(), 10))
        val ORE_NETHER_SULFUR = register("ore_nether_sulfur", Feature.ORE, OreConfiguration(NETHERRACK, NTechBlocks.netherSulfurOre.get().defaultBlockState(), 18))
        val ORE_NETHER_PHOSPHORUS = register("ore_nether_phosphorus", Feature.ORE, OreConfiguration(NETHERRACK, NTechBlocks.netherPhosphorusOre.get().defaultBlockState(), 5))
        val ORE_END_TRIXITE = register("ore_end_trixite", Feature.ORE, OreConfiguration(END_STONE, NTechBlocks.trixite.get().defaultBlockState(), 4, .1F))

        val OIL_BUBBLE = register("oil_bubble", Features.OIL_BUBBLE.get(), FeatureConfiguration.NONE)
    }

    private object OrePlacements {
        val ORE_URANIUM = register("ore_uranium", OreFeatures.ORE_URANIUM_SMALL, commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(25))))
        val ORE_URANIUM_LARGE = register("ore_uranium_large", OreFeatures.ORE_URANIUM_LARGE, rareOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(80))))
        val ORE_URANIUM_BURIED = register("ore_uranium_buried", OreFeatures.ORE_URANIUM_BURIED, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(80))))
        val ORE_THORIUM = register("ore_thorium_lower", OreFeatures.ORE_THORIUM, commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))))
        val ORE_THORIUM_MIDDLE = register("ore_thorium_middle", OreFeatures.ORE_THORIUM_SMALL, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(20))))
        val ORE_TITANIUM = register("ore_titanium", OreFeatures.ORE_TITANIUM, commonOrePlacement(14, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-100), VerticalAnchor.aboveBottom(100))))
        val ORE_SULFUR_LOWER = register("ore_sulfur_lower", OreFeatures.ORE_SULFUR, commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(40))))
        val ORE_SULFUR_SMALL = register("ore_sulfur_small", OreFeatures.ORE_SULFUR_SMALL, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(50))))
        val ORE_NITER = register("ore_niter", OreFeatures.ORE_NITER_SMALL, commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(80))))
        val ORE_NITER_EXTRA = register("ore_niter_extra", OreFeatures.ORE_NITER, commonOrePlacement(50, HeightRangePlacement.triangle(VerticalAnchor.absolute(30), VerticalAnchor.absolute(128))))
        val ORE_TUNGSTEN_MIDDLE = register("ore_tungsten_middle", OreFeatures.ORE_TUNGSTEN, commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(40))))
        val ORE_TUNGSTEN_BURIED = register("ore_tungsten_buried", OreFeatures.ORE_TUNGSTEN_BURIED, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.absolute(0))))
        val ORE_ALUMINIUM = register("ore_aluminium", OreFeatures.ORE_ALUMINIUM_SMALL, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(40))))
        val ORE_ALUMINIUM_MIDDLE = register("ore_aluminium_middle", OreFeatures.ORE_ALUMINIUM, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-15), VerticalAnchor.absolute(30))))
        val ORE_FLUORITE_LOWER = register("ore_fluorite_lower", OreFeatures.ORE_FLUORITE, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(10))))
        val ORE_FLUORITE_BURIED = register("ore_fluorite_buried", OreFeatures.ORE_FLUORITE_BURIED, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(50))))
        val ORE_BERYLLIUM = register("ore_beryllium", OreFeatures.ORE_BERYLLIUM_SMALL, commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(30))))
        val ORE_BERYLLIUM_LOWER = register("ore_beryllium_lower", OreFeatures.ORE_BERYLLIUM, commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(40))))
        val ORE_LEAD = register("ore_lead", OreFeatures.ORE_LEAD, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(30))))
        val ORE_LIGNITE = register("ore_lignite", OreFeatures.ORE_LIGNITE, commonOrePlacement(1, HeightRangePlacement.triangle(VerticalAnchor.absolute(30), VerticalAnchor.absolute(128))))
        val ORE_ASBESTOS = register("ore_asbestos", OreFeatures.ORE_ASBESTOS, commonOrePlacement(3, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(20), VerticalAnchor.absolute(40))))
        val ORE_ASBESTOS_MIDDLE = register("ore_asbestos_middle", OreFeatures.ORE_ASBESTOS, commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.top())))
        val ORE_RARE_EARTH = register("ore_rare_earth", OreFeatures.ORE_RARE_EARTH_SMALL, commonOrePlacement(100, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())))
        val ORE_RARE_EARTH_LARGE = register("ore_rare_earth_large", OreFeatures.ORE_RARE_EARTH_LARGE, rareOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(50))))
        val ORE_RARE_EARTH_BURIED = register("ore_rare_earth_buried", OreFeatures.ORE_RARE_EARTH_BURIED, commonOrePlacement(15, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-100), VerticalAnchor.aboveBottom(100))))
        val ORE_COBALT = register("ore_cobalt", OreFeatures.ORE_COBALT_SMALL, commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15))))
        val ORE_COBALT_EXTRA = register("ore_cobalt_extra", OreFeatures.ORE_COBALT_LARGE, rareOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(70))))
        val ORE_URANIUM_NETHER = register("ore_uranium_nether", OreFeatures.ORE_NETHER_URANIUM, commonOrePlacement(8, PlacementUtils.RANGE_10_10))
        val ORE_PLUTONIUM_NETHER = register("ore_plutonium_nether", OreFeatures.ORE_NETHER_PLUTONIUM, commonOrePlacement(6, PlacementUtils.RANGE_10_10))
        val ORE_TUNGSTEN_NETHER = register("ore_tungsten_nether", OreFeatures.ORE_NETHER_TUNGSTEN, commonOrePlacement(10, PlacementUtils.RANGE_10_10))
        val ORE_TUNGSTEN_DELTAS = register("ore_tungsten_deltas", OreFeatures.ORE_NETHER_TUNGSTEN, commonOrePlacement(10, PlacementUtils.RANGE_10_10))
        val ORE_SULFUR_NETHER = register("ore_sulfur_nether", OreFeatures.ORE_NETHER_SULFUR, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(10), VerticalAnchor.aboveBottom(74))))
        val ORE_PHOSPHORUS_NETHER = register("ore_phosphorus_nether", OreFeatures.ORE_NETHER_PHOSPHORUS, commonOrePlacement(16, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(54), VerticalAnchor.belowTop(10))))
        val ORE_TRIXITE_END = register("ore_trixite_end", OreFeatures.ORE_END_TRIXITE, commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(16), VerticalAnchor.absolute(80))))

        val OIL_BUBBLE = register("oil_bubble", OreFeatures.OIL_BUBBLE, rareOrePlacement(25, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))))

        fun orePlacement(first: PlacementModifier, second: PlacementModifier) = listOf(first, InSquarePlacement.spread(), second, BiomeFilter.biome())
        fun commonOrePlacement(count: Int, modifier: PlacementModifier) = orePlacement(CountPlacement.of(count), modifier)
        fun rareOrePlacement(rarity: Int, modifier: PlacementModifier) = orePlacement(RarityFilter.onAverageOnceEvery(rarity), modifier)
    }


    object TreeFeatures {
        val HUGE_GLOWING_MUSHROOM = register("huge_glowing_mushroom", Features.HUGE_GLOWING_MUSHROOM.get(), HugeMushroomFeatureConfiguration(BlockStateProvider.simple(
            NTechBlocks.glowingMushroomBlock.get()), BlockStateProvider.simple(NTechBlocks.glowingMushroomStem.get().defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)), 4))
    }

    private fun <FC : FeatureConfiguration, F : Feature<FC>> register(name: String, feature: F, config: FC): Holder<ConfiguredFeature<FC, *>> = BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, ntm(name).toString(), ConfiguredFeature(feature, config))
    private fun register(name: String, feature: Holder<out ConfiguredFeature<*, *>>, modifiers: List<PlacementModifier>): Holder<PlacedFeature> = BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, ntm(name), PlacedFeature(Holder.hackyErase(feature), modifiers.toList()))
}
