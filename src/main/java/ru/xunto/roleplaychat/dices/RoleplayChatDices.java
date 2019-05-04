package ru.xunto.roleplaychat.dices;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = RoleplayChatDices.MODID, name = RoleplayChatDices.NAME, version = RoleplayChatDices.VERSION, acceptableRemoteVersions = "*")
public class RoleplayChatDices
{
    public static final String MODID = "@MODID@";
    public static final String NAME = "@MODID@";
    public static final String VERSION = "@VERSION@";

    @Mod.EventHandler public void startServer(FMLServerStartingEvent event) {
    }
}
