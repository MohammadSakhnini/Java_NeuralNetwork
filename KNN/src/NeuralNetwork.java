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

    public void train(double[][] target) throws Exception {
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

    // TODO
    private void backpropagate(double[][] target) throws Exception {
	Collections.reverse(layers);

	double[][] delta = null;
	double[][] gradient = null;
	int index = 0;
	for (var layer : layers) {
	    if (layer instanceof HiddenLayer) {
		var adjusted_weights = ArrayUtil.add(layer.getWeights(), delta);
		Layer prev = layers.get(index - 1);
		prev.setBias(ArrayUtil.add(prev.getBias(), gradient));
		((HiddenLayer) layer).setWeights(adjusted_weights);
	    }
	    if (layer instanceof InputLayer) {

	    }
	    var error = ArrayUtil.subtract(target, layer.getOutput());
	    gradient = layer.dActivationFunction(layer.getOutput());

	    gradient = ArrayUtil.multiply(gradient, error);
	    gradient = ArrayUtil.multiply(gradient, l_rate);

	    var trans = ArrayUtil.transpose(layer.getNeuronsValues());
	    delta = ArrayUtil.multiply(gradient, trans);
	    index++;

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

    private double loss(double[] target) {
	double loss = 0;
	var predictions = ArrayUtil.toArray(lastLayer().getNeuronsValues());
	for (int i = 0; i < predictions.length; i++) {
	    loss += target[i] * Math.log(predictions[i]);
	}
	return -(1.0 / (target.length * loss));
    }

}
