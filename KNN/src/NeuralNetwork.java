import java.util.ArrayList;
import java.util.Arrays;

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
	if (layer.getClass().equals(OutputLayer.class)) {
	    throw new RuntimeException("Output layer already exists");
	}
	layers.add(layer);
    }

    public void outputLayer(int neurons, ActivationFunction activation) {
	layers.add(new OutputLayer(lastLayer().getNeuronsCount(), neurons, activation));
    }

    public void train(double[][] target) throws Exception {
	checkLayers();
	double[][] output = null;
	OutputLayer output_Layer = (OutputLayer) lastLayer();

	for (var layer : layers) {
	    if (layer instanceof OutputLayer) {
		output_Layer.setNeurons(output);
		output_Layer.setOutput(output);
		output_Layer.reshape();

	    }
	    if (layer instanceof InputLayer) {
		((InputLayer) layer).forward();
	    }
	    if (layer instanceof HiddenLayer) {
		((HiddenLayer) layer).forward(output);
	    }
	    layer.activationFunction();
	    output = layer.getOutput();
	}

	/*
	 * var error = ArrayUtil.subtract(target, output_Layer.getNeurons()); 
	 * var gradient = output_Layer.dActivationFunction(output);
	 * gradient = ArrayUtil.multiply(gradient, error); 
	 * gradient = ArrayUtil.multiply(gradient, l_rate);
	 * ArrayUtil.print(gradient);
	 */
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
	var predictions = ArrayUtil.toArray(lastLayer().getNeurons());
	for (int i = 0; i < predictions.length; i++) {
	    loss += target[i] * Math.log(predictions[i]);
	}
	return -(1.0 / (target.length * loss));
    }

}
