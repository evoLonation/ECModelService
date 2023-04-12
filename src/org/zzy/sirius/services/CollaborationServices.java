package org.zzy.sirius.services;

import java.util.*;
import java.util.stream.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.xtext.example.edgecloudmodel.eCModel.*;
public class CollaborationServices {
	
	static Map<Collaboration, CollaborationState> collaborationMap = new HashMap<>();
	
	private void ifInit(Collaboration c) {
		collaborationMap.putIfAbsent(c, new CollaborationState(c));
		var state = collaborationMap.get(c);
		if(c.getMessages().isEmpty()) {
			c.getMessages().addAll(state.getAllMessage());
		}
		if(c.getEnds().isEmpty()) {
			c.getEnds().addAll(state.getEnds());
		}
		if(c.getExecutions().isEmpty()) {
			c.getExecutions().addAll(state.getExecutions());
		}
		if(c.getLifelines().isEmpty()) {
			c.getLifelines().addAll(state.getLifelines());
		}
		if(c.getFragments().isEmpty()) {
			c.getFragments().addAll(state.getFragments());
		}
	}
	
	public List<CLifeline> getLifelines(Collaboration o){
		ifInit(o);
		return collaborationMap.get(o).getLifelines();
	}
	public List<CEnd> getEnds(Collaboration o){
		ifInit(o);
		return collaborationMap.get(o).getEnds();
	}
	public List<CMessage> getMessages(Collaboration o){
		ifInit(o);
		return collaborationMap.get(o).getMessages();
	}
	public List<CMessage> getReturnMessages(Collaboration o){
		ifInit(o);
		return collaborationMap.get(o).getReturnMessages();
	}
	public List<CExecution> getExecutions(Collaboration o, CLifeline lifeline){
		ifInit(o);
		return collaborationMap.get(o).getExecutions(lifeline);
	}
	public List<CFragment> getFragments(Collaboration o){
		ifInit(o);
		return collaborationMap.get(o).getFragments();
	}
	public Boolean hello(Collaboration collaboration){
		ifInit(collaboration);
		System.out.println("hello, fucker");
		return true;
	}
	
}
