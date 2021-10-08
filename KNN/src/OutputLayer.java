public class OutputLayer extends Layer {

    public OutputLayer(int input,int output, WeightGenerator generator, ActivationFunction activation) {
	super(input, output, generator, activation);
    }
    public double[][] getOutput()
    {
	return super.getOutput();
    }

}
