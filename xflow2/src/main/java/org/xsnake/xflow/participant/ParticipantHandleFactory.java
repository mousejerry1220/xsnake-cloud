package org.xsnake.xflow.participant;

import org.dom4j.Element;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.core.Activity;
import org.xsnake.xflow.core.ParticipantHandle;

public class ParticipantHandleFactory {

	public static ParticipantHandle getParticipantHandle(Activity activity, Element participantElement) throws DefinitionException {
		ParticipantHandle handle = null;
		String type = participantElement.attributeValue("type");
		//如果是指定参与者
//		if(ParticipantHandle.Type.assign.getType().equals(type)){
//			handle = new AssignParticipant(activity, participantElement);
//		}
//		else if(ParticipantHandle.Type.creator.getType().equals(type)){
//			handle = new CreatorParticipant(activity, participantElement);
//		}
//		else if(ParticipantHandle.Type.runtimeAssign.getType().equals(type)){
//			handle = new RuntimeAssignParticipant(activity, participantElement);
//		}
//		else if(ParticipantHandle.Type.expand.getType().equals(type)){
//			handle = new ExpandParticipant(activity, participantElement);
//		}
//		else if(ParticipantHandle.Type.positionInForm.getType().equals(type)){
//			handle = new PositionInFormParticipant();
//		}
//		else if(ParticipantHandle.Type.employeeInForm.getType().equals(type)){
//			handle = new EmployeeInFormParticipant();
//		}
//		else if(ParticipantHandle.Type.positionInForm2.getType().equals(type)){
//			handle = new PositionInForm2Participant();
//		}
//		else if(ParticipantHandle.Type.employeeInForm2.getType().equals(type)){
//			handle = new EmployeeInForm2Participant();
//		}
//		else if(ParticipantHandle.Type.sql.getType().equals(type)){
//			handle = new SQLParticipant(activity, participantElement);
//		}
//		else {
//			throw new DefinitionException("无效的参与者类型");
//		}
		return handle;
	}
	
}
