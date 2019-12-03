public class Main {
    private static ESConfigs esConfigs = ESConfigs.getInstance();
    private static AckleyProblem ackleyProblem;

    public static void main(String[] args) {
        while (mainMenu());
    }

    public static boolean mainMenu(){
        StylishPrinter.println("\nMenu:", StylishPrinter.BOLD_RED);
        System.out.println("1: ES Algorithm Settings");
        System.out.println("2: ES Solve");
        System.out.println("3: Exit");
        System.out.print("\nEnter Your Choice: ");
        int choice = SBProScanner.inputInt(1, 3);

        if(choice==1) while (esConfigs.menu());
        else if(choice == 2) esSolve();
        else if(choice == 3) return false;
        return true;
    }

    private static void esSolve() {
        ackleyProblem = new AckleyProblem();
        ackleyProblem.solve();
    }
}