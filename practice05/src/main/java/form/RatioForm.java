package form;

import java.util.Map;

/**
 * 割合情報Bean
 * @author e_kou
 */
public class RatioForm {
	/** 過去半年の割合情報 */
	private Map<String, Double> halfYearRatio;

	/** 本日の割合情報 */
	private Map<String, Double> todayRatio;

	public Map<String, Double> getHalfYearRatio() {
		return halfYearRatio;
	}

	public void setHalfYearRatio(Map<String, Double> halfYearRatio) {
		this.halfYearRatio = halfYearRatio;
	}

	public Map<String, Double> getTodayRatio() {
		return todayRatio;
	}

	public void setTodayRatio(Map<String, Double> todayRatio) {
		this.todayRatio = todayRatio;
	}

}
