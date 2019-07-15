package ru.xunto.roleplaychat.dices;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import ru.xunto.roleplaychat.forge.RoleplayChat;
import ru.xunto.roleplaychat.framework.api.PrefixMatchEndpoint;

@Mod(modid = RoleplayChatDices.MODID, name = RoleplayChatDices.NAME, version = RoleplayChatDices.VERSION, acceptableRemoteVersions = "*", dependencies = "required-after:roleplaychat")
public class RoleplayChatDices {
    public static final String MODID = "@MODID@";
    public static final String NAME = "@MODID@";
    public static final String VERSION = "@VERSION@";

    public static void sendError(EntityPlayer player, String errorMessage) {
        TextComponentString component = new TextComponentString(errorMessage);
        component.getStyle().setColor(TextFormatting.DARK_RED);
        player.sendMessage(component);
    }

    @Mod.EventHandler public void startServer(FMLServerStartingEvent event) {
        try {
            RoleplayChat.chat.register(new DicesEndpoint());
            RoleplayChat.chat.register(new FudgeDicesEndpoint());
        } catch (PrefixMatchEndpoint.EmptyPrefixException e) {
            e.printStackTrace();
        }
    }
}
