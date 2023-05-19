package org.eclipse.acceleo.module.jhispter.php.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import io.github.jhipster.jdl.jdl.JdlEntity;
import io.github.jhipster.jdl.jdl.JdlEntityField;
import io.github.jhipster.jdl.jdl.JdlEnum;
import io.github.jhipster.jdl.jdl.JdlEnumFieldType;
import io.github.jhipster.jdl.jdl.JdlEnumValue;
import io.github.jhipster.jdl.jdl.JdlBooleanFieldType;
import io.github.jhipster.jdl.jdl.JdlDateFieldType;
import io.github.jhipster.jdl.jdl.JdlFieldType;
import io.github.jhipster.jdl.jdl.JdlNumericFieldType;
import io.github.jhipster.jdl.jdl.JdlStringFieldType;

public class UtilsServices {
	
	
	public String getConstructorArgs(JdlEntity jdlEntity) {
		List<String> names= new ArrayList<String>();
		EList<JdlEntityField> listJdlEntityField = jdlEntity.getFieldDefinition().getFields();
		for (Iterator iterator = listJdlEntityField.iterator(); iterator.hasNext();) {
			JdlEntityField jdlEntityField = (JdlEntityField) iterator.next();
			String typeAndName ="?"+ getEntityFieldTypeName(jdlEntityField.getType())+ " $"+jdlEntityField.getName();
			names.add(typeAndName);			
		}
		
		return String.join(",", names);
	}
	public String getConstructorArgsAction(JdlEntity jdlEntity) {
		List<String> names= new ArrayList<String>();
		EList<JdlEntityField> listJdlEntityField = jdlEntity.getFieldDefinition().getFields();
		for (Iterator iterator = listJdlEntityField.iterator(); iterator.hasNext();) {
			JdlEntityField jdlEntityField = (JdlEntityField) iterator.next();
			String typeAndName ="$"+toLowerFirst(jdlEntity.getName())+"_json->"+toLowerFirst(jdlEntityField.getName());
			names.add(typeAndName);			
		}
		
		return String.join(",", names);
	}
	
	
	
	private String toLowerFirst(String value){
		if(value!=null && value.length()>1) {
			String firts=value.substring(0, 1).toLowerCase();
			return firts.concat(value.substring(1,value.length()));
		}
		
		return value;
	}
	
	public String getValidationsListAction(JdlEntity jdlEntity) {
		List<String> validations= new ArrayList<String>();
		validations.add("!isset($body)");
		EList<JdlEntityField> listJdlEntityField = jdlEntity.getFieldDefinition().getFields();
		for (Iterator iterator = listJdlEntityField.iterator(); iterator.hasNext();) {
			JdlEntityField jdlEntityField = (JdlEntityField) iterator.next();
			if(ifIsMandatory(jdlEntityField)) {
			String typeAndName ="!isset($body['"+toLowerFirst(jdlEntityField.getName())+"'])";
			validations.add(typeAndName);
			}
		}
		
		return String.join(" || ", validations);
	}
	
	public String getCharIfIsMandatory(JdlEntityField aEntityField) {
		String name=aEntityField.eAllContents().toString();
		if(name.contains("ValidatorImpl"))
			return "?";
		else
			return "";		
	}
	
	public boolean ifIsMandatory(JdlEntityField aEntityField) {
		String name=aEntityField.eAllContents().toString();
		if(name.contains("ValidatorImpl"))
			return true;
		else
			return false;		
	}
	
	public String getFieldDataType(JdlEntityField aEntityField) {
		String name=getEntityFieldTypeName(aEntityField.getType());
		if(name.contains("ValidatorImpl"))
			return "?";
		else
			return "";		
	}
	
	public String getEntityFieldTypeName(JdlFieldType jdlEntityField) {
			if (jdlEntityField instanceof JdlStringFieldType) { 
					return "string";
			}
				else if (jdlEntityField instanceof JdlEnumFieldType ) {
					return ((io.github.jhipster.jdl.jdl.JdlEnumFieldType) jdlEntityField).getElement().getName();
				}
					else if (jdlEntityField instanceof JdlBooleanFieldType) {
						return "bool";
					}   
						
				else if (jdlEntityField instanceof  JdlDateFieldType)   
						return "DateTime";
				else if (jdlEntityField instanceof  JdlNumericFieldType){
					String numeriType=((io.github.jhipster.jdl.jdl.JdlNumericFieldType) jdlEntityField).getElement().getName();
					return getNumericDataTypeNamePhp(numeriType);
				}
				else
					jdlEntityField.getClass().getName();
			return null;	
	}
	
	public String getNumericDataTypeNamePhp(String element) {
			if (element.equalsIgnoreCase("Integer") || element.equalsIgnoreCase("Long"))
				return "int";
			else if (element.equalsIgnoreCase("Double"))
				return "double";
			else
				return element;
	}
			

	
	public String camelToSnake(String str)
    {
 
        // Empty String
        String result = "";
 
        // Append first character(in lower case)
        // to result string
        char c = str.charAt(0);
        result = result + Character.toLowerCase(c);
 
        // Traverse the string from
        // ist index to last index
        for (int i = 1; i < str.length(); i++) {
 
            char ch = str.charAt(i);
 
            // Check if the character is upper case
            // then append "_" and such character
            // (in lower case) to result string
            if (Character.isUpperCase(ch)) {
                result = result + "_";
                result
                    = result
                      + Character.toLowerCase(ch);
            }
 
            // If the character is lower case then
            // add such character into result string
            else {
                result = result + ch;
            }
        }
 
        // return the result
        return result;
    }
	
	public String getValuesFromEnum(JdlEnum jdlEnum) {
		List<String> values= new ArrayList<String>();
		EList<JdlEnumValue> listJdlEnumValue = jdlEnum.getValues();
		for (Iterator iterator = listJdlEnumValue.iterator(); iterator.hasNext();) {
			JdlEnumValue jdlEnumValue = (JdlEnumValue) iterator.next();
			String enumValue ="'"+jdlEnumValue.getValue()+"'";
			values.add(enumValue);			
		}
		
		return String.join(",", values);
	}

}
