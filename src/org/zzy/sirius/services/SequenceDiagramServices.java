package org.zzy.sirius.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.eef.properties.ui.api.EEFTabbedPropertySheetPage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.query.EObjectQuery;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DEdgeSpec;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DNodeContainerSpec;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DNodeSpec;
import org.eclipse.sirius.ecore.extender.business.api.accessor.ModelAccessor;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DView;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import org.eclipse.sirius.viewpoint.provider.SiriusEditPlugin;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.emf.edit.EditingDomainServices;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIManager;
import org.eclipse.sirius.ui.business.api.session.IEditingSession;
import org.eclipse.sirius.ui.business.api.session.SessionUIManager;
import org.eclipse.sirius.diagram.sequence.business.internal.metamodel.*;
import org.eclipse.sirius.diagram.sequence.business.internal.metamodel.ordering.CompoundEventEndSpec;
import org.eclipse.sirius.diagram.sequence.business.internal.metamodel.ordering.SingleEventEndSpec;

import com.google.common.collect.Lists;
import org.xtext.example.edgecloudmodel.eCModel.*;

public class SequenceDiagramServices {
	/**
	 * Returns the event end which represents the finishing of an operand. An
	 * operand only has a starting end in the model. Its finishing end must be
	 * inferred from the context. If the operand is the last operand in the Combined
	 * Fragment, it finishes with the end of the CF. Otherwise it finished when the
	 * next operand starts.
	 * 
	 * @param operand the operand.
	 * @return the event end which represents the finishing of the operand.
	 */ 
//    public AbstractEnd getFinishingEnd(Operand operand) {
//        AbstractEnd result = null;
//        EObject eContainer = operand.eContainer();
//
//        if (eContainer instanceof CombinedFragment) {
//            CombinedFragment cf = (CombinedFragment) eContainer;
//            result = cf.getFinish();
//
//            Operand prev = null;
//            for (Operand op : cf.getOwnedOperands()) {
//                if (operand.getName().equals(prev.getName())) {
//                    result = op.getStart();
//                    break;
//                } else {
//                    prev = op;
//                }
//            }
//        }
//        
//        System.out.println("end is" + result.getName());
//        return result;
//    }

