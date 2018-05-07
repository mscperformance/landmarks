import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.planners.hsp.HSP;
import fr.uga.pddl4j.util.BitOp;
import fr.uga.pddl4j.util.BitState;
import fr.uga.pddl4j.util.Plan;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Landmarks {
    public static void main(String[] args) {

        // Get the domain and the problem paths from the command line
//        File domain = new File(args[0]); //String domain = args[0]
//        File problem = new File(args[1]); //String problem = args[1]
        File domain = new File("domain.pddl");
        File problem = new File("p01.pddl");

        // Create the problem factory
        final ProblemFactory factory = new ProblemFactory();

        // Parse the domain and the problem
        try {
            ErrorManager errorManager = factory.parse(domain, problem);
            if (!errorManager.isEmpty()) {
                errorManager.printAll();
                System.exit(0);
            }

            // Encode and simplify the planning problem in a compact representation
            final CodedProblem pb = factory.encode();

            System.out.println("Constants " + pb.getConstants());
            System.out.println( pb.toString(pb.getInit()));

            BitState init = new BitState(pb.getInit());
            System.out.println("Init "  + pb.toString(pb.getInit()));
            System.out.println("Goal " + pb.toString(pb.getGoal()));


            System.out.println("Predicates " + pb.getPredicates());
            System.out.println(pb.getPredicatesSignatures());

            System.out.println("Operators " + pb.getOperators());

            if (!pb.isSolvable()) {
                System.out.println("goal can be simplified to FALSE. "
                        + "no search will solve it");
                System.exit(0);
            }

            //Find the landmarks
            final RPGL planner= new RPGL();

            // Create the planner
//            final HSP planner = new HSP();
            //final FF planner = new FF();
            //final EHC planner = new EHC();

            // Search for a solution plan
            final Plan plan = planner.search(pb);
            if (plan != null) {
                System.out.println("found plan as follows:");
                System.out.println(pb.toString(plan));
            } else {
                System.out.println("no plan found");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
