package dmc.forecaster.wicket;

import org.apache.wicket.model.AbstractReadOnlyModel;

public class RowAlternatorModel extends AbstractReadOnlyModel<String> {
	
	private static final long serialVersionUID = -3439085347662862141L;
	int index = 0;

	@Override
	public String getObject() {
		return (index++ % 2 == 1) ? "even" : "odd";
	}

}
