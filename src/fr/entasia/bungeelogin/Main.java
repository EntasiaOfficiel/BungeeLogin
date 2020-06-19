package fr.entasia.bungeelogin;

import fr.entasia.apis.socket.SocketClient;
import fr.entasia.apis.socket.SocketEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class Main extends Plugin {

	public static Main main;
	public static ServerInfo hubServer;

	@Override
	public void onEnable() {
		try{
			main = this;
			hubServer = getProxy().getServerInfo("hub");
			getLogger().info("Chargement du plugin..");

			if(getProxy().getPluginManager().getPlugin("Libraries")==null)throw new SecurityException("Libraries not here");

			SocketClient.addListener(new SocketEvent("login") {
				@Override
				public void onEvent(String[] data) {
					if(data[0]==null) Main.main.getLogger().info("Login recu pour un joueur null");
					else {
						ProxiedPlayer p = ProxyServer.getInstance().getPlayer(data[0]);
						if (p == null) Main.main.getLogger().info("Login recu pour un joueur non connecté au proxy");
						else {
							if (!LoginUtils.logins.contains(data[0])) {
								LoginUtils.logins.add(data[0]);
							}
							p.connect(Main.hubServer);
						}
					}
				}
			});


			getLogger().info("plugin chargé !");
		}catch(Throwable e){
			e.printStackTrace();
			getLogger().severe("LE SERVEUR VA S'ETEINDRE");
			getProxy().stop();
		}
	}
}
