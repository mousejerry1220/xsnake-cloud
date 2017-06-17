package org.xsnake.xflow.core;

import java.util.List;

import org.xsnake.xflow.api.Participant;

/**
 * 参与者查找器
 * @author Jerry.Zhao
 *
 */
public abstract class ParticipantHandle {

	public abstract List<Participant> findParticipantList(ProcessInstanceContext context);

	
}
