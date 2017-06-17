package org.xsnake.xflow.participant;

import java.util.ArrayList;
import java.util.List;

import org.xsnake.xflow.api.Participant;
import org.xsnake.xflow.api.model.ProcessInstance;
import org.xsnake.xflow.core.ParticipantHandle;
import org.xsnake.xflow.core.ProcessInstanceContext;

public class CreatorParticipant extends ParticipantHandle{

	@Override
	public List<Participant> findParticipantList(ProcessInstanceContext context) {
		List<Participant> list = new ArrayList<Participant>();
		ProcessInstance processInstance = context.getProcessInstance();
		Participant creator = new Participant(processInstance.getCreatorId(),processInstance.getCreatorName(),processInstance.getCreatorType());
		list.add(creator);
		return list;
	}
}
