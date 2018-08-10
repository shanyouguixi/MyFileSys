package com.myFileSys.common.utils;

public class ToString {
	
	public static String toString(Integer[] arr){
		String str = "";
		if(arr!=null&&arr.length>0){
			for(Integer item:arr){
				str +=item+",";
			}
			str.substring(0, str.lastIndexOf(",")-1);
		}
		return str;
	}
}
