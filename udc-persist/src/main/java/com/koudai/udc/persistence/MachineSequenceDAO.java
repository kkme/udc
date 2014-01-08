package com.koudai.udc.persistence;

import com.koudai.udc.domain.MachineSequence;

public interface MachineSequenceDAO {

	void save(MachineSequence machineSequence);

	MachineSequence getMachineSequenceByMachineId(String machineId);

	MachineSequence getMachineSequenceById(Long id);

}
