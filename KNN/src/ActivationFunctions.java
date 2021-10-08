public class ActivationFunctions {

    public static double relu(double x) {
	return x > 0 ? x : 0;
    }

    public static double[][] dRelu(double[][] arr) {
	double[][] results = new double[arr.length][arr[0].length];
	for (int i = 0; i < arr.length; i++) {
	    for (int j = 0; j < arr[0].length; j++) {
		results[i][j] = arr[i][j] * (arr[i][j] > 0 ? 1 : 0);
	    }
	}
	return results;
    }

    /**
     * constant gradient = 0.01
     */
    public static double leakyRelu(double x) {
	return x > (0.01 * x) ? x : (0.01 * x);
    }

    public static double[][] dLeakyRelu(double[][] arr) {
	double[][] results = new double[arr.length][arr[0].length];
	for (int i = 0; i < arr.length; i++) {
	    for (int j = 0; j < arr[0].length; j++) {
		results[i][j] = arr[i][j] * (arr[i][j] > 0 ? 1 : 0.01);
	    }
	}
	return results;
    }

    public static double sigmoid(double x) {
	return 1.0 / (1 + Math.exp(-x));
    }

    public static double[][] dSigmoid(double[][] arr) {
	double[][] results = new double[arr.length][arr[0].length];
	for (int i = 0; i < arr.length; i++) {
	    for (int j = 0; j < arr[0].length; j++) {
		results[i][j] = arr[i][j] * (1 - sigmoid(arr[i][j]));
	    }
	}
	return results;
    }

    public static double tanh(double x) {
	return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
    }

    public static double[][] dTanh(double[][] arr) {

	double[][] results = new double[arr.length][arr[0].length];
	for (int i = 0; i < arr.length; i++) {
	    for (int j = 0; j < arr[0].length; j++) {
		results[i][j] = arr[i][j] * (1 - Math.pow(Math.tanh(arr[i][j]), 2));
	    }
	}
	return results;
    }
}
