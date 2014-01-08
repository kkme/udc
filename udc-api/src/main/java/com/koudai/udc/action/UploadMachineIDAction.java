package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.MachineSequence;
import com.koudai.udc.domain.factory.MachineSequenceFactory;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.MachineSequenceDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class UploadMachineIDAction extends ActionSupport {

	private static final long serialVersionUID = -4120083851358783744L;

	private static final Logger LOGGER = Logger.getLogger(UploadMachineIDAction.class);

	private MachineSequenceDAO machineSequenceDAOW;

	private MachineSequenceFactory machineSequenceFactory;

	private String machineId;

	private JSONObject result;

	@Override
	public String execute() throws Exception {
		try {
			if (S.isInvalidValue(machineId)) {
				throw new IncorrectInputParameterException("Machine id is null or empty");
			}
			long beginTime = System.currentTimeMillis();
			LOGGER.info("Query sequence id with machine id < " + machineId + " >");
			result = getMachineResult(S.SUCCESS_CODE, S.EMPTY_STR, getSequenceIdByMachineId(machineId));
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("uploadMachineID cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getMachineResult(S.ERROR_CODE, e.getMessage(), null);
			throw new ActionErrorDispatchException(ERROR, e);
		}
	}

	private String getSequenceIdByMachineId(String machineId) {
		MachineSequence machineSequence = machineSequenceDAOW.getMachineSequenceByMachineId(machineId);
		if (machineSequence == null) {
			machineSequence = machineSequenceFactory.newInstance(machineId);
			machineSequenceDAOW.save(machineSequence);
		}
		return String.valueOf(machineSequence.getId());
	}

	private JSONObject getMachineResult(int code, String reason, String id) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONObject idObject = new JSONObject();
			if (id != null) {
				idObject.put("sequeceid", id);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("machineidsequeceid", idObject);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setMachineSequenceDAOW(MachineSequenceDAO machineSequenceDAOW) {
		this.machineSequenceDAOW = machineSequenceDAOW;
	}

	public void setMachineSequenceFactory(MachineSequenceFactory machineSequenceFactory) {
		this.machineSequenceFactory = machineSequenceFactory;
	}

	public void setMachineID(String machineID) {
		this.machineId = machineID;
	}

	public JSONObject getResult() {
		return result;
	}

}
