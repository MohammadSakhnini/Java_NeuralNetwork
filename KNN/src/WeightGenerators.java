public class WeightGenerators {
    public static void random(double[][] weights) {
	for (int i = 0; i < weights.length; i++) {
	    for (int j = 0; j < weights[0].length; j++) {
		weights[i][j] = getRandomBetween(-1, 1);
	    }
	}
    }

    public static void he(double[][] weights) {
	int n = weights.length;
	double min = -(2 / Math.sqrt(n));
	double max = (2 / Math.sqrt(n));
	for (int i = 0; i < weights.length; i++) {
	    for (int j = 0; j < weights[0].length; j++) {
		weights[i][j] = getRandomBetween(min, max);
	    }

	}
    }

    public static void xavier(double[][] weights) {
	int n = weights.length;
	double min = -(1 / Math.sqrt(n));
	double max = (1 / Math.sqrt(n));
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < weights[0].length; j++) {
		weights[i][j] = getRandomBetween(min, max);
	    }
	}
    }

    public static void zeros(double[][] weights) {
	for (int i = 0; i < weights.length; i++) {
	    for (int j = 0; j < weights[0].length; j++) {
		weights[i][j] = 0;
	    }
	}
    }

    private static double getRandomBetween(double min, double max) {
	return ((Math.random() * (max - min)) + min);
    }
}
