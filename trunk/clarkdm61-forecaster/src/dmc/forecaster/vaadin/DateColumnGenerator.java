package dmc.forecaster.vaadin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.data.Property;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

/**
 * Required to specify date format
 * @author David
 *
 */
public class DateColumnGenerator implements ColumnGenerator {

	SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	
	@Override
	public Object generateCell(Table source, Object itemId, Object columnId) {
		 // Get the object stored in the cell as a property
        Property prop =
            source.getItem(itemId).getItemProperty(columnId);
        if (prop.getType().equals(Date.class)) {
            
        	Date date = (Date) prop.getValue(); 
        	
        	Label label = new Label(df.format(date));

            return label;
        }
        return null;
	}

}
