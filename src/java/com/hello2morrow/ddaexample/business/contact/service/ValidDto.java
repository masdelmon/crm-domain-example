package com.hello2morrow.ddaexample.business.contact.service;

public class ValidDto extends com.hello2morrow.dda.business.common.service.DomainObjectDto {
	private static final long serialVersionUID = -7936225573099290761L;
	private final String m_finalField;
	//private String m_nonFinalField;
	
	public ValidDto(String finalName, String nonFinalName)
	{
		m_finalField = finalName;
		//m_nonFinalField = nonFinalName;
	}
	
	public String getName() {
		return m_finalField;
	}
	
//	public String getNonFinalField() {
//		return m_nonFinalField;
//	}
}
