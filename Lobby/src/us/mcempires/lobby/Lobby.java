package us.mcempires.lobby;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitScheduler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import net.md_5.bungee.api.config.ServerInfo;

public class Lobby extends JavaPlugin 
{
	ServerInfo empires1Info;
    BukkitScheduler scheduler;
    
    int empires1Online;
    int cp1Online;
    int cph1Online;
	
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	   // getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", (PluginMessageListener) this);
	    
	    scheduler = getServer().getScheduler();
	    
	    // Update server information every 3 seconds
	    /*scheduler.scheduleSyncRepeatingTask(this, new Runnable(){
	    	@Override
	    	public void run(){
	    		
	    		// Empires1
	    		try
	    		{
		    		Socket socket = new Socket();
		    		socket.connect(new InetSocketAddress("170.130.28.186", 25581));
		    		if(!socket.isConnected())
		    		{
		    			socket.close();
		    			return;
		    		}
		    		
		    		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		    		DataInputStream in = new DataInputStream(socket.getInputStream());
		    		
		    		out.write(0xFE);
		    		StringBuilder str = new StringBuilder();
		    		
		              int b;
                      while ((b = in.read()) != -1) {
                    	  if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                          str.append((char) b);
                          }
                      }
                      
                      String[] data = str.toString().split("§");
                      empires1Online = Integer.valueOf(data[1]);
                      socket.close();
	    		}
	    		catch (Exception e){
	    			e.printStackTrace();
	    		}
	    		
	    		// Control Point 1
	    		try
	    		{
		    		Socket socket = new Socket();
		    		socket.connect(new InetSocketAddress("107.175.106.231", 25580));
		    		if(!socket.isConnected())
		    		{
		    			socket.close();
		    			return;
		    		}
		    		
		    		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		    		DataInputStream in = new DataInputStream(socket.getInputStream());
		    		
		    		out.write(0xFE);
		    		StringBuilder str = new StringBuilder();
		    		
		              int b;
                      while ((b = in.read()) != -1) {
                    	  if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                          str.append((char) b);
                          }
                      }
                      
                      String[] data = str.toString().split("§");
                      cp1Online = Integer.valueOf(data[1]);
                      socket.close();
	    		}
	    		catch (Exception e){
	    			e.printStackTrace();
	    		}
	    		
	    		// Control Heroes 1
	    		try
	    		{
		    		Socket socket = new Socket();
		    		socket.connect(new InetSocketAddress("107.174.19.150", 25590));
		    		if(!socket.isConnected())
		    		{
		    			socket.close();
		    			return;
		    		}
		    		
		    		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		    		DataInputStream in = new DataInputStream(socket.getInputStream());
		    		
		    		out.write(0xFE);
		    		StringBuilder str = new StringBuilder();
		    		
		              int b;
                      while ((b = in.read()) != -1) {
                    	  if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                          str.append((char) b);
                          }
                      }
                      
                      String[] data = str.toString().split("§");
                      cph1Online = Integer.valueOf(data[1]);
                      socket.close();
	    		}
	    		catch (Exception e){
	    			e.printStackTrace();
	    		}

	    		
	    	}
	    }, 0L, 60L);*/
	}
	 
	@Override
	public void onDisable()
	{
		
	}
	
	public void sendToServer(Player player, String targetServer)
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try
		{
			out.writeUTF("Connect");
			out.writeUTF(targetServer);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		player.sendPluginMessage(this,  "BungeeCord", b.toByteArray());
	}
	
	/*public void onPluginMessageReceived(String channel, Player player, byte[] message) {
    if (!channel.equals("BungeeCord")) {
      return;
    }
    ByteArrayDataInput in = ByteStreams.newDataInput(message);
    String subchannel = in.readUTF();
    if (subchannel.equals("SomeSubChannel")) {
      // Use the code sample in the 'Response' sections below to read
      // the data.
    	try 
    	{
	    	short len = in.readShort();
	    	byte[] msgbytes = new byte[len];
	    	in.readFully(msgbytes);

	    	DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			String somedata = msgin.readUTF();
			// Read the data in the same way you wrote it
		    short somenumber = msgin.readShort();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
  }*/

}
