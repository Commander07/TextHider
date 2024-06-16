package me.commander07.texthider.mixin;

import me.commander07.texthider.TextHider;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    public void shouldRenderName(CallbackInfo ci) {
        if (TextHider.HIDDEN)
            ci.cancel();
    }

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    public void shouldRender(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (TextHider.HIDDEN && entity.getType() == EntityType.TEXT_DISPLAY)
            cir.setReturnValue(false);
    }
}
