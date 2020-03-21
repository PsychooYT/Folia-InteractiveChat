package com.loohp.interactivechat.API.Events;

import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMentionPlayerEvent extends Event implements Cancellable {
	
	//This event is called before the plugin plays the title and sound to the player who is mentioned
	//If the plugin is on mention cooldown (3 seconds after each mention), the event will fire as cancelled
	//The sound will be null if you did't set a sound in the config
	//Set the Sound to null if you do not want to play sound

	Player reciever;
	UUID sender;
    String title;
    String subtitle;
    Sound sound;
    boolean cancel;

    public PlayerMentionPlayerEvent(Player reciever, UUID sender, String title, String subtitle, Sound sound, boolean cancel) {
        this.reciever = reciever;
        this.sender = sender;
        this.title = title;
        this.subtitle = subtitle;
        this.sound = sound;
        this.cancel = cancel;
    }
    
	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
    
    public Player getReciver() {
    	return reciever;
    }
    
    public UUID getSender() {
    	return sender;
    }
    
    public String getTitle() {
    	return title;
    }

    public String getSubtitle() {
    	return subtitle;
    }
    
    public Sound getMentionSound() {
    	return sound;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }

    public void setSubtitle(String subtitle) {
    	this.subtitle = subtitle;
    }
    
    public void setMentionSound(Sound sound) {
    	this.sound = sound;
    }
    
    @Deprecated
    public void setReciver(Player reciever) {
    	this.reciever = reciever;
    }
    
    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}