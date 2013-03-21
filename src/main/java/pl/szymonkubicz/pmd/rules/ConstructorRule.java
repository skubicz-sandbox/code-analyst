package pl.szymonkubicz.pmd.rules;

import java.util.List;

import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

/**
 * 
 * @author Szymon Kubicz
 *
 */
public class ConstructorRule extends AbstractJavaRule {
	public Object visit(ASTClassOrInterfaceDeclaration type, Object ruleCtx) {
		super.visit(type, ruleCtx);
		try {
			//Sprawdzamy czy klasa nalezy do interesujacego nas pakietu
			if(type.getScope().getEnclosingSourceFileScope().getPackageName().startsWith("pl.szymonkubicz.pmd.examples.construcor") ) {
				//Pobieramy liste interfejsow ktore implementuje analizowana klasa.
				List interfaces = type.findChildNodesWithXPath("//ImplementsList//ClassOrInterfaceType");
				if(interfaces.size() == 0) {
					return ruleCtx;
				}
				//Sprawdzamy czy klasa posiada zdefiniowany konstrukotr
				if("WithoutConstructor".equals( ((ASTClassOrInterfaceType)interfaces.get(0) ).getImage())) {
					if(type.findChildNodesWithXPath("//ClassOrInterfaceBody//ClassOrInterfaceBodyDeclaration//ConstructorDeclaration").size() > 0) {
						((RuleContext)ruleCtx).setReport(new Report());
						addViolation(ruleCtx, type);
					}
				}
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

		return ruleCtx;
	}  
}
