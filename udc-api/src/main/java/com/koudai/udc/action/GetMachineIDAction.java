package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.MachineSequence;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.MachineSequenceDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetMachineIDAction extends ActionSupport {

	private static final long serialVersionUID = 8767390158313101216L;

	private static final Logger LOGGER = Logger.getLogger(GetMachineIDAction.class);

	private MachineSequenceDAO machineSequenceDAOR;

	private String sequenceId;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			if (S.isInvalidValue(sequenceId)) {
				throw new IncorrectInputParameterException("Sequence id is null or empty");
			}
			long beginTime = System.currentTimeMillis();
			LOGGER.info("Query machine id with sequence id < " + sequenceId + " >");
			MachineSequence machineSequence = machineSequenceDAOR.getMachineSequenceById(Long.valueOf(sequenceId));
			if (machineSequence == null) {
				StringBuffer errorMsg = new StringBuffer("no machine register with sequence id < ");
				errorMsg.append(sequenceId).append(" >");
				result = getMachineResult(S.ERROR_CODE, errorMsg.toString(), null);
			} else {
				result = getMachineResult(S.SUCCESS_CODE, S.EMPTY_STR, machineSequence.getMachineId());
			}
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getMachineID cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getMachineResult(S.ERROR_CODE, e.getMessage(), null);
			return ERROR;
		}
	}

	private JSONObject getMachineResult(int code, String reason, String id) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONObject idObject = new JSONObject();
			if (id != null) {
				idObject.put("machineid", id);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("sequeceidmachineid", idObject);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setMachineSequenceDAOR(MachineSequenceDAO machineSequenceDAOR) {
		this.machineSequenceDAOR = machineSequenceDAOR;
	}

	public void setSequenceID(String sequenceID) {
		this.sequenceId = sequenceID;
	}

	public JSONObject getResult() {
		return result;
	}

}
