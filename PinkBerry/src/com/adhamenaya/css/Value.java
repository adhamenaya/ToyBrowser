package com.adhamenaya.css;

abstract public class Value {

	protected String valueString = "";
	
	abstract public void setKeyword(Keyword keyword);

	abstract public void setColor(Color color);

	abstract public void setLength(Length length);

	abstract public float toPx();
	
	
	public String getValueString(){
		return this.valueString;
	}
}
