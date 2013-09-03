package de.lsn.playground.zgame.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="de.lsn.playground.zgame.converter.DateToCalendarConverter")
public class DateToCalendarConverter implements Converter {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(value));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Calendar cal = (Calendar) value;
		return sdf.format(cal.getTime()).toString();
	}

}
