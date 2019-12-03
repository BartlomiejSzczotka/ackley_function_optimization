import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individual {
    public static final double A = 20;
    public static final double B = 0.2;
    public static final double C = 2 * Math.PI;
    double minSigma = 0.003;

    int number;
    List<AckleyX> representation;
    double value = 0;
    double overallTau;
    double cordinateTau;
    double fitness = 0;

    Individual(int number, double minSigma){
        this.minSigma = minSigma;
        representation = new ArrayList<>();
        this.number = number;
        overallTau = 1 / Math.pow(2 * number, 0.5);
        cordinateTau = 1 / Math.pow(2 * Math.pow(number, 0.5), 0.5);

        Random random = new Random();
        for(int i=0; this.number>i; i++){
            int zv = random.nextFloat() < 0.5 ? 1 : -1;
            int zs = random.nextFloat() < 0.5 ? 1 : -1;
            representation.add(
                new AckleyX(zv * 32 * random.nextDouble(), zs * 10 * random.nextDouble())
            );
        }
    }

    Individual(Individual src){
        number = src.number;
        minSigma = src.minSigma;
        overallTau = src.overallTau;
        cordinateTau = src.cordinateTau;
        representation = new ArrayList<>();
        value = src.value;
        fitness = src.fitness;
        for(int i=0; src.representation.size()>i; i++){
            representation.add(
                    new AckleyX(src.representation.get(i).value, src.representation.get(i).step));
        }
    }

    public static Individual mutation(Individual src){
        Individual child = new Individual(src);
        for(int i=0; child.representation.size()>i; i++) {
            child.representation.get(i).step = child.representation.get(i).step * Math.exp(
                    UsefulUtils.getGaussian(0, src.overallTau) +
                    UsefulUtils.getGaussian(0, src.cordinateTau)
            );
            if(child.representation.get(i).step < child.minSigma)
                child.representation.get(i).step = child.minSigma;
            child.representation.get(i).value = child.representation.get(i).value +
                    UsefulUtils.getGaussian(0, child.representation.get(i).step);
        }
        return child;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);

        for(int i=0; representation.size()>i; i++){
            if(i != 0) stringBuilder.append(", ");
            stringBuilder.append(df.format(representation.get(i).value));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static double getValue(List<AckleyX> list, int n){
        double leftSigma = 0;
        double rightSigma = 0;

        for(int i=0; n>i; i++){
            leftSigma += (Math.pow(list.get(i).value, 2));
            rightSigma += (Math.cos(C * list.get(i).value));
        }

        double left = -1 * A * Math.exp(-1 * B * Math.sqrt(leftSigma / (double)n));
        double right = -1 * Math.exp(rightSigma / (double)n);

        return left + right + A + Math.exp(1);
    }
}