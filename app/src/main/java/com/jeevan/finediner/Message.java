package com.jeevan.finediner;

import java.io.Serializable;

public class Message implements Serializable {
		private static final long serialVersionUID = 1L;
		int type;
		String st;
		Object o;
		public Message(int type, Object o){
			this.type = type;
			this.o = o;
		}
		
	}