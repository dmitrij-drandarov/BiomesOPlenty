/*******************************************************************************
 * Copyright 2014-2019, the Biomes O' Plenty Team
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 *
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/
package biomesoplenty.init;

import biomesoplenty.api.entity.BOPEntities;
import biomesoplenty.common.entity.projectile.EntityMudBall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities
{
    public static void init()
    {
        EntityType<EntityMudBall> mud_ball = createEntity(EntityMudBall::new, EntityClassification.MISC, "mud_ball", 64, 10, true);
        //EntityType<EntityBoatBOP> boat_bop = createEntity(EntityBoatBOP::new, EntityClassification.MISC, "boat_bop", 80, 3, true);

        BOPEntities.mud_ball = mud_ball;
        //BOPEntities.boat_bop = boat_bop;
    }

    public static <T extends Entity> EntityType<T> createEntity(EntityType.IFactory<T> factory, EntityClassification classification, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        ResourceLocation location = new ResourceLocation("biomesoplenty", name);
        EntityType<T> type = EntityType.Builder.<T>create(factory, classification).setTrackingRange(trackingRange).setUpdateInterval(updateFrequency).setShouldReceiveVelocityUpdates(sendsVelocityUpdates).build(location.toString());
        type.setRegistryName(name);
        ForgeRegistries.ENTITIES.register(type);
        return type;
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRendering()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityMudBall.class, manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
        //RenderingRegistry.registerEntityRenderingHandler(EntityBoatBOP.class, manager -> new RenderBoatBOP(manager));
    }
}
