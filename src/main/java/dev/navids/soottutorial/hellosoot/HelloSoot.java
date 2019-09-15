/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dev.navids.soottutorial.hellosoot;

import dev.navids.soottutorial.visual.Visualizer;
import soot.*;
import soot.jimple.JimpleBody;
import soot.jimple.internal.JIfStmt;
import soot.options.Options;
import soot.toolkits.graph.ClassicCompleteUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class HelloSoot {

    public static String sourceDirectory = System.getProperty("user.dir") + "/demo/HelloSoot/";
    public static String clsName = "FizzBuzz";
    public static String methodName = "printFizzBuzz";
    public static void setupSoot(){
        G.reset();
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_soot_classpath(sourceDirectory);
        SootClass sc = Scene.v().loadClassAndSupport(clsName);
        sc.setApplicationClass();
        Scene.v().loadNecessaryClasses();

    }

    public static void main(String[] args) {
        setupSoot();

        // Retrieve printFizzBuzz's body
        SootClass mainClass = Scene.v().getSootClass(clsName);
        SootMethod sm = mainClass.getMethodByName(methodName);
        JimpleBody body = (JimpleBody) sm.retrieveActiveBody();

        // Print some information about printFizzBuzz
        System.out.println("Method Signature: " + sm.getSignature() );
        System.out.println("--------------");
        System.out.println("Argument(s):");
        for(Local l : body.getParameterLocals()){
            System.out.println(l.getName() + " : " + l.getType());
        }
        System.out.println("--------------");
        System.out.println("This: " + body.getThisLocal());
        System.out.println("--------------");
        System.out.println("Units:");
        int c = 1;
        for(Unit u : body.getUnits()){
            System.out.println("("+c+") " +u.toString());
            c++;
        }
        System.out.println("--------------");

        // Print statements that have branch conditions
        System.out.println("Branch Statements:");
        for(Unit u : body.getUnits()){
            if (u instanceof JIfStmt)
                System.out.println(u.toString());
        }

        // Draw the control-flow graph of the method if 'draw' is provided in arguments
        boolean drawGraph = false;
        if(args.length > 0 && args[0].equals("draw"))
            drawGraph = true;
        if(drawGraph) {
            UnitGraph ug = new ClassicCompleteUnitGraph(sm.getActiveBody());
            Visualizer.v().addUnitGraph(ug);
            Visualizer.v().draw();
        }
    }
}