	public AbstractEnd getEnd(Operand operand) {
		AbstractEnd result = null;
		EObject eContainer = operand.eContainer();

		if (eContainer instanceof CombinedFragment) {
			CombinedFragment cf = (CombinedFragment) eContainer;
			// result = cf.getFinish();

//            Operand prev = null;
//            for (Operand op : cf.getOwnedOperands()) {
//                if (operand.equals(prev)) {
//                    result = op.getStart();
//                    break;
//                } else {
//                    prev = op;
//                }
//            }
		}

		System.out.println("end is" + result.getName());
		return result;
	}

//    public EObject getOperandEnd(EObject self) {
//    	
//    	
//    	if (self instanceof Operand)
//    	{
//    		Operand currentop = (Operand) self;
//        	System.out.println("current op:" + currentop.getName());
//    		EObject container = self.eContainer();
//    		if (container instanceof CombinedFragment)
//    		{
//    			CombinedFragment cf = (CombinedFragment) container;
//    	    	System.out.println("current container:" + cf.getName());
//
//    	    	EList<Operand> ops = cf.getOwnedOperands();
//    	    	Iterator<Operand> it = ops.iterator();
//    	    	int size = ops.size();
//				CombinedFragmentEnd end = cf.getFinish();
//				System.out.println("size is " + size);
//
//				
//    			if (size <= 1)
//    			{
//    				return end;
//            		//return REMODELFactory.eINSTANCE.createAbstractEnd();
//
//    			}
//    			else
//    			{
//    				
//    				while (it.hasNext())
//    				{
//    					Operand op = it.next();
//    					if (op.equals(currentop))
//    					{
//    						System.out.println("found it");
//    						if (!it.hasNext())
//    						{
//    							System.out.println("last one");
//    		    				return REMODELFactory.eINSTANCE.createAbstractEnd();
//    		    				
//    						//	return end;
//    						}
//    						else
//    						{
//    							System.out.println("not last one");
//    		    			//	return REMODELFactory.eINSTANCE.createAbstractEnd();
//
//    							return it.next().getStart();
//    						}
//    					}
//    				} 
//            		return REMODELFactory.eINSTANCE.createAbstractEnd();
//    			}
//    		}
//    		else 
//    		{
//    			System.out.println("container is not Operand");
//        		return REMODELFactory.eINSTANCE.createAbstractEnd();
//    		}
//    	}
//    	else
//    	{
//    
//    		System.out.println("it is not Operand");
//    		return REMODELFactory.eINSTANCE.createAbstractEnd();
//    	}
//    	
//    	
//    }

//	public Collection<EObject> getAllContainedOpeartion(EObject self) {
//
//		if (self instanceof Service) {
//			// System.out.println(self);
//
//			Service s = ((Service) self);
//
//			Workflow swf = null;
//			EList<Workflow> workflows = s.getWorkflow();
//			for (Workflow wf : workflows) {
//				if (wf.getName().equals("ProcessSaleWF")) {
//					swf = wf;
//				}
//			}
//			return swf.eContents();
//		} else {
//			return null;
//		}
//
//	}
//
//	public String computeCombinedLabel(EObject self) {
//		if (self instanceof SwitchExp) {
//			return "Alt: " + ((SwitchExp) self).getName();
//		} else {
//			if (self instanceof LoopExp) {
//				return "Loop: " + ((LoopExp) self).getName();
//			} else {
//				return "";
//			}
//		}
//	}
//
//	public Collection<EObject> getAllSwitchExp(EObject self) {
//
//		Collection<EObject> result = Lists.newArrayList();
//
//		if (self instanceof SwitchExp) {
//			result.add(self);
//			System.out.println("add: " + self);
//		}
//
//		return result;
//
//	}

	public EObject getInteraction(EObject self) {

		IEditorPart currenteditorpart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();

		System.out.println("currenteditorpart: " + currenteditorpart.getTitle());

		if (self instanceof DNodeSpec) {
			DNodeSpec node = (DNodeSpec) self;

			for (EObject o : node.getTarget().eContainer().eContents()) {

				System.out.println("Contents: " + o.toString());

				if (o instanceof Interaction) {
					Interaction current = ((Interaction) o);

					System.out.println("Interaction: " + current.getName());

					if (current.getName().equals(currenteditorpart.getTitle())) {
						System.out.println("find it : " + current.getName());
						return current;
					}
				}

			}

		} else {

			for (EObject o : self.eContainer().eContents()) {

				System.out.println("Contents: " + o.toString());

				if (o instanceof Interaction) {
					Interaction current = ((Interaction) o);

					System.out.println("Interaction: " + current.getName());

					if (current.getName().equals(currenteditorpart.getTitle())) {
						System.out.println("find it : " + current.getName());
						return current;
					}
				}

			}
		}

		return self;
//    	Session session = SessionManager.INSTANCE.getSession(self);
//    	
////    	TreeIterator<EObject> ti = session.getSessionResource().getAllContents();
////    	while (ti.hasNext())
////    	{
////    	//	System.out.println(ti.next());
////    	}
//    	
//    	ModelAccessor ma = session.getModelAccessor();
//    	
//    	
//    	TreeIterator<EObject> all = ma.eAllContents(self.eContainer());
//    	
//    	
//    	
//    	
//    	Collection<EObject> r = new EObjectQuery(self).getInverseReferences(ViewpointPackage.Literals.DSEMANTIC_DECORATOR__TARGET);
//    	System.out.println("r size:" + r.size());
//    	
//    	for (EObject o : r)
//    	{
//    		System.out.println(o.toString());
//    	}
//    	
//    	
//    	TransactionalEditingDomain ted = session.getTransactionalEditingDomain();
//    	
//    	
//    	IEditingSession ieditingsession = SessionUIManager.INSTANCE.getUISession(session);
//    	
//    	
//    	Collection<DRepresentation> cds = DialectManager.INSTANCE.getAllRepresentations(session);
//  //  	Collection<DRepresentation> cds = DialectManager.INSTANCE.getRepresentations(self, session);
//    	System.out.println("collection size:" + cds.size());
//
//    	for (DRepresentation d : cds)
//    	{
//    		System.out.println("dname is: " + DialectUIManager.INSTANCE.getEditorName(d));
//    		
//        	
//
//    	}
//    	System.out.println("return self");
//    	
//    	EditingDomainServices s = new EditingDomainServices();
//    	
//    	
//    	

//        return self;
	}

