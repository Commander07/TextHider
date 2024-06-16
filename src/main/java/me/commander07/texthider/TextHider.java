package me.commander07.texthider;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class TextHider implements ClientModInitializer {
    public static boolean HIDDEN = false;
    private static KeyBinding TOGGLE_KEY;

    @Override
    public void onInitializeClient() {
        TOGGLE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.texthider.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F6,
                "key.categories.misc"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (TOGGLE_KEY.wasPressed()) {
                assert client.player != null;
                HIDDEN = !HIDDEN;
                client.player.sendMessage(Text.translatable("texthider.toggled", HIDDEN ? Text.translatable("texthider.toggled.disabled") : Text.translatable("texthider.toggled.enabled"), TOGGLE_KEY.getBoundKeyLocalizedText()), false);
            }
        });
    }
}
