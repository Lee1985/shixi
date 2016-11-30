package com.bluemobi.www.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;

import com.bluemobi.www.jackson.JacksonObjectMapper;

public class JacksonJsonMapper {
	static volatile ObjectMapper objectMapper = null;

	private JacksonJsonMapper() {
	}

	public static ObjectMapper getInstance() {
		if (objectMapper == null) {
			synchronized (ObjectMapper.class) {
				if (objectMapper == null) {
					objectMapper = new JacksonObjectMapper();
				}
			}
		}
		return objectMapper;
	}

	public static String objectToJson(Object beanobject) {
		ObjectMapper mapper = JacksonJsonMapper.getInstance();
		String resutl = null;
		try {
			resutl = mapper.writeValueAsString(beanobject);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resutl;
	}

	public static String objectToJson(Object beanobject, boolean nonNull) {
		if (nonNull) {
			ObjectMapper mapper = JacksonJsonMapper.getInstance();
			mapper.setSerializationInclusion(Inclusion.NON_NULL);
			String resutl = null;
			try {
				resutl = mapper.writeValueAsString(beanobject);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return resutl;
		} else {
			return objectToJson(beanobject);
		}
	}

	public static <T> T jsonToObject(String jsonString, Class<T> claszz) {
		T t = null;
		try {
			t = JacksonJsonMapper.getInstance().readValue(jsonString, claszz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> jsonToList(String jsonString, Class<T> claszz){  
		ObjectMapper mapper = JacksonJsonMapper.getInstance();
        JavaType javaType = getCollectionType(ArrayList.class, claszz);
        List<T> list = null;
        try {
        	list = (List<T>)mapper.readValue(jsonString, javaType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        return list;
    }   
	   
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
		ObjectMapper mapper = JacksonJsonMapper.getInstance();
	    return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}
}
