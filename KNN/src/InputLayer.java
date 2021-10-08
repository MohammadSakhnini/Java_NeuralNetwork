public class InputLayer extends Layer {

    public InputLayer(double[][] neurons, double[] bias, WeightGenerator generator, ActivationFunction activation) {
	super(neurons, bias, generator, activation);
    }
    /**
     *@implNote
     *The output count of this layer is neurons[0].length (columns) 
     */
    public InputLayer(double[][] neurons, WeightGenerator generator, ActivationFunction activation) {
	super(neurons, generator, activation);
    }

    public void forward() {
	setOutput(ArrayUtil.multiply(super.getNeurons(), super.getWeights()));

	if (super.getBias() != null) {
	    ArrayUtil.add(super.getOutput(), super.getBias());
	}

    }

}
