import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Layer {
    private Neuron[][] neurons;
    private double weights[][];
    private double bias[][];
    private double output[][];
    private WeightGenerator generator;
    private ActivationFunction activation;

    /**
     * @implNote the inputs of the layer must be equal to the output neurons of the
     *           previous layer
     * 
     * @param inputs       Number of inputs in the layer
     * @param neuronscount Number of neurons per hidden layer
     * @param generator    The weight generating method
     * @param activation   The applied Activation function on this specific Layer
     */
    public Layer(int inputs, int neuronscount, WeightGenerator generator, ActivationFunction activation) {
	double[][] temp = new double[neuronscount][0];
	createNeurons(temp, activation);
	weights = new double[inputs][neuronscount];
	this.generator = generator;
	this.activation = activation;
	generate_weights();
    }

    public Layer(double neurons[][], int output, WeightGenerator generator) {
	createNeurons(neurons, activation);
	this.generator = generator;
	generate_weights();
    }

    public Layer(double neurons[][], int output, WeightGenerator generator, ActivationFunction activation) {
	createNeurons(neurons, activation);
	weights = new double[neurons[0].length][output];
	this.generator = generator;
	this.activation = activation;
	generate_weights();
    }

    public Layer(double neurons[][], int output, double bias[][], WeightGenerator generator,
	    ActivationFunction activation) {
	createNeurons(neurons, bias, activation);
	this.bias = new double[neurons.length][neurons[0].length];
	for (int i = 0; i < bias.length; i++) {
	    for (int j = 0; j < bias[0].length; j++) {
		this.bias[i][j] = bias[i][j];
	    }
	}
	generate_weights();
    }

    public Layer(int inputs, int neurons, double bias[][], WeightGenerator generator, ActivationFunction activation) {
	this(inputs, neurons, generator, activation);
	if (bias.length != neurons) {
	    throw new RuntimeException("neurons and bias.length are not the same size");
	}
	for (int i = 0; i < bias.length; i++) {
	    for (int j = 0; j < bias.length; j++) {
		this.bias[i][j] = bias[i][j];
	    }
	}
    }

    private void generate_weights() {
	if (generator == null) {
	    return;
	}

	Consumer<double[][]> function = null;
	switch (generator) {
	case Random:
	    function = WeightGenerators::random;
	    break;
	case He:
	    function = WeightGenerators::he;
	    break;
	case Xavier:
	    function = WeightGenerators::xavier;
	case Zeros:
	    function = WeightGenerators::zeros;
	    break;
	}
	function.accept(weights);
    }

    public void activationFunction() {
	if (activation == null) {
	    return;
	}
	Function<Double, Double> function = null;
	switch (activation) {
	case Identity:
	    function = ActivationFunctions::identity;
	    break;
	case ELU:
	    function = ActivationFunctions::elu;
	    break;
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
	for (int i = 0; i < this.neurons.length; i++) {
	    for (int j = 0; j < this.neurons[0].length; j++) {
		// if a there is an outlier neuron with a different activation funciton
		if (neurons[i][j].getActivation() != activation) {
		    ActivationFunctions s = new ActivationFunctions();
		    try {
			String name = this.neurons[i][j].getActivation().name().toLowerCase();
			var method = s.getClass().getMethod(name, double.class);
			method.invoke(s, this.neurons[i][j].getValue());
			continue;
		    } catch (Exception e) {
			System.out.println(e);
		    }
		}
		this.neurons[i][j].setValue(function.apply(this.neurons[i][j].getValue()));
	    }

	}
    }

    public double[][] dActivationFunction() {
	double[][] results = getNeuronsValues();
	Function<Double, Double> function = null;
	if (activation == null) {
	    return null;
	}
	switch (activation) {
	case Identity:
	    function = ActivationFunctions::dIdentity;
	    break;
	case ELU:
	    function = ActivationFunctions::dElu;
	    break;
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
	for (int i = 0; i < this.neurons.length; i++) {
	    for (int j = 0; j < this.neurons[0].length; j++) {
		// if a there is an outlier neuron with a different activation funciton
		if (neurons[i][j].getActivation() != activation) {
		    ActivationFunctions s = new ActivationFunctions();
		    try {
			String name = this.neurons[i][j].getActivation().name().toLowerCase();
			var method = s.getClass().getMethod(name, double.class);
			method.invoke(s, this.neurons[i][j].getValue());
			continue;
		    } catch (Exception e) {
			System.out.println(e);
		    }
		}
		results[i][j] = function.apply(this.neurons[i][j].getValue());
	    }
	}
	return results;
    }

    public Neuron[][] getNeurons() {
	return this.neurons;
    }

    public double[][] getWeights() {
	return weights;
    }

    public double[][] getBias() {
	return bias;
    }

    public int getNeuronsCount() {
	return this.neurons[0].length;
    }

    public double[][] getOutput() {
	return output;
    }

    public double[][] getNeuronsValues() {
	double[][] results = new double[this.neurons.length][this.neurons[0].length];
	for (int i = 0; i < results.length; i++) {
	    for (int j = 0; j < results[0].length; j++) {
		results[i][j] = this.neurons[i][j].getValue();
	    }
	}
	return results;

    }

    public void setOutput(double[][] output) {
	this.output = output;
    }

    public void setNeurons(double[][] neurons) {
	createNeurons(neurons, activation);
    }

    public void setBias(double[][] bias) {
	this.bias = bias;
    }

    public void setWeights(double[][] weights) {
	this.weights = weights;
    }

    private void createNeurons(int count) {
	this.neurons = new Neuron[count][1];
	for (int i = 0; i < count; i++) {
	    this.neurons[i][0] = new Neuron(0);
	}
    }

    private void createNeurons(double[][] neurons) {
	ArrayUtil.neutralArray(neurons);
	this.neurons = new Neuron[neurons.length][neurons[0].length];
	for (int i = 0; i < neurons.length; i++) {
	    for (int j = 0; j < neurons[0].length; j++) {
		this.neurons[i][j] = new Neuron(neurons[i][j]);
	    }
	}
    }

    private void createNeurons(double[][] neurons, double[][] bias) {
	this.neurons = new Neuron[neurons.length][neurons[0].length];
	for (int i = 0; i < neurons.length; i++) {
	    for (int j = 0; j < neurons[0].length; j++) {
		this.neurons[i][j] = new Neuron(neurons[i][j], bias[i][j]);
	    }
	}
    }

    private void createNeurons(double[][] neurons, ActivationFunction activation) {
	this.neurons = new Neuron[neurons.length][neurons[0].length];
	for (int i = 0; i < neurons.length; i++) {
	    for (int j = 0; j < neurons[0].length; j++) {
		this.neurons[i][j] = new Neuron(neurons[i][j], activation);
	    }
	}
    }

    private void createNeurons(double[][] neurons, double[][] bias, ActivationFunction activation) {
	this.neurons = new Neuron[neurons.length][neurons[0].length];
	for (int i = 0; i < neurons.length; i++) {
	    for (int j = 0; j < neurons[0].length; j++) {
		this.neurons[i][j] = new Neuron(neurons[i][j], bias[i][j], activation);
	    }
	}
    }

}
