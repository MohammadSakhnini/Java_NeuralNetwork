
public class Main {
    public static void main(String[] args) throws Exception {
	double target[][] = {{1},{2},{3},{4}};
	double x[][] = { { 1, 2 }, { 4, 5 }, { 7, 8 }, { 10, 11 } };
	var input = new InputLayer(x, WeightGenerator.Random, ActivationFunction.Sigmoid);
	var hidden = new HiddenLayer(3, 3, WeightGenerator.Random, ActivationFunction.Sigmoid);
	NeuralNetwork nn = new NeuralNetwork(1);
	nn.addLayer(input);
	nn.addLayer(hidden);
	nn.outputLayer(1,ActivationFunction.Sigmoid);
	nn.train(target);
    }
}
