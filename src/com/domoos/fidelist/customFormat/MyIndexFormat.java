package com.domoos.fidelist.customFormat;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class MyIndexFormat extends Format{
	public String[] Labels=null;
	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo,FieldPosition pos){
		float f1=((Number)obj).floatValue();
		int index = Math.round(f1);
		if(Labels==null||Labels.length<=index || Math.abs(f1 - index) >0.1)
			return new StringBuffer("");
		return new StringBuffer(Labels[index]);
	}
	@Override
	public Object parseObject(String string, ParsePosition position) {
		// TODO Auto-generated method stub
		return null;
	}

}
