package com.jeevan.finediner;

import java.io.Serializable;

public class Request implements Serializable {
		private static final long serialVersionUID = 1L;
		public int type;
		String st;
		public Object o;
		public Request(int type, Object o){
			this.type = type;
			this.o = o;
		}
		
	}