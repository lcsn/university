package de.lsn.playground.entity.attribute;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Coord extends Attribute {

	private Integer xCoord;
	
	private Integer yCoord;
	
	public Coord() {
		// TODO Auto-generated constructor stub
	}

	public Coord(Integer xCoord, Integer yCoord) {
		super();
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
	public Integer getxCoord() {
		return xCoord;
	}
	
	protected void setxCoord(Integer xCoord) {
		this.xCoord = xCoord;
	}
	
	public Integer getyCoord() {
		return yCoord;
	}
	
	protected void setyCoord(Integer yCoord) {
		this.yCoord = yCoord;
	}
	
	public String toString() {
		return this.xCoord + ", " + this.yCoord;
	}
	
}
