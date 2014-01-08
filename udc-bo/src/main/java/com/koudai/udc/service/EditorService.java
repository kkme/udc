package com.koudai.udc.service;

import com.koudai.udc.exception.TransactionCallbackException;

public interface EditorService {

	void dealWithEditorData(String day) throws TransactionCallbackException;

}
