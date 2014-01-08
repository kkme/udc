package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.MachineSequence;

public class SimpleMachineSequenceFactory implements MachineSequenceFactory {

	@Override
	public MachineSequence newInstance(String machineId) {
		return new MachineSequence(machineId);
	}

}
