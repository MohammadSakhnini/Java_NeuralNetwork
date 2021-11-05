public class Main {
    public static void main(String[] args) throws Exception {
	double target[] = { 0, 1, 1, 1 };// Exepcted output
	double inputs[][] = { { 1, 0, 0 } };// eingabe
	var inputLayer = new InputLayer(inputs, 3, WeightGenerator.Random, ActivationFunction.Identity);
	var hiddenLayer = new HiddenLayer(3, 4, WeightGenerator.Random, ActivationFunction.Sigmoid);
	NeuralNetwork nn = new NeuralNetwork(0.00005);
	var s = hiddenLayer.getNeurons();
	nn.addLayer(inputLayer);
	nn.addLayer(hiddenLayer);
	var outputLayer = nn.addOutputLayer(4, ActivationFunction.Sigmoid);
	nn.feedForward();
	ArrayUtil.print(outputLayer.getOutput());
//	for (int i = 0; i < 1000; i++) {
//	    nn.backpropagate(target);
//	    nn.feedForward();
//	    ArrayUtil.print(outputLayer.getOutput());
//	}
    }
}
