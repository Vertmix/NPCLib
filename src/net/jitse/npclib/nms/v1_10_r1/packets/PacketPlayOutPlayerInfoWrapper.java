package net.jitse.npclib.nms.v1_10_r1.packets;

import com.comphenix.tinyprotocol.Reflection;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_10_R1.EnumGamemode;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutPlayerInfo;

import java.util.List;

public class PacketPlayOutPlayerInfoWrapper {

    private final Class<?> packetPlayOutPlayerInfoClazz = Reflection.getMinecraftClass("PacketPlayOutPlayerInfo");
    private final Class<?> playerInfoDataClazz = Reflection.getMinecraftClass("PacketPlayOutPlayerInfo$PlayerInfoData");
    private final Reflection.ConstructorInvoker playerInfoDataConstructor = Reflection.getConstructor(playerInfoDataClazz,
            packetPlayOutPlayerInfoClazz, GameProfile.class, int.class, EnumGamemode.class, IChatBaseComponent.class);

    public PacketPlayOutPlayerInfo create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction action, GameProfile gameProfile, String name) {
        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo();
        Reflection.getField(packetPlayOutPlayerInfo.getClass(), "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.class)
                .set(packetPlayOutPlayerInfo, action);

        Object playerInfoData = playerInfoDataConstructor.invoke(packetPlayOutPlayerInfo,
                gameProfile, 1, EnumGamemode.NOT_SET, IChatBaseComponent.ChatSerializer.b(name));

        Reflection.FieldAccessor<List> fieldAccessor = Reflection.getField(packetPlayOutPlayerInfo.getClass(), "b", List.class);
        List list = fieldAccessor.get(packetPlayOutPlayerInfo);
        list.add(playerInfoData);
        fieldAccessor.set(packetPlayOutPlayerInfo, list);

        return packetPlayOutPlayerInfo;
    }
}
