package com.domoos.fidelist;

public class itemPlayer {
	protected String id;
	protected String name;
	protected String title;
	protected String wTitle;
	protected String oTitle;
	protected String fed;
	protected String rtg;
	protected String rpd;
	protected String blz;
	protected String byear;
	protected String s;
	protected String iflag;
	
	public itemPlayer(String id, String name, String t, String wt, String ot, String fed, String rtg, String rpd, String blz, String byear, String s, String iflag ){
		this.id=id;
		this.name=name;
		this.title= t;
		this.wTitle=wt;
		this.oTitle=ot;
		this.fed=fed;
		this.rtg=rtg;
		this.rpd=rpd;
		this.blz=blz;
		this.byear=byear;
		this.s=s;
		this.iflag=iflag;
	}
	
	public String getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getRtg(){
		return this.rtg;
	}
	public void setRtg(String rtg){
		this.rtg = rtg;
	}

}
