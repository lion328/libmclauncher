package com.lion328.libmclauncher.gamelaunch.json;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateType implements JsonSerializer<Date>, JsonDeserializer<Date> {

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	
	@Override
	public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
		return new JsonPrimitive(format.format(arg0));
	}
	
	@Override
	public Date deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		try {
			return format.parse(arg0.getAsString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
