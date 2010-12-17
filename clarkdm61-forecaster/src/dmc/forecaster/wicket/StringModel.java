package dmc.forecaster.wicket;

import org.apache.wicket.model.AbstractReadOnlyModel;;

public class StringModel extends AbstractReadOnlyModel<String> {
	private static final long serialVersionUID = -5714213355990456822L;
	private String value;
	
	public StringModel(String value) {
		setValue(value);
	}

	@Override
	public String getObject() {
		return getValue();
	}

	private String getValue() {
		return value;
	}

	private void setValue(String value) {
		this.value = value;
	}

}
