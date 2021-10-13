public class HiddenLayer extends Layer {
    /**
     * {@link Layer#Layer(int, int, WeightGenerator, ActivationFunction)}
     * 
     */
    public HiddenLayer(int inputs, int neurons, WeightGenerator generator, ActivationFunction activation) {
	super(inputs, neurons, generator, activation);
    }

    public HiddenLayer(int inputs, int neurons, double bias[], WeightGenerator generator,
	    ActivationFunction activation) {
	super(inputs, neurons, bias, generator, activation);
    }

    public void forward(double[][] inputs) {
	setNeurons(inputs);
	setOutput(ArrayUtil.multiply(inputs, super.getWeights()));
	if (super.getBias() != null) {
	    ArrayUtil.add(super.getOutput(), super.getBias());
	}
    }

}
