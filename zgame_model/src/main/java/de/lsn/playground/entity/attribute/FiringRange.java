package de.lsn.playground.framwork.attribute;

import javax.persistence.Embeddable;


@SuppressWarnings("serial")
@Embeddable
public class FiringRange extends Range {

	private static final String TYPE = "FiringRange";
	
	private Integer firingRangeValue;
	
	public FiringRange() {
		this(0);
	}
	
	public FiringRange(Integer rangeValue) {
		super(TYPE);
		this.firingRangeValue = rangeValue;
	}
	
	public Integer getFiringRangeValue() {
		return firingRangeValue;
	}
	
	public void setFiringRangeValue(Integer firingRangeValue) {
		this.firingRangeValue = firingRangeValue;
	}

	@Override
	public Integer getRangeValue() {
		return this.firingRangeValue;
	}
	
}
