package com.jeevan.finediner.activity;

import java.io.Serializable;

class Message implements Serializable {
		private static final long serialVersionUID = 1L;
		int type;
		String st;
		
		Message(int type, String st){
			this.type = type;
			this.st = st;
		}
		
	}