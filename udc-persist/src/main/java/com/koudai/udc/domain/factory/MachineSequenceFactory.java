package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.MachineSequence;

public interface MachineSequenceFactory {

	MachineSequence newInstance(String machineId);

}