	public EObject testCan(EObject self) {
		System.out.println("dddtestmoreself:" + self);
		System.out.println("sdddtestmoreContainer:" + self.eContainer());
		System.out.println("dddtestmoreAllContents:" + self.eAllContents());

		return self;
	}

	public boolean precondition(EObject self) {
		System.out.println("testmoreself:" + self);
//        System.out.println("testmoreContainer:" + self.eContainer());
//        System.out.println("testmoreAllContents:" + self.eAllContents());

		return true;
	}

	public String getLabelADCaseToAction(EObject self, EObject view) {

		if (view instanceof DEdgeSpec) {
			DEdgeSpec v = (DEdgeSpec) view;
			System.out.println("target node:" + v.getTargetNode());
			System.out.println(v.getTargetNode().getClass());
			DNodeContainerSpec dc = (DNodeContainerSpec) v.getTargetNode();
			EObject ob = dc.basicGetTarget().eContainer();
			System.out.println("EClass: " + ob.getClass());

			String label = (String) ob.eGet(ob.eClass().getEStructuralFeature("caseValue"));
			return label;
		} else {

		}

		return "default";

	}

	public EObject testMore(EObject self, EObject container) {

		System.out.println("self:" + self);
		System.out.println("selfContainer:" + self.eContainer());
		System.out.println("selfAllContents:" + self.eAllContents());

		System.out.println("testmoreself:" + container);

		EObjectQuery query = new EObjectQuery(self);
		Session session = query.getSession();
		// org.eclipse.sirius.ui.business.api.session.SessionUIManager.INSTANCE.getUISession(session)

		// List<DRepresentation> s = (List)
		// org.eclipse.sirius.business.api.dialect.DialectManager.INSTANCE.getRepresentations(self,
		// session);
		// System.out.println(s.get(0).getName());

		System.out.println("containerClass:" + container.eClass());
		System.out.println("containerContainer:" + container.eContainer());
		System.out.println("containerAllContents:" + container.eAllContents());

		return container;

	}

	public EList<Execution> getInteractionExecutions(EObject self, EObject object) {

		EList<Execution> finalexecs = new org.eclipse.emf.common.util.BasicEList();

		System.out.println("self:" + self);

		if (object instanceof SequenceDDiagramSpec) {

			SequenceDDiagramSpec diagram = (SequenceDDiagramSpec) object;
			Interaction inter = (Interaction) diagram.getTarget();
			finalexecs = inter.getExecutions();
		}

		System.out.println("selfContainer:" + self.eContainer());
		System.out.println("selfAllContents:" + self.eAllContents());

		return finalexecs;

	}

	public EObject refreshProperty(EObject self) {
		System.out.println("testmoreself:" + self);
		System.out.println("testmoreContainer:" + self.eContainer());
		System.out.println("testmoreAllContents:" + self.eAllContents());

		String id = "org.eclipse.ui.views.PropertySheet";

		IViewPart view = SiriusEditPlugin.getPlugin().getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.findView(id);
		if (view instanceof PropertySheet) {

			PropertySheet propertySheet = (PropertySheet) view;
			IPage currentPage = propertySheet.getCurrentPage();
			currentPage.dispose();
			if (currentPage instanceof EEFTabbedPropertySheetPage) {
				EEFTabbedPropertySheetPage propertySheetPage = (EEFTabbedPropertySheetPage) currentPage;
				propertySheetPage.refreshPage();

			}

		}

		return self;
	}

