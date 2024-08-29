package form;

import java.util.List;

import omikuji.Omikuji;

/**
 * 過去半年の占い情報Bean
 * @author e_kou
 */
public class HalfYearResultForm {
	/** 過去半年の占い情報 */
	private List<Omikuji> halfYearResult;

	public List<Omikuji> getHalfYearResult() {
		return halfYearResult;
	}

	public void setHalfYearResult(List<Omikuji> halfYearResult) {
		this.halfYearResult = halfYearResult;
	}
}
