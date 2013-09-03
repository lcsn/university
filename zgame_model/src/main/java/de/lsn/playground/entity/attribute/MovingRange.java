package de.lsn.playground.framwork.attribute;

import javax.persistence.Embeddable;


@SuppressWarnings("serial")
@Embeddable
public class MovingRange extends Range {

	private static final String TYPE = "MovingRange";
	
	private Integer movingRangeValue;
	
	public MovingRange() {
		this(0);
	}
	
	public MovingRange(Integer rangeValue) {
		super(TYPE);
		this.movingRangeValue = rangeValue;
	}
	
	public Integer getMovingRangeValue() {
		return movingRangeValue;
	}
	
	public void setMovingRangeValue(Integer movingRangeValue) {
		this.movingRangeValue = movingRangeValue;
	}
	
	@Override
	public Integer getRangeValue() {
		return this.movingRangeValue;
	}
	
}
