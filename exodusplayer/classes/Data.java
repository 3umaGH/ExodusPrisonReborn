package exodusplayer.classes;

import java.util.HashMap;

public class Data {

	HashMap<String, String> data = new HashMap<String, String>();

	public Data(){
		
	}
	
	public void setData(String name, Long data) {
		this.data.put(name, String.valueOf(data));
	}

	public void setDataInt(String name, int data) {
		this.data.put(name, String.valueOf(data));
	}

	public void setDataString(String name, String data) {
		this.data.put(name, data);
	}

	public void removeDataString(String name) {
		this.data.remove(name);
	}

	public Long getData(String name) {
		if (this.data.containsKey(name))
			return Long.parseLong(this.data.get(name));
		else
			return (long) -1;
	}

	public int getDataInt(String name) {
		if (this.data.containsKey(name))
			return Integer.parseInt(this.data.get(name));
		else
			return -1;
	}

	public String getDataString(String name) {
		if (this.data.containsKey(name))
			return this.data.get(name);
		else
			return "";
	}

	public HashMap<String, String> getData() {
		return data;
	}

}
