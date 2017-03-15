package org.reladev.anumati;


public interface SecuredParentChild {
	Object getParentId();
	void setParentId(Object id);
	SecuredObjectType getParentType();

	Object getChildId();
	SecuredObjectType getChildType();
}
