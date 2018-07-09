package com.snoopinou.BotASCII;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;



public class Main {
	
	public static void main(String args[]) throws LoginException {
		JDA jda = new JDABuilder(AccountType.BOT).setToken("NDY1OTE0MjgxODE3NDA3NDg4.DiUcBQ.jHC1-LjIv2N4M6hzT5CuPk1dV8c").buildAsync();
		System.out.println(jda.asBot().getInviteUrl());
		jda.addEventListener(new MyListener());
	}

}



