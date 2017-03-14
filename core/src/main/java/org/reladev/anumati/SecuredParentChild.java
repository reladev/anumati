package org.reladev.anumati;


public interface SecuredParentChild<Key> {
	Key getParentId();
	void setParentId(Key id);
	SecuredObjectType getParentType();

	Key getChildId();
	SecuredObjectType getChildType();
}