	public List<AbstractEnd> reOrderEndForCallMessage(EObject self, Execution targetExe, MessageEnd sendEnd) {

		if (self instanceof Interaction) {
			List<AbstractEnd> allends = ((Interaction) self).getEnds();
			System.out.println("delete sendEnd" + allends.remove(sendEnd));

			int targetExeIndex = allends.indexOf(targetExe.getStart());
			allends.add(targetExeIndex, sendEnd);

			return allends;
		} else {
			return null;
		}

	}

	public List<AbstractEnd> reOrderEndForCallMessage(EObject self, Execution targetExe, MessageEnd sendEnd,
			SingleEventEndSpec start, SingleEventEndSpec end) {

		System.out.println("Invoke reOrderEndForCallMessage");
		System.out.println("create staring end is: " + start);
		System.out.println("create finish end is: " + end);

		if (self instanceof Interaction) {
			List<AbstractEnd> allends = ((Interaction) self).getEnds();
			System.out.println("delete sendEnd" + allends.remove(sendEnd));

			int targetExeIndex = allends.indexOf(targetExe.getStart());
			allends.add(targetExeIndex, sendEnd);

			return allends;
		} else {
			return null;
		}

	}

	public EList<AbstractEnd> reOrderEndForCreateCombinedFragment(EObject self, EObject start,
			EObject end, CombinedFragmentEnd cfend, OperandEnd opend, CombinedFragmentEnd cffinishend) {

//		public EList<AbstractEnd> reOrderEndForCreateCombinedFragment(EObject self, SingleEventEndSpec start,
//				SingleEventEndSpec end, CombinedFragmentEnd cfend, OperandEnd opend, CombinedFragmentEnd cffinishend) {

		
//		System.out.println("Invoke reOrderEndForCreateCombinedFragment");
//		System.out.println("create staring end is: " + start);
//		System.out.println("create finish end is: " + end);
//
//		MessageEnd startend = (start != null && start.getSemanticEnd() instanceof MessageEnd)
//				? (MessageEnd) start.getSemanticEnd()
//				: null;
//		MessageEnd finishend = (end != null && end.getSemanticEnd() instanceof MessageEnd)
//				? (MessageEnd) end.getSemanticEnd()
//				: null;

		
		
//		MessageEnd startend = (start != null && start instanceof SingleEventEndSpec)
//				? (MessageEnd) ((SingleEventEndSpec) start).getSemanticEnd()
//				: null;
//		
//		MessageEnd finishend = (end != null && end instanceof SingleEventEndSpec)
//				? (MessageEnd) ((SingleEventEndSpec) end).getSemanticEnd()
//				: null;
		
		AbstractEnd startend = null;
		AbstractEnd finishend = null;
		startend = (start != null)
				? ((start instanceof SingleEventEndSpec) ? (AbstractEnd) ((SingleEventEndSpec) start).getSemanticEnd() : (AbstractEnd) ((CompoundEventEndSpec) start).getSemanticEnd()) 
				: null;
		
		finishend = (end != null)
				? ((end instanceof SingleEventEndSpec) ? (AbstractEnd) ((SingleEventEndSpec) end).getSemanticEnd() : (AbstractEnd) ((CompoundEventEndSpec) end).getSemanticEnd())
				: null;

		
		System.out.println("staring end is: " + startend);
		System.out.println("finish end is: " + finishend);

		System.out.println("CombinedFragmentEnd: " + cfend);
		System.out.println("OperandEnd: " + opend);
		System.out.println("CombinedFragmentFinishEnd: " + cffinishend);

		if (self instanceof Interaction) {

			EList<AbstractEnd> allends = ((Interaction) self).getEnds();
			System.out.println("end size is:" + allends.size());

			// create ssd in front
			if (startend == null & finishend == null) {

				// empty ssd
				if (allends.size() <= 3) {
					// empty ssd
					System.out.println("create combinedfragment in an empty ssd");
					return allends;
				}
//				else {
//					// not empty ssd
//					System.out.println("create combinedfragment in the front of ssd");
//					allends.add(0, cffinishend);
//					allends.add(0, opend);
//					allends.add(0, cfend);
//					return allends;
//				}

			}

			System.out.println("delete CFstartEnd " + allends.remove(cfend));
			System.out.println("delete OperandEnd " + allends.remove(opend));

			int targetExeIndex = allends.indexOf(startend);
			System.out.println("Target start Index: " + targetExeIndex);
			allends.add(targetExeIndex + 1, cfend);
			allends.add(targetExeIndex + 2, opend);

			System.out.println("delete CFfinished " + allends.remove(cffinishend));
			int targetFinishIndex = allends.indexOf(finishend);
			System.out.println("Target finish Index: " + targetFinishIndex);

			if (targetFinishIndex == -1) // create ssd in the front
				allends.add(2, cffinishend);
			else if (targetExeIndex == targetFinishIndex) // create ssd in the middle and end
				allends.add(targetExeIndex + 3, cffinishend);
			else // cover message
				allends.add(targetFinishIndex + 1, cffinishend);

			
			System.out.println(allends);
			
			return allends;
		} else {
			return null;
		}
//		return null;

	}

