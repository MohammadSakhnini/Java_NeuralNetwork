public class InputLayer extends Layer {

    public InputLayer(double[][] neurons, int outputs, double[][] bias, WeightGenerator generator,
	    ActivationFunction activation) {
	super(neurons, outputs, bias, generator, activation);
    }

    /**
     * @implNote The output count of this layer is neurons[0].length (columns)
     */
    public InputLayer(double[][] inputs, int neurons, WeightGenerator generator) {
	super(inputs, neurons, generator);
    }

    public void forward() throws Exception {
	setOutput(ArrayUtil.multiply(super.getNeuronsValues(), super.getWeights()));
	if (super.getBias() != null) {
	    ArrayUtil.add(super.getOutput(), super.getBias());
	}

    }

}
