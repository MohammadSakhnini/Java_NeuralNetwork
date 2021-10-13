import java.util.function.Function;

public abstract class Layer {
    private double neurons[][];
    private double weights[][];
    private double bias[];
    private double output[][];
    private WeightGenerator generator;
    private ActivationFunction activation;

    public Layer(double neurons[][], double bias[], WeightGenerator generator, ActivationFunction activation) {
	this(neurons, generator, activation);
	this.bias = new double[neurons.length];
	for (int i = 0; i < bias.length; i++) {
	    this.bias[i] = bias[i];
	}
	setup();
    }

    public Layer(double neurons[][], WeightGenerator generator, ActivationFunction activation) {
	this.neurons = neurons;
	weights = new double[neurons.length][neurons[0].length];
	this.generator = generator;
	this.activation = activation;
	setup();
    }

    /**
     * @implNote the inputs of the layer must be equal to the output neurons of the
     *           previous layer
     * 
     * @param inputs     Number of inputs in the layer
     * @param neurons    Number of neurons per hidden layer
     * @param generator  The weight generating method
     * @param activation The applied Activation function on this specific Layer
     */
    public Layer(int inputs, int neurons, WeightGenerator generator, ActivationFunction activation) {
	this.neurons = new double[neurons][0];
	weights = new double[inputs][neurons];
	this.generator = generator;
	this.activation = activation;
	setup();
    }

    public Layer(int inputs, int neurons, double bias[], WeightGenerator generator, ActivationFunction activation) {
	this(inputs, neurons, generator, activation);
	if (bias.length != neurons) {
	    throw new RuntimeException("neurons and bias.length are not the same size");
	}
	for (int i = 0; i < bias.length; i++) {
	    this.bias[i] = bias[i];
	}
    }

    private void setup() {
	if (generator == null) {
	    return;
	}
	switch (generator) {
	case Random:
	    random_weights_Init();
	    break;
	case He:
	    he_Weights_Init();
	    break;
	case Xavier:
	    xavier_Weights_Init();
	default:
	    break;
	}
    }

    private void random_weights_Init() {
	for (int i = 0; i < weights.length; i++) {
	    for (int j = 0; j < weights[0].length; j++) {
		weights[i][j] = getRandomBetween(-1, 1);
	    }
	}
    }

    private void he_Weights_Init() {
	int n = weights.length;
	double min = -(2 / Math.sqrt(n));
	double max = (2 / Math.sqrt(n));
	for (int i = 0; i < weights.length; i++) {
	    for (int j = 0; j < weights[0].length; j++) {
		weights[i][j] = getRandomBetween(min, max);
	    }

	}
    }

    private void xavier_Weights_Init() {
	int n = weights.length;
	double min = -(1 / Math.sqrt(n));
	double max = (1 / Math.sqrt(n));
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < weights[0].length; j++) {
		weights[i][j] = getRandomBetween(min, max);
	    }
	}
    }

    private double getRandomBetween(double min, double max) {
	return ((Math.random() * (max - min)) + min);
    }

    public void activationFunction() {
	if (activation == null) {
	    return;
	}
	Function<Double, Double> function = null;
	switch (activation) {
	case Relu:
	    function = ActivationFunctions::relu;
	    break;
	case LeakyRelu:
	    function = ActivationFunctions::leakyRelu;
	    break;
	case Sigmoid:
	    function = ActivationFunctions::sigmoid;
	    break;
	case Tanh:
	    function = ActivationFunctions::tanh;
	    break;
	}
	for (int i = 0; i < output.length; i++) {
	    for (int j = 0; j < output[0].length; j++) {
		output[i][j] = function.apply(output[i][j]);
	    }

	}
    }

    public double[][] dActivationFunction(double[][] arr) {
	Function<double[][], double[][]> function = null;
	if (activation == null) {
	    return null;
	}
	switch (activation) {
	case Relu:
	    function = ActivationFunctions::dRelu;
	    break;
	case LeakyRelu:
	    function = ActivationFunctions::dLeakyRelu;
	    break;
	case Sigmoid:
	    function = ActivationFunctions::dSigmoid;
	    break;
	case Tanh:
	    function = ActivationFunctions::dTanh;
	    break;
	}
	return function.apply(arr);
    }

    public double[][] getNeurons() {
	return neurons;
    }

    public double[][] getWeights() {
	return weights;
    }

    public double[] getBias() {
	return bias;
    }

    public int getNeuronsCount() {
	return neurons.length;
    }

    public double[][] getOutput() {
	return output;
    }

    public void setOutput(double[][] output) {
	this.output = output;
    }

    public void setNeurons(double[][] neurons) {
	this.neurons = neurons;
    }

}
