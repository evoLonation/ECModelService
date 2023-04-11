package org.zzy.sirius.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.transaction.*;
import org.eclipse.emf.transaction.util.*;
import org.xtext.example.edgecloudmodel.eCModel.*;

public class CollaborationState {
	List<CLifeline> lifelineList = new ArrayList<>();
	List<CEnd> endList = new ArrayList<>();
	List<CMessage> messageList = new ArrayList<>();
	List<CMessage> returnMessageList = new ArrayList<>();
	Map<CLifeline, List<CExecution>> executionMap = new HashMap<>();
	public List<CLifeline> getLifelines(){
		return lifelineList;
	}
	public List<CEnd> getEnds(){
		return endList;
	}
	public List<CMessage> getAllMessage(){
		return Stream.concat(messageList.stream(), returnMessageList.stream()).collect(Collectors.toList());
	}
	public List<CMessage> getMessages(){
		return messageList;
	}
	public List<CMessage> getReturnMessages(){
		return returnMessageList;
	}
	public List<CExecution> getExecutions(CLifeline lifeline){
		return executionMap.get(lifeline);
	}
	public List<CExecution> getExecutions(){
		return executionMap.values().stream().flatMap(e -> e.stream()).collect(Collectors.toList());
	}
	
	
	public CollaborationState(Collaboration collaboration) {
		initLifeline();
		var execution = startCall("call1", caller, callee);
		selfCall("call2", execution);
		selfCall("call3", execution);
		finishCall(true);
		
//		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(collaboration);
//	    domain.getCommandStack().execute(new RecordingCommand(domain) {
//	        @Override
//	        protected void doExecute() {
//	            collaboration.getMessages().addAll(messageList);
//	    		collaboration.getEnds().addAll(endList);
//	    		collaboration.getExecutions().addAll(executionMap.values()
//	    				.stream().flatMap(e -> e.stream()).collect(Collectors.toList()));
//	    		collaboration.getLifelines().addAll(lifelineList);
//	        }
//	    });
		
	}
	
	private CLifeline caller;
	private CLifeline callee;
	private void initLifeline() {
		var l1 = addLifeline("patient");
		var l2 = addLifeline("service");
		caller = l1;
		callee = l2;
	}
	
	static class CallInfo{
		CPoint source;
		CPoint target;
		CEnd finishEnd;
		
	}
	private Stack<CallInfo> callStack = new Stack<>();
	private CExecution startCall(String name, CPoint source, CLifeline target) {
		var info = new CallInfo();
		var end1 = addEnd();
		var end2 = addEnd();
		var end3 = createEnd();
		var execution = addExecution(target, end2, end3);
		addMessage(end1, end2, source, execution, name, false);
		info.source = source;
		info.target = execution;
		info.finishEnd = end3;
		callStack.push(info);
		return execution;
	}
	private void finishCall(boolean isReturn) {
		var info = callStack.pop();
		var e1 = info.finishEnd;
		addEnd(e1);
		if(isReturn) {	
			var e2 = addEnd();
			addMessage(e1, e2, info.target, info.source, "return", true);
		}
	}
	private void selfCall(String name, CExecution execution) {
		addMessage(addEnd(), addEnd(), execution, execution, name, false);
	}


	
	
	
	
	ECModelFactory ecModelFactory = ECModelFactory.eINSTANCE;
	private CLifeline addLifeline(String name) {
		CLifeline lifeline = ecModelFactory.createCLifeline();
		lifeline.setName(name);
		lifelineList.add(lifeline);
		return lifeline;
	}
	private int endNum = 0;
	private CEnd addEnd() {
		CEnd tempEnd = createEnd();
		addEnd(tempEnd);
		return tempEnd;
	}
	private void addEnd(CEnd end) {
		endList.add(end);
	}
	private CEnd createEnd() {
		CEnd tempEnd = ecModelFactory.createCEnd();
		tempEnd.setName("a" + endNum);
		endNum++;
		return tempEnd;
	}
	private void addMessage(CEnd sender, CEnd receiver, CPoint source, CPoint target, String name, boolean isReturn) {
		CMessage tempMessage = ecModelFactory.createCMessage();
		tempMessage.setName(name);
		tempMessage.setSender(sender);
		tempMessage.setReceiver(receiver);
		tempMessage.setSourcePoint(source);
		tempMessage.setTargetPoint(target);
		if(isReturn) {
			returnMessageList.add(tempMessage);
		}else {
			messageList.add(tempMessage);
		}
	}
	private int executionNum = 0;
	private CExecution addExecution(CLifeline lifeline, CEnd start, CEnd finish) {
		CExecution tempExecution = ecModelFactory.createCExecution();
		tempExecution.setName("a" + executionNum);
		executionNum++;
		tempExecution.setStart(start);
		tempExecution.setFinish(finish);
		executionMap.putIfAbsent(lifeline, new ArrayList<>());
		executionMap.get(lifeline).add(tempExecution);
		return tempExecution;
	}
	


	
	

}
