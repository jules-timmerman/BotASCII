package com.snoopinou.BotASCII;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MyListener extends ListenerAdapter{
	
	String prefix = "!!ascii";
	
	HashMap<Integer, String> map = new HashMap<Integer, String>();
	
	public MyListener() {
		initMap();
	}
	
	private void initMap() {
		
		LinkedList<Integer> empty = new LinkedList<Integer>();
		
		for(int i = 0; i < 4096; i++) {
			URL url = this.getClass().getResource("/"+i+".txt");
			
			try {
				BufferedInputStream input = new BufferedInputStream(url.openStream());
				String str = new String(input.readAllBytes());

				map.put(i, str);
				
			} catch (IOException | NullPointerException e) {
				System.err.println("No existing "+i+".txt");
				empty.add(i);
			}
		}
		
		String str = map.get(63);
		for(int i : empty) {
			map.put(i, str);
		}
	}
	
	private String convert(String input) {
		
		String art = "";
		
		LinkedList<String> lines = new LinkedList<String>();
		
		for(int i = 0; i < input.length(); i++) { // Lettre dans le string
			char c = input.charAt(i);
			int ascii = (int)c;
			
			String str = map.get(ascii);
			
			int beginIndex = 0;
			int endIndex = 0;
			for(int j = 0; j < getLineNumber(str); j++) { // Ligne dans l'ascii art
				beginIndex = endIndex+2; // +2 pour eviter les retour ligne (13 et 10 en ASCII)
				endIndex = str.indexOf((char)13, beginIndex+1); // +1 pour avancer dans str
				
				if(endIndex == -1) { // Si plus de retour ligne, on va jusqu'a la fin de str
					endIndex = str.length(); 
				}
				if(beginIndex > str.length()) { // Si beginIndex depasse str (a cause du +2) on met a la fin
					beginIndex = str.length();
				}
				
				try {
					lines.set(j, lines.get(j)+str.substring(beginIndex, endIndex));
				}catch(IndexOutOfBoundsException e) { // Si aucune ligne creer auparavant
					lines.add(str.substring(beginIndex, endIndex));
				}
				
			}
		}
		for(String str : lines) {
			art += str+"\n"; // on ajoute ligne par ligne en mettant un retour ligne
		}

		
		return art;
	}


	private int getLineNumber(String str) {
		
		int nbre = 1;
		
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == Character.LINE_SEPARATOR) {
				nbre++;
			}
		}
		
		return nbre;
	}

	
	public void onMessageReceived(MessageReceivedEvent e) {
		
		
		if(e.getAuthor().isBot()) {
			return;
		}
		
		Message message = e.getMessage();
		String content = message.getContentDisplay();
		
		if(content.substring(0, prefix.length()).toLowerCase().equals("!!ascii")) { // Si utilisation du prefix
			
			String str = convert(content.substring(prefix.length()+1));
			
			MessageChannel channel = e.getChannel();
			try {
				channel.sendMessage("```"+str+"```").queue();
			}catch(IllegalArgumentException e1){
				channel.sendMessage("Too long message to ASCII");
			}
		}
		
		
		
	}

}
