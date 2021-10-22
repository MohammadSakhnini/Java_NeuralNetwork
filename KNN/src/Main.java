public class Main {
    public static void main(String[] args) throws Exception {
	double target[][] = { { 0 }, { 1 }, { 1 }, { 0 } };
	double x[][] = { { 0, 0, 0 }, { 1, 0, 0 }, { 1, 0, 0 }, { 1, 1, 1 } };
	var input = new InputLayer(x, 3, WeightGenerator.Xavier);
	var hidden = new HiddenLayer(3, 1, WeightGenerator.Random, ActivationFunction.Relu);
	NeuralNetwork nn = new NeuralNetwork(1);
	nn.addLayer(input);
	nn.addLayer(hidden);
	var output = nn.addOutputLayer(1, ActivationFunction.Sigmoid);
	nn.train(target);
	ArrayUtil.print(output.getNeuronsValues());
    }
}
