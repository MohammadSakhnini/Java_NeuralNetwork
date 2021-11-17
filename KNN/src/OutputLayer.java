public class OutputLayer extends Layer {


    public OutputLayer(int input, int output, ActivationFunction activation) {
	super(input, output, null, activation);
    }

    public double[] getNeurons1D() {
	return ArrayUtil.toArray(getNeuronsValues());
    }


}
