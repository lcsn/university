package de.lsn.playground.zgame.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import de.lsn.playground.framwork.Gender;

@FacesConverter(value="de.lsn.playground.zgame.converter.StringToGenderConverter")
public class StringToGenderConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Gender gender = null;
		try {
			gender = Gender.valueOf(value);
		} catch (Exception e) {
//			log error
		}
		return gender;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return (String) value;
	}

}
