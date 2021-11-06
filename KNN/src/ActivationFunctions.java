public class ActivationFunctions {

    public static double identity(double x) {
	return x;
    }

    public static double dIdentity(double x) {
	return 1;
    }

    /**
     * constant gradient = 0.01
     */
    public static double elu(double x) {
	return x > 0 ? x : 0.01 * (Math.exp(x) - 1);
    }

    /**
     * constant gradient = 0.01
     */
    public static double dElu(double x) {
	if (x == 0) {
	    throw new RuntimeException("dElu is not defined for 0");
	}
	return x > 0 ? 1 : 0.01 * Math.exp(x);
    }

    public static double relu(double x) {
	return x > 0 ? x : 0;
    }

    public static double dRelu(double x) {
	if (x == 0) {
	    throw new RuntimeException("dRelu is not defined for 0");
	}
	return x > 0 ? 1 : 0;
    }

    /**
     * constant gradient = 0.01
     */
    public static double leakyRelu(double x) {
	if (x == 0) {
	    throw new RuntimeException("dRelu is not defined for 0");
	}
	return x > (0.01 * x) ? x : (0.01 * x);
    }

    /**
     * constant gradient = 0.01
     */
    public static double dLeakyRelu(double x) {
	if (x == 0) {
	    throw new RuntimeException("dLeakyRelu is not defined for 0");
	}
	return x > 0 ? 1 : 0.01;
    }

    public static double sigmoid(double x) {
	return 1.0 / (1 + Math.exp(-x));
    }

    public static double dSigmoid(double x) {
	return sigmoid(x) * (1 - sigmoid(x));
    }

    public static double tanh(double x) {
	return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
    }

    public static double dTanh(double x) {
	return 1 - Math.pow(tanh(x), 2);
    }
}
