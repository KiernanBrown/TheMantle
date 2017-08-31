package com.themantlemc.mantleEssentials;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;

public class Playlist {
	
	String title;
	List<Song> songs = new ArrayList<Song>();
	Song currentSong;
	SongPlayer sPlayer;
	Player listener;
	PlayerInfo listenerInfo;
	int songNum = 0;
	int delay = 0;
	boolean shuffle;
	
	public void Play(int sNum, short tick)
	{
		songNum = sNum;
		currentSong = songs.get(songNum);
		
		sPlayer = new RadioSongPlayer(currentSong);
		sPlayer.addPlayer(listener);
		ActionbarTitleObject songTitle = new ActionbarTitleObject(ChatColor.GOLD + "Now Playing: " + currentSong.getTitle());
		songTitle.send(listener);
		sPlayer.setPlaying(true);
		//sPlayer.setTick(tick);
	}
	
	public void addPlayer(Player player, PlayerInfo info)
	{
		listener = player;
		listenerInfo = info;
	}
	
	public void updateSong()
	{
		if(!sPlayer.isPlaying())
		{
			if(delay > 40)
			{
				if(listenerInfo.shuffle && songs.size() > 2)
				{
					int newSong = 0;
					do
					{
						newSong = (int)Math.floor(Math.random() * songs.size());
					} while(newSong == songNum);
					songNum = newSong;			
				}
				else
				{
					if(songNum + 1 < songs.size())
					{
						songNum++;
					}
					else
					{
						songNum = 0;
					}
				}
				Play(songNum, (short) 0);
				delay = 0;
			}
			else
			{
				delay += 10;
			}
		}
	}

	
	public Playlist copy()
	{
		Playlist copyList = new Playlist(title);
		copyList.songs = songs;
		return copyList;
	}
	
	public Playlist(String t)
	{
		title = t;
	}
}
