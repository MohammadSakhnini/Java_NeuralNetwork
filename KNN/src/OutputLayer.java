public class OutputLayer extends Layer {

    private int input, output;

    public OutputLayer(int input, int output, ActivationFunction activation) {
	super(input, output, null, activation);
	this.input = input;
	this.output = output;

    }

    public double[] getNeurons1D() {
	return ArrayUtil.toArray(getNeurons());
    }

    public void reshape() {
	var temp = ArrayUtil.neutralArray(input, output);
	setNeurons(ArrayUtil.multiply(super.getNeurons(), temp));
    }

}