	public List<AbstractEnd> reOrderEndForSSD(EObject self, AbstractEnd targetExe, AbstractEnd sendEnd) {

		if (self instanceof Interaction) {
			List<AbstractEnd> allends = ((Interaction) self).getEnds();
			System.out.println("delete sendEnd" + allends.remove(sendEnd));

			int targetExeIndex = allends.indexOf(targetExe);
			allends.add(targetExeIndex, sendEnd);

			return allends;
		} else {
			return null;
		}

	}

	private List<Execution> execs;
	private String interactionName;

	public List<AbstractEnd> getEndAndComputeExecutions(EObject self) {

		if (self instanceof Interaction) {
			Interaction inter = (Interaction) self;
			List<AbstractEnd> allends = inter.getEnds();
			execs = inter.getExecutions();
			interactionName = inter.getName();
			System.out.println("Interaction " + interactionName + "executions computed");
			return allends;
		} else {
			return null;
		}

	}

	public List<Execution> getExecutionCandiate(EObject self) {

		List<Execution> finals = new LinkedList<Execution>();

		if (execs != null && self instanceof Service) {
			for (Execution e : execs) {

				Service s = (Service) self;

				System.out.println(e.getOwner().getName());

				System.out.println("e container name:" + e.eContainer());

				if (e.getOwner().getName().equals(s.getName())) {
					System.out.println(e.getName() + " added");
					finals.add(e);
				}

			}
		}

		return finals;

//      	IEditorPart currenteditorpart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor(); 	
//      	System.out.println("currenteditorpart: " + currenteditorpart.getTitle());
//    	
//    	
//      	System.out.println("-----------------------");
//    	
//      	
//      	
//    		System.out.println("self:" + self);
//           System.out.println("selfContainer:" + self.eContainer());
//           System.out.println("selfAllContents:" + self.eAllContents());
//    	
//	    	Collection<EObject> r = new EObjectQuery(self).getInverseReferences(ViewpointPackage.Literals.DSEMANTIC_DECORATOR__TARGET);
////	    	System.out.println("r size:" + r.size());
//	    	
//	    	for (EObject o : r)
//	    	{
////	    		System.out.println(o.toString());
//	    	  	if (o instanceof DNodeSpec)
//	        	{
//	        		DNodeSpec node = (DNodeSpec) o;
//	        		System.out.println(node.getParentDiagram().getName());
//	        		break;
//	        	}
//	    	}

//	    	System.out.println("-----------------------");

	}

}
