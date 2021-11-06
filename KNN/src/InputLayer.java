public class InputLayer extends Layer {

    public InputLayer(double[][] neurons, int outputs, double[][] bias, WeightGenerator generator,
	    ActivationFunction activation) {
	super(neurons, outputs, bias, generator, activation);
    }

    /**
     * @implNote The output count of this layer is neurons[0].length (columns)
     */
    public InputLayer(double[][] inputs, int neurons, WeightGenerator generator, ActivationFunction activation) {
	super(inputs, neurons, generator, activation);
    }

    public void forward() throws Exception {
	setOutput(ArrayUtil.multiply(super.getNeuronsValues(), super.getWeights()));
	if (super.getBias() != null) {
	    setOutput(ArrayUtil.add(super.getOutput(), super.getBias()));
	}

    }

}
