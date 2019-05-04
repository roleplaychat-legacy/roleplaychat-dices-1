package ru.xunto.roleplaychat.dices;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import ru.xunto.roleplaychat.features.endpoints.PrefixMatchEndpoint;
import ru.xunto.roleplaychat.forge.RoleplayChat;

@Mod(modid = RoleplayChatDices.MODID, name = RoleplayChatDices.NAME, version = RoleplayChatDices.VERSION, acceptableRemoteVersions = "*", dependencies = "required-after:roleplaychat")
public class RoleplayChatDices
{
    public static final String MODID = "@MODID@";
    public static final String NAME = "@MODID@";
    public static final String VERSION = "@VERSION@";

    @Mod.EventHandler public void startServer(FMLServerStartingEvent event) {
        try {
            RoleplayChat.chat.register(new DicesEndpoint());
            RoleplayChat.chat.register(new FudgeDicesEndpoint());
        } catch (PrefixMatchEndpoint.EmptyPrefixException e) {
            e.printStackTrace();
        }
    }
}
