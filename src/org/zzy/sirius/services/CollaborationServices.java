package org.zzy.sirius.services;

import java.util.*;
import java.util.stream.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.xtext.example.edgecloudmodel.eCModel.*;
public class CollaborationServices {
	
	public CollaborationServices(){}
	static List<CParticipant> participantList = new ArrayList<>();
	static List<CEnd> endList = new ArrayList<>();
	static List<CMessage> messageList = new ArrayList<>();
	static List<CExecution> executionList = new ArrayList<>();
	static ECModelFactory ecModelFactory = ECModelFactory.eINSTANCE;
	static Map<CExecution, CEnd> executionStartEndMap = new HashMap<>();
	static Map<CExecution, CEnd> executionFinishEndMap = new HashMap<>();
	static Map<CMessage, CEnd> messageStartEndMap = new HashMap<>();
	static Map<CMessage, CEnd> messageFinishEndMap = new HashMap<>();
	static Map<CMessage, CMultiEnd> messageStartTargetMap = new HashMap<>();
	static Map<CMessage, CMultiEnd> messageFinishTargetMap = new HashMap<>();
	static Map<CParticipant, List<CExecution>> executionMap = new HashMap<>();
	static {
		CEnd tempEnd = ecModelFactory.createCEnd();
		tempEnd.setName("a1");
		endList.add(tempEnd);
		tempEnd = ecModelFactory.createCEnd();
		tempEnd.setName("a2");
		endList.add(tempEnd);
		tempEnd = ecModelFactory.createCEnd();
		tempEnd.setName("a3");
		endList.add(tempEnd);
		tempEnd = ecModelFactory.createCEnd();
		tempEnd.setName("a4");
		endList.add(tempEnd);
		tempEnd = ecModelFactory.createCEnd();
		tempEnd.setName("a5");
		endList.add(tempEnd);
		
		
		CParticipant tempParticipant = ecModelFactory.createCParticipant();
		tempParticipant.setName("a1");
		participantList.add(tempParticipant);
		tempParticipant = ecModelFactory.createCParticipant();
		tempParticipant.setName("a2");
		participantList.add(tempParticipant);
		
		
		
		
		CExecution tempExecution = ecModelFactory.createCExecution();
		tempExecution.setName("a1");
		tempExecution.setSender(endList.get(1));
		tempExecution.setReceiver(endList.get(4));
		executionList.add(tempExecution);
		executionStartEndMap.put(tempExecution, endList.get(1));
		executionFinishEndMap.put(tempExecution, endList.get(4));
		
		
		CMessage tempMessage = ecModelFactory.createCMessage();
		tempMessage.setName("a1");
		tempMessage.setSender(endList.get(0));
		tempMessage.setReceiver(endList.get(1));
		messageList.add(tempMessage);
		messageStartEndMap.put(tempMessage, endList.get(0));
		messageFinishEndMap.put(tempMessage, endList.get(1));
		messageStartTargetMap.put(tempMessage, participantList.get(0));
		messageFinishTargetMap.put(tempMessage, executionList.get(0));
		tempMessage = ecModelFactory.createCMessage();
		tempMessage.setName("a2");
		tempMessage.setSender(endList.get(2));
		tempMessage.setReceiver(endList.get(3));
		messageList.add(tempMessage);
		messageStartEndMap.put(tempMessage, endList.get(2));
		messageFinishEndMap.put(tempMessage, endList.get(3));
		messageStartTargetMap.put(tempMessage, executionList.get(0));
		messageFinishTargetMap.put(tempMessage, executionList.get(0));

	}
	public List<CParticipant> getParticipants(Collaboration o){
		return participantList;
	}
	static boolean alreadyAdd = false;
	public Boolean hello(Collaboration collaboration){
		if(!alreadyAdd) {
//			try {
				collaboration.getMessages().addAll(messageList);
				collaboration.getEnds().addAll(endList);
				collaboration.getExecutions().addAll(executionList);
				collaboration.getParticipants().addAll(participantList);
				alreadyAdd = true;
//			}catch(Exception e) {
//				System.out.println("hello, exception fucker");
//				e.printStackTrace();
//			}
		}
		System.out.println("hello, fucker");
		return true;
	}
	public List<CEnd> getEnds(Collaboration collaboration){
		return endList;
	}
	public List<CMessage> getMessages(Collaboration collaboration){
		return messageList;
	}
	public List<CExecution> getExecutions(Collaboration collaboration, CParticipant participant){
		if(participant.equals(participantList.get(0))) {
			return Collections.emptyList();
		}else {
			return executionList;
		}
		
	}
	public CEnd getMessageStartEnd(Collaboration collaboration, CMessage message) {
		return messageStartEndMap.get(message);
	}
	public CEnd getMessageFinishEnd(Collaboration collaboration, CMessage message) {
		return messageFinishEndMap.get(message);
	}
	public CMultiEnd getMessageStartTarget(Collaboration collaboration, CMessage message) {
		return messageStartTargetMap.get(message);
	}
	public CMultiEnd getMessageFinishTarget(Collaboration collaboration, CMessage message) {
		return messageFinishTargetMap.get(message);
	}
	public CEnd getExecutionStartEnd(Collaboration collaboration, CExecution execution) {
		return executionStartEndMap.get(execution);
	}
	public CEnd getExecutionFinishEnd(Collaboration collaboration, CExecution execution) {
		return executionFinishEndMap.get(execution);
	}
	
}
