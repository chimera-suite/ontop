package it.unibz.krdb.sql.api;

/**
 * This class represents the literal of decimal value.
 */
public class DecimalLiteral extends NumericLiteral {

	private static final long serialVersionUID = 6801280683568130405L;
	
	/**
	 * The float value.
	 */
	protected Float value;

	public DecimalLiteral(String value) {
		set(new Float(value));
	}

	public void set(Float value) {
		this.value = value;
	}

	public Float get() {
		return value;
	}

	@Override
	public String toString() {
		return get().toString();
	}
}