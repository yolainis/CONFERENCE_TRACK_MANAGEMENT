package com.fisa.ctm.enumerated;

public enum DurationUnit {
	MINUTES(1, "min"), LIGHTENING(5, "lightning");

	private int factor;
	private String stringRepresentation;

	private DurationUnit(int factor, String stringRepresentation) {
		this.factor = factor;
		this.stringRepresentation = stringRepresentation;
	}

	public int inMinutes(int duration) {
		return factor * duration;
	}

	public int getFactor() {
		return factor;
	}

	public String getStringRepresentation() {
		return stringRepresentation;
	}

	public void setFactor(int factor) {
		this.factor = factor;
	}

	public void setStringRepresentation(String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}

	@Override
	public String toString() {
		return stringRepresentation;
	}
}
