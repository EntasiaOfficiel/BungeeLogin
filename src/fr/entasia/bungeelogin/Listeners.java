package fr.entasia.bungeelogin;

import fr.entasia.apis.other.ChatComponent;
import fr.entasia.libraries.Common;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Listeners implements Listener {

	static final String[] logincmds = new String[]{"login", "log", "lo", "l", "register", "reg"};

	@EventHandler(priority = -115)
	public void onChat(ChatEvent e) {
		if(!Common.enableDev) {
			if (LoginUtils.logins.contains(e.getSender().toString())) return;
			String a = e.getMessage().split(" ")[0];
			if(a.startsWith("/")){
				a = a.substring(1).toLowerCase();
				for(String i : logincmds)if(a.equals(i))return;
			}
			e.setCancelled(true);
			((ProxiedPlayer) e.getSender()).sendMessage(ChatComponent.create("§cTu dois d'abord te t'enregistrer ou t'authentifier !"));
		}
	}
	@EventHandler(priority = -115)
	public void onServerChange(ServerSwitchEvent e) {
		if(!Common.enableDev) {
			if (e.getPlayer().getServer().getInfo().getName().equals("login")) return;
			if (LoginUtils.logins.contains(e.getPlayer().getName())) return;
			e.getPlayer().disconnect(ChatComponent.create("§cTu dois d'abord te t'enregistrer ou t'authentifier !"));
		}
	}

	@EventHandler(priority = -115)
	public void onDisconnect(PlayerDisconnectEvent e) {
		if(!Common.enableDev){
			while(true){
				if(!LoginUtils.logins.remove(e.getPlayer().getName()))break;
			}
		}
	}
}
