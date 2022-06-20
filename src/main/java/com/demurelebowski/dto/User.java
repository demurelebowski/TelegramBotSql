package com.demurelebowski.dto;

import com.demurelebowski.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;

@Getter @Setter @ToString
public class User {
	private final Long timestamp;
	private final Long chatID;
	private String name;
	private String date;
	private String password;
	private boolean authorized;

	public User(String name, long chatID) throws IOException {
		this.chatID = chatID;
		this.timestamp = UserService.getTimeInLong();
		this.name = name;
		this.password = UserService.generateSimplePassword();
		this.date = UserService.getCurrentLocalDateTimeStamp();
		this.authorized = false;
		//insertUserSql(this);
	}

}
