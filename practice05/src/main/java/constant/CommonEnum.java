package constant;

/**
 * 共通定数Enun
 * @author e_kou
 */
public enum CommonEnum {
	DRIVER("org.postgresql.Driver"), 
	URL("jdbc:postgresql://localhost:5432/kadai"), 
	USER("postgres"), 
	PASS("jiang"),
	;

	private String constant;

	private CommonEnum(String constant) {
		this.constant = constant;
	}

	public String getConstant() {
		return this.constant;
	}

}
