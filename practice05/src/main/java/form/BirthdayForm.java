package form;

import org.apache.struts.validator.ValidatorForm;

/**
 * 誕生日情報Bean
 * @author e_kou
 */
public class BirthdayForm extends ValidatorForm{
	/** 誕生日*/
	private String birthday;

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}
