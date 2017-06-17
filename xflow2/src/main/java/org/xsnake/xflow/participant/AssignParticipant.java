package org.xsnake.xflow.participant;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.api.Participant;
import org.xsnake.xflow.core.ParticipantHandle;
import org.xsnake.xflow.core.ProcessInstanceContext;

public class AssignParticipant extends ParticipantHandle {

	List<Participant> participantsList = new ArrayList<Participant>();

	@SuppressWarnings("unchecked")
	public AssignParticipant(Element participantElement) throws DefinitionException {
		List<Element> elements = participantElement.elements("participant");
		if (elements == null || elements.size() == 0) {
			throw new DefinitionException("还未指定具体的参与者");
		}
		parseFields(elements);
	}

	@Override
	public List<Participant> findParticipantList(ProcessInstanceContext context) {
		return participantsList;
	}

	private void parseFields(List<Element> elements) {
		if (elements != null && elements.size() > 0) {
			for (Element e : elements) {
				String id = e.attributeValue("id");
				String name = e.attributeValue("name");
				String type = e.attributeValue("type");
				Participant participant = new Participant(id, name, type);
				participantsList.add(participant);
			}
		}
	}

}
