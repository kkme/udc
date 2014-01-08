package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.MachineSequence;

public class MachineSequenceDAOHibernate extends HibernateDaoSupport implements MachineSequenceDAO {

	@Override
	public void save(MachineSequence machineSequence) {
		getHibernateTemplate().save(machineSequence);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MachineSequence getMachineSequenceByMachineId(String machineId) {
		List<MachineSequence> machineSequences = getHibernateTemplate().find("from MachineSequence where machineId = ?", new Object[] { machineId });
		if (machineSequences.size() != 0) {
			return (MachineSequence) machineSequences.get(0);
		}
		return null;
	}

	@Override
	public MachineSequence getMachineSequenceById(Long id) {
		return getHibernateTemplate().get(MachineSequence.class, id);
	}

}
