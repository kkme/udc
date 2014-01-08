package com.koudai.udc.interceptor;

import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AbstractLifecycleInterceptor;

public class SpringTransactionInterceptor extends AbstractLifecycleInterceptor implements Transaction {

	private static final long serialVersionUID = 3173795904152440895L;

	private static final Logger LOGGER = Logger.getLogger(SpringTransactionInterceptor.class);

	transient PlatformTransactionManager ptm;

	transient ThreadLocal<TransactionStatus> myTransactionHolder = new ThreadLocal<TransactionStatus>();

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		final TransactionStatus ts = startATransaction();
		handleTransactionAction(actionInvocation);
		try {
			return super.intercept(actionInvocation);
		} catch (Exception e) {
			rollbackIfTransactionStatusIsNotComplete(ts);
			throw e;
		}
	}

	private void rollbackIfTransactionStatusIsNotComplete(final TransactionStatus ts) {
		if (!ts.isCompleted()) {
			ptm.rollback(ts);
		}
	}

	private void handleTransactionAction(ActionInvocation actionInvocation) {
		Object action = actionInvocation.getAction();
		LOGGER.info("action name: " + action);
		if (action instanceof TransactionAction) {
			((TransactionAction) action).setTransaction(this);
		}
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		ptm = transactionManager;
	}

	public void beforeResult(ActionInvocation actionInvocation, String result) {
		LOGGER.info("before do beforeResult: " + result);
		super.beforeResult(actionInvocation, result);
		commitTransaction();
	}

	public void commitAndRestart() {
		commitTransaction();
		startATransaction();
	}

	private TransactionStatus startATransaction() {
		TransactionStatus ts = ptm.getTransaction(new DefaultTransactionDefinition());
		myTransactionHolder.set(ts);
		return ts;
	}

	private void commitTransaction() {
		TransactionStatus ts = myTransactionHolder.get();
		if (!ts.isCompleted()) {
			ptm.commit(ts);
		}
	}
}
