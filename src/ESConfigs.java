public class ESConfigs {
    private static ESConfigs single_instance = null;
    int numDim;
    int populationSize;
    int childrenSize;
    int maxGenerationNumber;
    double minSigma;

    private ESConfigs() {
        numDim = 20;
        populationSize = 150;
        childrenSize = 7 * populationSize;
        maxGenerationNumber = 1500;
        minSigma = 0.003;
    }

    public static ESConfigs getInstance() {
        if (single_instance == null)
            single_instance = new ESConfigs();

        return single_instance;
    }

    public boolean menu(){
        StylishPrinter.println("\nGA Configs:", StylishPrinter.BOLD_RED);
        System.out.println("1: Number of dimensions: " + numDim);
        System.out.println("2: Population size: " + populationSize);
        System.out.println("3: Children size: " + childrenSize);
        System.out.println("4: Max generation number: " + maxGenerationNumber);
        System.out.println("5: Min sigma: " + minSigma);
        System.out.println("6: Back");

        System.out.print("\nEnter your settings number: ");
        int choice = SBProScanner.inputInt(1, 6);

        if(choice == 1){
            System.out.print("\nEnter number of ackley functions dimensions: ");
            numDim = SBProScanner.inputInt(1, 10000);
        }
        else if(choice == 2){
            System.out.print("\nEnter population size: ");
            populationSize = SBProScanner.inputInt(2, 100000);
        }
        else if(choice == 3){
            System.out.print("\nEnter children size: ");
            childrenSize = SBProScanner.inputInt(2, 500000);
        }
        else if(choice == 4){
            System.out.print("\nEnter max number of generations: ");
            maxGenerationNumber = SBProScanner.inputInt(1, 200000);
        }
        else if(choice == 5){
            System.out.print("\nEnter ES minimum sigma: ");
            minSigma = SBProScanner.inputDouble(0, 100000);
        }
        else return false;
        return true;
    }
}
