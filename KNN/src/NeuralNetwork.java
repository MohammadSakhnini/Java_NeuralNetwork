import java.util.ArrayList;
import java.util.Collections;

public class NeuralNetwork {
    private ArrayList<Layer> layers;
    private double l_rate;

    public NeuralNetwork(double l_rate) {
	this.l_rate = l_rate;
	layers = new ArrayList<>();
    }

    public void addLayer(Layer layer) {
	if (layers.isEmpty()) {
	    if (!(layer.getClass().equals(InputLayer.class))) {
		throw new RuntimeException("First layer has to be the input layer");
	    }
	}
	for (Layer temp : layers) {
	    if ((temp.getClass().equals(OutputLayer.class))) {
		throw new RuntimeException("Invalid structre, Outputlayer must come last");

	    }
	}
	layers.add(layer);
    }

    public OutputLayer addOutputLayer(int neurons, ActivationFunction activation) {
	var temp = new OutputLayer(lastLayer().getNeuronsCount(), neurons, activation);
	layers.add(temp);
	return temp;
    }

    public void feedForward() throws Exception {
	checkLayers();
	double[][] output = null;

	for (var layer : layers) {
	    if (layer instanceof InputLayer) {
		layer.activationFunction();
		((InputLayer) layer).forward();
	    }
	    if (layer instanceof HiddenLayer) {
		((HiddenLayer) layer).setNeurons(output);
		layer.activationFunction();
		((HiddenLayer) layer).forward();
	    }
	    if (layer instanceof OutputLayer) {
		((OutputLayer) layer).setNeurons(output);
		((OutputLayer) layer).setOutput(layer.getNeuronsValues());
		layer.activationFunction();
	    }
	    output = layer.getOutput();
	}

    }

    public void backpropagate(double[] target) throws Exception {
	feedForward();
	double error = cost(target);

	double[][] gradient = null;
	double[][] delta = null;
	double[][] new_weights = null;

	Collections.reverse(layers);

	// delta = (previousLayer * gradient)
	// new_weights = gradient * error * l_rate * (delta)
	// weights = old_weights -

	for (var layer : layers) {
	    if (layer instanceof OutputLayer) {
		gradient = layer.dActivationFunction();
		gradient = ArrayUtil.multiply(gradient, error);
		gradient = ArrayUtil.multiply(gradient, l_rate);
		continue;
	    }

	    else {

		var t_layer = ArrayUtil.transpose(layer.dActivationFunction());
		delta = ArrayUtil.multiply(t_layer, gradient);
		new_weights = ArrayUtil.subtract(layer.getWeights(), ArrayUtil.multiply(delta, l_rate));
		layer.setWeights(new_weights);

		gradient = layer.dActivationFunction();
		gradient = ArrayUtil.multiply(gradient, error);
		gradient = ArrayUtil.multiply(gradient, l_rate);
	    }
	}
	Collections.reverse(layers);
    }

    public void train(double[] target, int epoch) throws Exception {
	for (int i = 0; i < epoch; i++) {
	    backpropagate(target);
	    feedForward();
	}
    }

    private Layer lastLayer() {
	return layers.get(layers.size() - 1);
    }

    private void checkLayers() throws Exception {
	if (layers.isEmpty()) {
	    throw new Exception("The neural network has no layers!");
	}
	if (!(layers.get(0) instanceof InputLayer)) {
	    throw new Exception("Input layer missing");
	}

	if (!(lastLayer() instanceof OutputLayer)) {
	    throw new Exception("Output layer missing");
	}

    }

    public double cost(double[] target) throws Exception {
	double sum = 0;
	feedForward();
	var output = lastLayer().getOutput();
	for (int i = 0; i < target.length; i++) {
	    for (int j = 0; j < output[0].length; j++) {
		sum += Math.pow(target[i] - output[i][j], 2) / output.length;
	    }
	}
	return sum;
    }

    public double[][] output() {
	return lastLayer().getOutput();
    }

    public String toString() {
	try {
	    feedForward();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	int index = 1;
	StringBuilder builder = new StringBuilder("layers:");
	for (Layer layer : layers) {
	    builder.append(layer.getNeuronsCount() + ";");
	}
	builder.append('\n');
	builder.append("-------------------------\n");
	for (Layer layer : layers) {
	    if (layer instanceof HiddenLayer) {
		builder.append(layer.getClass().getSimpleName() + index + ":\n");
		index++;
	    } else {
		builder.append(layer.getClass().getSimpleName() + ":\n");
	    }
	    builder.append(ArrayUtil.array_toString(layer.getWeights(), "Weights"));
	    builder.append(ArrayUtil.array_toString(layer.getBias(), "Bias"));
	    builder.append(ArrayUtil.array_toString(layer.getNeuronsValues(), "Neurons"));
	    builder.append("-------------------------\n");
	}
	return builder.toString();
    }

}
