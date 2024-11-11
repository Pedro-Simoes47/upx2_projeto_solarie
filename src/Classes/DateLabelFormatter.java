package Classes;

import javax.swing.*;

public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private final java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Object stringToValue(String text) throws java.text.ParseException {
        return dateFormatter.parse(text);
    }

    @Override
    public String valueToString(Object value) {
        if (value != null) {
            java.util.Calendar cal = (java.util.Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}
