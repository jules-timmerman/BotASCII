package com.snoopinou.BotASCII;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Main {
	
	public static void main(String args[]) throws LoginException {
		JDA jda = new JDABuilder(AccountType.BOT).setToken("NDYxOTI3OTQxNDg0NDQ1NzA2.Dhabgg.rx18bX8tpFABbQe8kCyYfytlGMI").buildAsync();
		jda.addEventListener(new MyListener());
	}

}



