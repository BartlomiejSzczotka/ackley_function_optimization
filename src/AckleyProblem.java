import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AckleyProblem {
    private ESConfigs esConfigs = ESConfigs.getInstance();
    private int generationNumber = 0;
    List<Individual> population;
    List<Individual> children;
    Individual globalMinIndividual;
    double minChildValue = Double.MAX_VALUE;
    double maxChildValue = Double.MIN_VALUE;

    public void solve() {
        generatePopulation();
        while(esConfigs.maxGenerationNumber > generationNumber){
            generateChildren();
            preparingCompetitors();
            chooseSurvivors();
            if((generationNumber % 200) == 0)
                System.out.println("Generation " + generationNumber + " Done!");
        }

        //sortPopulation(IndividualsComparator.SORT_BY_FITNESS);
        //printPopulation();
        printFinalOptimumIndividual();
    }

    public void generatePopulation(){
        generationNumber = 0;
        population = new ArrayList<>();
        globalMinIndividual = null;

        for(int i=0; esConfigs.populationSize>i; i++){
            Individual individual = new Individual(esConfigs.numDim, esConfigs.minSigma);
            individual.value = Individual.getValue(individual.representation, esConfigs.numDim);
            if(globalMinIndividual == null || globalMinIndividual.value > individual.value)
                setNewMinimumIndividual(individual);
            population.add(individual);
        }
    }

    public void generateChildren(){
        children = new ArrayList<>();
        minChildValue = Double.MAX_VALUE;
        maxChildValue = Double.MIN_VALUE;
        Random random = new Random();
        for(int i=0; esConfigs.childrenSize>i; i++){
            int randIndex = random.nextInt(population.size());
            Individual individual = Individual.mutation(population.get(randIndex));
            individual.value = Individual.getValue(individual.representation, esConfigs.numDim);
            if(globalMinIndividual == null || globalMinIndividual.value > individual.value)
                setNewMinimumIndividual(individual);
            if(individual.value < minChildValue) minChildValue = individual.value;
            if(individual.value > maxChildValue) maxChildValue = individual.value;
            children.add(individual);
        }
    }

    public void setNewMinimumIndividual(Individual individual){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(6);
        globalMinIndividual = individual;

        System.out.println(
            "GN: " + generationNumber +
                " | " + globalMinIndividual.toString()
                + ", " + df.format(globalMinIndividual.value)
        );
    }

    public void printFinalOptimumIndividual(){
        System.out.println();
        System.out.println("Final Minimum Individual: ");
        System.out.println(globalMinIndividual.toString());
        System.out.println("Value: " + globalMinIndividual.value);
        System.out.println("Fitness: " + globalMinIndividual.fitness);
        System.out.println();
    }

    public void preparingCompetitors(){
        double baseValue = (minChildValue > 0 ? -1 : 1) * minChildValue * 0.999;

        double sumFitness = 0;
        for(int i=0; children.size()>i; i++){
            sumFitness += (1 / (children.get(i).value + baseValue));
        }
        for(int i=0; children.size()>i; i++){
            children.get(i).fitness = (1 / (children.get(i).value + baseValue)) / sumFitness;
        }
    }

    public void chooseSurvivors(){
        Random random = new Random();
        population = new ArrayList<>();

        while(esConfigs.populationSize > population.size()){
            double randDouble = random.nextDouble();
            double fitPointer = 0;

            for(int i=0; children.size()>i; i++){
                fitPointer += children.get(i).fitness;
                if(fitPointer > randDouble){
                    population.add(new Individual(children.get(i)));
                    break;
                }
            }
        }

        children = null;
        generationNumber++;
    }
}
