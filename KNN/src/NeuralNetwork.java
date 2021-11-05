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
		((InputLayer) layer).forward();
	    }
	    if (layer instanceof HiddenLayer) {
		((HiddenLayer) layer).setNeurons(output);
		((HiddenLayer) layer).forward();
	    }
	    if (layer instanceof OutputLayer) {
		((OutputLayer) layer).setNeurons(output);
		((OutputLayer) layer).setOutput(layer.getNeuronsValues());
	    }
	    layer.activationFunction();
	    output = layer.getOutput();
	}

    }

    public void backpropagate(double[] target) throws Exception {
	double error = cost(target);

	double[][] gradient = null;
	double[][] delta = null;
	double[][] new_weights = null;

	Collections.reverse(layers);

	for (var layer : layers) {
	    layer.dActivationFunction();
	    if (layer instanceof OutputLayer) {
		gradient = layer.getNeuronsValues();
		gradient = ArrayUtil.multiply(gradient, error);
		gradient = ArrayUtil.multiply(gradient, l_rate);
		continue;
	    }

	    //delta = (previousLayer * gradient)
	    //new_weights = gradient * error * l_rate * (delta)
	    //weights = old_weights + new weights
	    if (layer instanceof HiddenLayer) {
		var t_layer = ArrayUtil.transpose(layer.getNeuronsValues());
		delta = ArrayUtil.multiply(t_layer, gradient);
		new_weights = ArrayUtil.add(layer.getWeights(), delta);
		layer.setWeights(new_weights);
		if (layer.getBias() == null) {
		    layer.setBias(gradient);
		} else {
		    layer.setBias(ArrayUtil.add(layer.getBias(), gradient));
		}
		var layer_error = ArrayUtil.multiply(layer.getWeights(), error);

		gradient = layer.getNeuronsValues();
		gradient = ArrayUtil.multiply(gradient, layer_error);
		gradient = ArrayUtil.multiply(gradient, l_rate);
	    }
	}
	Collections.reverse(layers);
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
	var output = lastLayer().getOutput();
	for (int i = 0; i < target.length; i++) {
	    for (int j = 0; j < output[0].length; j++) {
		sum += Math.pow(output[i][j] - target[i], 2) / output.length;
	    }
	}
	return sum;
    }

}
